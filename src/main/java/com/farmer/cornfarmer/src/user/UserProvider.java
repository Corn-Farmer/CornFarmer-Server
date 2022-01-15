package com.farmer.cornfarmer.src.user;

import com.farmer.cornfarmer.config.BaseException;
import com.farmer.cornfarmer.config.secret.Secret;
import com.farmer.cornfarmer.utils.AES128;
import com.farmer.cornfarmer.utils.JwtService;
import com.farmer.cornfarmer.config.BaseResponseStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserProvider {

    private final UserDao userDao;
    private final JwtService jwtService;

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public UserProvider(UserDao userDao, JwtService jwtService) {
        this.userDao = userDao;
        this.jwtService = jwtService;
    }
}
