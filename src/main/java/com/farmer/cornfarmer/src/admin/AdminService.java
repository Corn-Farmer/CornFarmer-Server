package com.farmer.cornfarmer.src.admin;

import com.farmer.cornfarmer.src.user.UserDao;
import com.farmer.cornfarmer.src.user.UserProvider;
import com.farmer.cornfarmer.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final AdminDao adminDao;
    private final AdminProvider adminProvider;
    private final JwtService jwtService;

    @Autowired
    public AdminService(AdminDao adminDao, AdminProvider adminProvider, JwtService jwtService) {
        this.adminDao = adminDao;
        this.adminProvider = adminProvider;
        this.jwtService = jwtService;
    }

}
