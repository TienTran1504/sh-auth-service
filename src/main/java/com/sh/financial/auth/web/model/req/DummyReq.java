package com.sh.financial.auth.web.model.req;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.jackson.Jacksonized;

@Getter
@ToString
@Builder
@Jacksonized
public class DummyReq {
  @NotNull(message = "dummy.id.not_null")
  private String id;
  
  @NotBlank(message = "dummy.name.not_blank")
  private String name;
}