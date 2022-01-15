package com.farmer.cornfarmer.src.oauth;

import com.farmer.cornfarmer.src.user.UserDao;
import com.farmer.cornfarmer.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OauthProvider {

    private final OauthDao oauthDao;
    private final JwtService jwtService;

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public OauthProvider(OauthDao oauthDao, JwtService jwtService) {
        this.oauthDao = oauthDao;
        this.jwtService = jwtService;
    }
}
