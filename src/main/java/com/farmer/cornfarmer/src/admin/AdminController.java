package com.farmer.cornfarmer.src.admin;

import com.farmer.cornfarmer.config.BaseException;
import com.farmer.cornfarmer.config.BaseResponse;
import com.farmer.cornfarmer.src.admin.model.GetReviewRes;
import com.farmer.cornfarmer.src.user.UserProvider;
import com.farmer.cornfarmer.src.user.UserService;
import com.farmer.cornfarmer.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final AdminProvider adminProvider;
    @Autowired
    private final AdminService adminService;
    @Autowired
    private final JwtService jwtService;


    public AdminController(AdminProvider adminProvider, AdminService adminService, JwtService jwtService) {
        this.adminProvider = adminProvider;
        this.adminService = adminService;
        this.jwtService = jwtService;
    }


    /**
     * API 설명
     * [GET] /admin/reviews
     * 개발자 : 제트(김예지)
     */
    @ResponseBody
    @GetMapping("/reviews")
    public BaseResponse<List<GetReviewRes>> getAllReviews(){
        try{
            //admin 계정인지 검증
            List<GetReviewRes> result = adminProvider.getAllReviews();
            return new BaseResponse<>(result);
        }catch(BaseException exception){
            exception.printStackTrace();
            return new BaseResponse<>(exception.getStatus());
        }
    }

}
