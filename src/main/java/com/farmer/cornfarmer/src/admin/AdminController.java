package com.farmer.cornfarmer.src.admin;

import com.farmer.cornfarmer.config.BaseException;
import com.farmer.cornfarmer.config.BaseResponse;
import com.farmer.cornfarmer.config.BaseResponseStatus;
import com.farmer.cornfarmer.src.admin.model.GetReviewRes;
import com.farmer.cornfarmer.src.admin.model.*;
import com.farmer.cornfarmer.utils.JwtService;
import com.farmer.cornfarmer.utils.S3Uploader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.ArrayList;

import static com.farmer.cornfarmer.config.BaseResponseStatus.REQUEST_ERROR;

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
     * [GET] /admin/genres/{genreIdx}/movies
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


    /**
     * 모든 후기 보기 (관리자용)
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
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * 후기 삭제 (관리자용) API
     * [DELETE] /admin/reviews/{reviewId}
     * 개발자 : 제트(김예지)
     */
    @ResponseBody
    @DeleteMapping("/reviews/{reviewIdx}")
    public BaseResponse deleteReview(@PathVariable int reviewIdx){
        try{
            //admin 계정인지 검증
            adminService.deleteReview(reviewIdx);
            return new BaseResponse<>(BaseResponseStatus.SUCCESS);
        } catch(BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * 영화 추가 API
     * [POST] /admin/movies
     * 개발자 : 홍민주(앨리)
     */
    @ResponseBody
    @PostMapping("/movies")
    public BaseResponse<PostMovieRes> postMovies(@Valid @ModelAttribute PostMovieReq postMovieReq, BindingResult validResult){
        // TODO : 관리자 체크 (jwt)
        try {
            if (validResult.hasErrors()) {
                throw new BaseException(REQUEST_ERROR);
            }
            // 받은 이미지파일들 S3에 업로드 후 URL리스트 저장
            List<String> moviePhotoURLs = new ArrayList<>();
            for(MultipartFile moviePhoto : postMovieReq.getMoviePhoto())
                moviePhotoURLs.add(S3Uploader.upload(moviePhoto,"movie"));

            // movie 데이터베이스에 저장
            PostMovieRes postMovieRes = adminService.createMovie(moviePhotoURLs, postMovieReq);
            return new BaseResponse<>(postMovieRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 모든 사용자 조회 API
     * [GET] /admin/users
     * 개발자 : 홍민주(앨리)
     */
    @ResponseBody
    @GetMapping("/users")
    public BaseResponse<List<GetUserRes>> getUsers(){
        // TODO : 관리자 체크 (jwt)
        try {
            List<GetUserRes> getUserResList = adminProvider.getUsers();
            return new BaseResponse<>(getUserResList);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }
  
    /**  
     * 작품 삭제(관리자용) API
     * [DELETE] /admin/movies/{movieIdx}
     * 개발자 : 제트(김예지)
     */
    @ResponseBody
    @DeleteMapping("/movies/{movieIdx}")
    public BaseResponse deleteMovie(@PathVariable int movieIdx){
        try{
            //admin 계정인지 검증
            adminService.deleteMovie(movieIdx);
            return new BaseResponse<>(BaseResponseStatus.SUCCESS);
        } catch(BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * 상황키워드 삭제(관리자용) API
     * [DELETE] /admin/movies/keywords/{keywordIdx}
     * 개발자 : 제트(김예지)
     */
    @ResponseBody
    @DeleteMapping("/movies/keywords/{keywordIdx}")
    public BaseResponse deleteKeyword(@PathVariable int keywordIdx){
        try{
            //admin 계정인지 검증
            adminService.deleteKeyword(keywordIdx);
            return new BaseResponse<>(BaseResponseStatus.SUCCESS);
        }catch(BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }
}
