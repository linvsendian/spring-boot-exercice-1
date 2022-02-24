package com.exercice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * AOP BusinessException handler.
 */
@Slf4j
@ControllerAdvice
public class BusinessExceptionHandler {

  /**
   * Exception handler.
   *
   * @param ex {@code Exception}
   * @return {@code ResponseEntity}
   */
  @ExceptionHandler(BusinessException.class)
  public ResponseEntity<Void> handlerException(BusinessException ex) {
    log.warn("[Business exception]\r\nCode:{}\r\nInfo:{}", ex.getCode(),
        ex.getMessage());
    return ResponseEntity.status(ex.getCode()).build();
  }
}
