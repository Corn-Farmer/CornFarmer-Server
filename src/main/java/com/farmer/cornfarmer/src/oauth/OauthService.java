package com.farmer.cornfarmer.src.oauth;

import com.farmer.cornfarmer.src.user.UserDao;
import com.farmer.cornfarmer.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OauthService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final OauthDao oauthDao;
    private final OauthProvider oauthProvider;
    private final JwtService jwtService;

    @Autowired
    public OauthService(OauthDao oauthDao, OauthProvider oauthProvider, JwtService jwtService) {
        this.oauthDao = oauthDao;
        this.oauthProvider = oauthProvider;
        this.jwtService = jwtService;
    }

}
