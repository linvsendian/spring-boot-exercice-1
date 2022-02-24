package com.exercice;

import org.springframework.http.HttpStatus;

/**
 * Exception to throw in business level.
 */
public class BusinessException extends RuntimeException {

  private final HttpStatus code;

  public HttpStatus getCode() {
    return code;
  }

  public BusinessException(HttpStatus status) {
    super(status.getReasonPhrase());
    this.code = status;
  }
}
