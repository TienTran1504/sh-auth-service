package com.sh.financial.utility.web.model.res;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Getter
@Builder
public class ApiResp<T> {
  private boolean success;
  private T data;
  private ErrorResp error;
  
  @Data
  @Builder
  public static class ErrorResp {
    private ErrorCode code;
    private String message;
    private Object details;
  }
}