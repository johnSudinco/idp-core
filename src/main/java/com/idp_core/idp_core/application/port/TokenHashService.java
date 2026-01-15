package com.idp_core.idp_core.application.port;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Component;

@Component
public class TokenHashService {

    public String hash(String token) {
        return DigestUtils.sha256Hex(token);
    }
}
