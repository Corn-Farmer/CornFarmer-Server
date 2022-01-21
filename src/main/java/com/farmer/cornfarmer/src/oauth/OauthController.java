package com.farmer.cornfarmer.src.oauth;

import com.farmer.cornfarmer.src.user.UserProvider;
import com.farmer.cornfarmer.src.user.UserService;
import com.farmer.cornfarmer.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/oauth")
public class OauthController {

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final OauthProvider oauthProvider;
    @Autowired
    private final OauthService oauthService;
    @Autowired
    private final JwtService jwtService;


    public OauthController(OauthProvider oauthProvider, OauthService oauthService, JwtService jwtService) {
        this.oauthProvider = oauthProvider;
        this.oauthService = oauthService;
        this.jwtService = jwtService;
    }

}
