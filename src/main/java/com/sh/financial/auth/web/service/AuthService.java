package com.sh.financial.auth.web.service;

import com.sh.financial.auth.web.model.req.RegisterReq;

public interface AuthService {
    String register(RegisterReq registerReq);
}
