package com.uni.gateway.exception;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.cloud.gateway.support.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.codec.HttpMessageReader;
import org.springframework.http.codec.HttpMessageWriter;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.lang.NonNull;
import org.springframework.util.Assert;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.reactive.result.view.ViewResolver;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * Web Exception handler.
 */

@Slf4j
public class JsonExceptionHandler implements ErrorWebExceptionHandler {

  private List<HttpMessageReader<?>> messageReaders = Collections.emptyList();
  private List<HttpMessageWriter<?>> messageWriters = Collections.emptyList();
  private List<ViewResolver> viewResolvers = Collections.emptyList();
  private final ThreadLocal<Map<String, Object>> exceptionHandlerResult = new ThreadLocal<>();

  /**
   * Set message readers.
   *
   * @param messageReaders {@code List<HttpMessageReader<?>>}
   */
  public void setMessageReaders(List<HttpMessageReader<?>> messageReaders) {

    Assert.notNull(messageReaders, "'messageReaders' must not be null");
    this.messageReaders = messageReaders;
  }

  /**
   * Set resolvers.
   *
   * @param viewResolvers {@code List<ViewResolver>}
   */
  public void setViewResolvers(List<ViewResolver> viewResolvers) {
    this.viewResolvers = viewResolvers;
  }

  /**
   * Set message writers.
   *
   * @param messageWriters {@code List<HttpMessageWriter<?>>}
   */
  public void setMessageWriters(List<HttpMessageWriter<?>> messageWriters) {

    Assert.notNull(messageWriters, "'messageWriters' must not be null");
    this.messageWriters = messageWriters;
  }

  @NonNull
  protected Mono<ServerResponse> renderErrorResponse(ServerRequest request) {

    Map<String, Object> result = exceptionHandlerResult.get();
    Mono<ServerResponse> responseMono = ServerResponse.status((HttpStatus) result.get("httpStatus"))
        .contentType(MediaType.APPLICATION_JSON)
        .body(BodyInserters.fromValue(result.get("body")));
    exceptionHandlerResult.remove();
    return responseMono;
  }

  private Mono<? extends Void> write(ServerWebExchange exchange, ServerResponse response) {

    exchange.getResponse().getHeaders().setContentType(response.headers().getContentType());
    return response.writeTo(exchange, new ResponseContext());
  }

  /**
   * Exception handler.
   *
   * @param serverWebExchange {@code ServerWebExchange}
   * @param throwable         {@code Throwable}
   * @return {@code Mono}
   */
  @Override
  @NonNull
  public Mono<Void> handle(@NonNull ServerWebExchange serverWebExchange, @NonNull Throwable
      throwable) {
    HttpStatus httpStatus;
    String body;

    if (throwable instanceof NotFoundException) {
      httpStatus = HttpStatus.NOT_FOUND;
      body = "Service Not Found";
    } else if (throwable instanceof ResponseStatusException) {
      ResponseStatusException responseStatusException = (ResponseStatusException) throwable;
      httpStatus = responseStatusException.getStatus();
      body = responseStatusException.getMessage();
    } else {
      httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
      body = "Internal Server Error";
    }

    Map<String, Object> result = new HashMap<>(2, 1);
    result.put("httpStatus", httpStatus);

    String msg = "{\"code\":" + httpStatus.value() + ",\"message\": \"" + body + "\"}";
    result.put("body", msg);

    ServerHttpRequest request = serverWebExchange.getRequest();
    log.error("[Web exception]\r\nRequest path：{}\r\ninfo：{}", request.getPath(),
        throwable.getMessage());

    if (serverWebExchange.getResponse().isCommitted()) {
      return Mono.error(throwable);
    }

    exceptionHandlerResult.set(result);
    ServerRequest newRequest = ServerRequest.create(serverWebExchange, this.messageReaders);

    return RouterFunctions.route(RequestPredicates.all(), this::renderErrorResponse)
        .route(newRequest)
        .switchIfEmpty(Mono.error(throwable))
        .flatMap(handler -> handler.handle(newRequest))
        .flatMap(response -> write(serverWebExchange, response));
  }

  /**
   * ResponseContext class.
   */
  private class ResponseContext implements ServerResponse.Context {

    /**
     * Message writers.
     *
     * @return {@code List<HttpMessageWriter>}
     */
    @Override
    @NonNull
    public List<HttpMessageWriter<?>> messageWriters() {
      return JsonExceptionHandler.this.messageWriters;
    }

    /**
     * View resolvers.
     *
     * @return {@code List<ViewResolver>}
     */
    @Override
    @NonNull
    public List<ViewResolver> viewResolvers() {
      return JsonExceptionHandler.this.viewResolvers;
    }
  }

}
