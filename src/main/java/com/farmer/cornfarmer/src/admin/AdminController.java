package com.farmer.cornfarmer.src.admin;

import com.farmer.cornfarmer.config.BaseException;
import com.farmer.cornfarmer.config.BaseResponse;
import com.farmer.cornfarmer.src.admin.model.GetGenreRes;
import com.farmer.cornfarmer.src.admin.model.GetOttRes;
import com.farmer.cornfarmer.src.admin.model.PostGenreReq;
import com.farmer.cornfarmer.src.admin.model.PostGenreRes;
import com.farmer.cornfarmer.src.user.UserProvider;
import com.farmer.cornfarmer.src.user.UserService;
import com.farmer.cornfarmer.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
     * 모든 OTT 정보 조회 API
     * [GET] /admin/otts
     * 개발자 : 홍민주(앨리)
     */
    @ResponseBody
    @GetMapping("/otts")
    public BaseResponse<List<GetOttRes>> getOtts() {
        // TODO : 관리자 체크 (jwt)
        try {
            List<GetOttRes> getUsersRes = adminProvider.getOtts();
            return new BaseResponse<>(getUsersRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 모든 장르 정보 조회 API
     * [GET] /admin/genres
     * 개발자 : 홍민주(앨리)
     */
    @ResponseBody
    @GetMapping("/genres")
    public BaseResponse<List<GetGenreRes>> getGenres() {
        // TODO : 관리자 체크 (jwt)
        try {
            List<GetGenreRes> getUGenresRes = adminProvider.getGenres();
            return new BaseResponse<>(getUGenresRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 장르 추가 API
     * [POST] /admin/genres
     * 개발자 : 홍민주(앨리)
     */
    @ResponseBody
    @PostMapping("/genres")
    public BaseResponse<PostGenreRes> postGenres(@RequestBody PostGenreReq postGenreReq) {
        // TODO : 관리자 체크 (jwt)
        try {
            PostGenreRes postGenreRes = adminService.createGenre(postGenreReq);
            return new BaseResponse<>(postGenreRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }
}
