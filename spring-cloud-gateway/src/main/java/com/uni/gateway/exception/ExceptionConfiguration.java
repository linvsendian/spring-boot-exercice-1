package com.uni.gateway.exception;

import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.web.reactive.result.view.ViewResolver;

/**
 * Gateway exception handler.
 */
@Configuration
public class ExceptionConfiguration {

  /**
   * Inject JsonExceptionHandler as ErrorWebExceptionHandler.
   *
   * @param viewResolversProvider {@code ObjectProvider<List<ViewResolver>>}
   * @param serverCodecConfigurer {@code ServerCodecConfigurer serverCodecConfigurer}
   * @return {@code ErrorWebExceptionHandler}
   */
  @Primary
  @Bean
  @Order(Ordered.HIGHEST_PRECEDENCE)
  public ErrorWebExceptionHandler errorWebExceptionHandler(
      ObjectProvider<List<ViewResolver>> viewResolversProvider,
      ServerCodecConfigurer serverCodecConfigurer) {

    JsonExceptionHandler jsonExceptionHandler = new JsonExceptionHandler();
    jsonExceptionHandler
        .setViewResolvers(viewResolversProvider.getIfAvailable(Collections::emptyList));
    jsonExceptionHandler.setMessageWriters(serverCodecConfigurer.getWriters());
    jsonExceptionHandler.setMessageReaders(serverCodecConfigurer.getReaders());
    return jsonExceptionHandler;
  }

}
