package com.sh.financial.auth.web.model.req;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Jacksonized
public class RegisterReq {
    @NotEmpty(message = "Name is required")
    @Size(min = 3, message = "Name should be at least 3 characters")
    private String name;

    @NotEmpty(message = "Email is required")
    @Email(message = "Email is invalid")
    private String email;

    @NotEmpty(message = "Password is required")
    @Size(min = 4, message = "Password should be at least 4 characters")
    private String password;
}