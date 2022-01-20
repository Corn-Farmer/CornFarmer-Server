package com.farmer.cornfarmer.src.admin;

import com.farmer.cornfarmer.config.BaseException;
import com.farmer.cornfarmer.config.BaseResponse;
import com.farmer.cornfarmer.src.admin.model.*;
import com.farmer.cornfarmer.src.user.UserProvider;
import com.farmer.cornfarmer.src.user.UserService;
import com.farmer.cornfarmer.utils.JwtService;
import com.farmer.cornfarmer.utils.S3Uploader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
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
    @Autowired
    private final S3Uploader S3Uploader;


    public AdminController(AdminProvider adminProvider, AdminService adminService, JwtService jwtService, S3Uploader S3Uploader) {
        this.adminProvider = adminProvider;
        this.adminService = adminService;
        this.jwtService = jwtService;
        this.S3Uploader = S3Uploader;
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
            List<GetOttRes> getOttResList = adminProvider.getOtts();
            return new BaseResponse<>(getOttResList);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * OTT 추가 API
     * [POST] /admin/otts
     * 개발자 : 홍민주(앨리)
     */
    @ResponseBody
    @PostMapping("/otts")
    public BaseResponse<PostOttRes> postOtts(@ModelAttribute PostOttReq postOttReq) {
        // TODO : 관리자 체크 (jwt)
        try {
            String ottImgURL = S3Uploader.upload(postOttReq.getOttPhoto(), "ott");
            PostOttRes postOttRes = adminService.createOtt(postOttReq.getOttName(), ottImgURL);
            return new BaseResponse<>(postOttRes);
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
            List<GetGenreRes> getGenreResList = adminProvider.getGenres();
            return new BaseResponse<>(getGenreResList);
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

    /**
     * 모든 장르 정보 조회 API
     * [GET] /admin/genres
     * 개발자 : 홍민주(앨리)
     */
    @ResponseBody
    @GetMapping("/genres/{genreIdx}/movies")
    public BaseResponse<List<GetMovieRes>> getGenreMovies(@PathVariable("genreIdx") int genreIdx) {
        // TODO : 관리자 체크 (jwt)
        try {
            List<GetMovieRes> getMovieResList = adminProvider.getGenreMovies(genreIdx);
            return new BaseResponse<>(getMovieResList);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }
}
