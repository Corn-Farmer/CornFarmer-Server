package com.farmer.cornfarmer.src.movie;

import com.farmer.cornfarmer.src.user.UserProvider;
import com.farmer.cornfarmer.src.user.UserService;
import com.farmer.cornfarmer.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/movies")
public class MovieController {

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final MovieProvider movieProvider;
    @Autowired
    private final MovieService movieService;
    @Autowired
    private final JwtService jwtService;


    public MovieController(MovieProvider movieProvider, MovieService movieService, JwtService jwtService) {
        this.movieProvider = movieProvider;
        this.movieService = movieService;
        this.jwtService = jwtService;
    }

    /**
     * API 설명
     * [HTTP METHOD] URL
     * 개발자 : 이름
     */
}
