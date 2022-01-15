package com.farmer.cornfarmer.src.movie;

import com.farmer.cornfarmer.src.user.UserDao;
import com.farmer.cornfarmer.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MovieProvider {

    private final MovieDao movieDao;
    private final JwtService jwtService;

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public MovieProvider(MovieDao movieDao, JwtService jwtService) {
        this.movieDao = movieDao;
        this.jwtService = jwtService;
    }
}
