package com.farmer.cornfarmer.src.movie;

import com.farmer.cornfarmer.src.user.UserDao;
import com.farmer.cornfarmer.src.user.UserProvider;
import com.farmer.cornfarmer.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MovieService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final MovieDao movieDao;
    private final MovieProvider movieProvider;
    private final JwtService jwtService;

    @Autowired
    public MovieService(MovieDao movieDao, MovieProvider movieProvider, JwtService jwtService) {
        this.movieDao = movieDao;
        this.movieProvider = movieProvider;
        this.jwtService = jwtService;
    }

}
