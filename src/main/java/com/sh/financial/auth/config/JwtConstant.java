package com.sh.financial.auth.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Getter
public class JwtConstant {
    private final String publicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAvJ1A2jb4jIqHVeGXp+dxIJxgpkNvbUxFW7mfHPHaqqaz6gcx81CZJxDj7ewm+pzxB6bA7JhByg5AtKUhMHeWUNJBUJqAOlWBWyYVWTBcOYBmwjNfbo/jWHneZyjnDUKRPpewEItfQ8D1aeMw45P3uJGUFyLXBIx88ok7a8pX+0Jz2K/Q+PrFLvVMRmtoV40e28hqA7pUMlhS3t0aZ5MmHJyJkJEA4cil2H6lwFDKQYfQkHLWjYdUhWkv6/2wX8HsHxCTKqpSO3EPBL8kIoZ3TGSkwfYoHF/GfzloOII2z4mlC3i+R+YktR70TDWTWQWLWhlV23+D2o/XK39xxByTBQIDAQAB";
    private final String privateKey = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQC8nUDaNviMiodV4Zen53EgnGCmQ29tTEVbuZ8c8dqqprPqBzHzUJknEOPt7Cb6nPEHpsDsmEHKDkC0pSEwd5ZQ0kFQmoA6VYFbJhVZMFw5gGbCM19uj+NYed5nKOcNQpE+l7AQi19DwPVp4zDjk/e4kZQXItcEjHzyiTtrylf7QnPYr9D4+sUu9UxGa2hXjR7byGoDulQyWFLe3RpnkyYcnImQkQDhyKXYfqXAUMpBh9CQctaNh1SFaS/r/bBfwewfEJMqqlI7cQ8EvyQihndMZKTB9igcX8Z/OWg4gjbPiaULeL5H5iS1HvRMNZNZBYtaGVXbf4Paj9crf3HEHJMFAgMBAAECggEABK7AKq3uzA+ilJyaBx9DmEkeZcC3iwZ3xvM/Jd9deKgHwCTNr51kMMuMtEsAQu+moadyudAw4Ysqvfr6INAppIshXhbFd4B8CoJR4t0Ev4e2ZWL6a+efsw75f5gM6zy1F7fGQi7ka1PzZwQIYj05vlsQF5mNlLpHkT6CqDMsLbVAAq70ohD/B77b4e2HAQA4Fn2iHMPStrCsT8uMNpomCemvTtWNTuNtayjp31Ic8JbzEJvjiqrQk6XbM4CUOLUelNzHkGN3EssLGcaTwOUAfShGkC3ylx8DsV1486RR5+S1me5dHHUa909YQNutgr4WnEReTCInig8G1d2p5xDQcQKBgQDEH7YpNpCey0M6vZQxBUz+v5Z4sUEhQjodC6uC0ULkdZOHl2wGgQ2Id/mJtr3BXk7oRa9HPgFohIzO4J51irg05yl/tdVRUkNqZZLVlRs8QxjpBE7hkXnleWJgoiyIfPOX4XfO1WdmjmNsSDCinrirz0TMvKLFLh5p4i7g8PjnMQKBgQD2Mp693r85ZbweO+RbWwbE1/IP6Mpbueyh1ACmdLAmE6y7f2atMj093qeDxCiDjqlfj/IZ0uyygmTG+czcTfB86zysMv0RfD3HfD09aih7Uu+/yqHcJXXLsNOm7FVTyX5RSTGmJafQC9twEW987iDTTusinwPxR6oP9hsJJ1xcFQKBgDTbkx3SbfHOOIVZWNpJYnH6MtS+eR48mir/3S65tXybT7Kold2+5GXWZ/O8w7Gc+ElTlqTtyjVgHA8ZrLtjMrhXYPDhkeeD81V2oLddo2R/dPRAS/4xVhgJugDvVG9IteJGSyL8kwyp7ZPedPUvL/hmOVWwtXHNNzc0VYwchgjRAoGAE6mDSAKsHvdjCL8bw7gHWF0utdqPnrfAkEQQMTsMfl3/45GPL7Hddj17/2gBHFCeckVavnk78h270rBMjiOjWzamR9Jip6zWekIP2gHKbd73ysdyGdTcDYJ3xNMHFYP4FKHV10EWn+Tyh4a88qtJ79ZTrRYqCHECwfeTvS1pkOkCgYBFmiZ+3ZOQ26xTg3rw8PMVvprDokw/iQjcELM9K455afyBaBizGqxhTrflNvqmNS6VIZeBJUHhTjX8Ep7HC75Qa8CGdCgLVJvdnBLzoyiyue83wBWQdNplCyyK2yU0Ms7RTi/8o0qBseLn9tOhzodyELj0dbo9gorzdQpD/WUyPg==";

    /*Example token:
    eyJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJvaWRjLWNsaWVudCIsImlhdCI6MTcyNjY1MDE3NX0.bag5cSKX18jHt1sjVe8y0kENZUxscG70PhrBhT_0uTIoYYZiAGlNhluNpJ4zDOjTOTicAAgfYDR-Qy-2jAJBVjuUuzMT6XWAeJgYuma4ZLJRjSsj5U6x3p2fUwhC58K8yrfvTG8IRpUPKw_clB79Qif1jVGPDCtKbidvQBHQRhRk_c0aECDBGtl55gn07HmCzUEJaHWNO_FVgNNm8nqv94dOV_Oz197mrNOcN2S2Gmo2hWg3yZv1-sIvbYVmC5usIz63k1uhw05ChDomVukn9Coq8HHJ_Iay4Ejb3-AxrmVIrpk3AOkPxXsRo44M7dUrQGrNh8W9WrT_iVz49Wy01g

    */
}
