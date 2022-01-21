package com.farmer.cornfarmer.src.movie;

import com.farmer.cornfarmer.config.BaseException;
import com.farmer.cornfarmer.config.BaseResponse;
import com.farmer.cornfarmer.src.movie.model.*;
import com.farmer.cornfarmer.src.user.UserProvider;
import com.farmer.cornfarmer.src.user.UserService;
import com.farmer.cornfarmer.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
     * 키워드 조회 API
     * [Get] /movies/keywords
     * 키워드 랜덤 추천 기능을 담당하는 API
     * 개발자 : 쉐리(강혜연)
     */
    // Body
    @ResponseBody
    @GetMapping("/keywords")    // Get 방식의 요청을 매핑하기 위한 어노테이션d
    public BaseResponse<List<GetKeywordRes>> viewKeyword() {
        try{
            List<GetKeywordRes> GetKeywordRes = movieProvider.getKeywords();

            //6개 뽑는 코드
            int total_length=GetKeywordRes.size();
            /*System.out.println("총 개수는"+total_length);*/
            List <GetKeywordRes> Get6Keyword = null;

            //키워드가 6개 이하일 경우 모두 리턴
            if(total_length<=6){
                Get6Keyword=GetKeywordRes;
            }
            else{
                /*System.out.println("길이 6이상인 경우가 실행되었습니다");*/
                Collections.shuffle(GetKeywordRes);
                Get6Keyword=GetKeywordRes.subList(0,6);
            }
            
           
            return new BaseResponse<>(Get6Keyword);
        }

        catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * API 설명
     * 키워드별 작품 추천결과 조회 API
     * [GET] /movies/keywords/keywordIdx
     * 예시 : localhost:9000/movies/keywords/2
     * 개발자 : 쉐리(강혜연)
     */
    @ResponseBody
    @GetMapping("keywords/{keywordIdx}")
    public BaseResponse <GetKeywordRecommandRes> getKeywordName(@PathVariable("keywordIdx") int keywordIdx) {

        // TODO: 2022-01-16  나중에 jwt를 통해 받아와야 함, 테스트를 위해 1이라고 가정.
        int useridx=1;
        try {
            GetKeywordRecommandRes getKeywordRes = movieProvider.getKeyword(keywordIdx);

            //장르 추가하는 코드
            List <GetMovieInfo> movieinfoss=movieProvider.getmovies(keywordIdx);

            for(int i=0;i<movieinfoss.size();i++){
                List <GetGenre> movieGenre = movieProvider.getMovieGenre(movieinfoss.get(i).getMovieIdx());
                List <String> genre=new ArrayList<>();
                for(int j=0;j<movieGenre.size();j++){
                    genre.add(movieGenre.get(j).getGenre());
                }
                movieinfoss.get(i).setMovieGenreList(genre);

                //isLiked추가하는 코드
                GetLike like=movieProvider.getLike(useridx,movieinfoss.get(i).getMovieIdx());
                if(like.getIsLike()==1){
                    movieinfoss.get(i).setLiked(true);
                }
                else{
                    movieinfoss.get(i).setLiked(false);
                }

                //moviePhoto 추가하는 코드
                List <GetGenre> moviePhoto=movieProvider.getMoviePhoto(movieinfoss.get(i).getMovieIdx());
                List <String> photo=new ArrayList<>();
                for(int j=0;j<moviePhoto.size();j++){
                    photo.add(moviePhoto.get(j).getGenre());
                }
                movieinfoss.get(i).setMoviePhotoList(photo);
            }
            getKeywordRes.setMovieList(movieinfoss);



            return new BaseResponse<>(getKeywordRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }

    }

    /**
     * API 설명
     * 작품 찜하기, 취소하기 API(2022-01-18 )
     * [HTTP METHOD] URL
     * [put] localhost:9000/movies/5/like
     * 개발자 : 쉐리(강혜연)
     */

    @ResponseBody
    @PutMapping("{movieIdx}/like") //ex localhost:9000/movies/5/like
    public BaseResponse<PutUserWishRes> userWish(@PathVariable("movieIdx") int movieIdx){
        // TODO: 2022-01-18 userIdx를 1이라고 가정, 나중에 jwt로부터 userIdx 받아와야함
        int userIdx=1;

        try{
            GetLike like=movieProvider.getLike(userIdx,movieIdx);
            PutUserWishRes putUserWishRes=new PutUserWishRes();
            if(like.getIsLike()==1){
                putUserWishRes.setMsg("찜한 작품에서 삭제되었습니다.");
                movieProvider.deleteFromWish(userIdx,movieIdx);
            }
            else{
                putUserWishRes.setMsg("찜한 작품에 추가되었습니다.");
                movieProvider.addFromWish(userIdx,movieIdx);
            }
            return new BaseResponse<>(putUserWishRes);
        }

        catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }

    }


    /**
     * API 설명
     * 날짜별 작품 추천결과 조회(메인화면)API(2022-01-20)
     * [HTTP METHOD] URL
     * [GET] localhost:9000/movies/today
     * 개발자 : 쉐리(강혜연)
     */

    @ResponseBody
    @GetMapping("/today") //ex) localhost:9000/movies/today
    public BaseResponse<List<GetMovieInfo>> getMoviesToday(){
        int userIdx=1;


        try {
            //1. 어제 좋아요 받은 영화 정렬해서 가져오기
            List <GetMovieInfo> getMovieIdx=movieProvider.getMovieIdx_Today();

            if(getMovieIdx.size()<=10){
                //영화 정보 추가
                for(int i=0;i<getMovieIdx.size();i++){
                    GetMovieInfo tmp=movieProvider.getMovieToday(getMovieIdx.get(i).getMovieIdx());
                    getMovieIdx.set(i,tmp);
                }

                for(int i=0;i<getMovieIdx.size();i++){
                    List <GetGenre> movieGenre = movieProvider.getMovieGenre(getMovieIdx.get(i).getMovieIdx());
                    List <String> genre=new ArrayList<>();

                    for(int j=0;j<movieGenre.size();j++){
                        genre.add(movieGenre.get(j).getGenre());
                    }
                    getMovieIdx.get(i).setMovieGenreList(genre);

                    //isLiked추가하는 코드
                    GetLike like=movieProvider.getLike(userIdx,getMovieIdx.get(i).getMovieIdx());
                    if(like.getIsLike()==1){
                        getMovieIdx.get(i).setLiked(true);
                    }
                    else{
                        getMovieIdx.get(i).setLiked(false);
                    }

                    //moviePhoto 추가하는 코드
                    List <GetGenre> moviePhoto=movieProvider.getMoviePhoto(getMovieIdx.get(i).getMovieIdx());
                    List <String> photo=new ArrayList<>();
                    for(int j=0;j<moviePhoto.size();j++){
                        photo.add(moviePhoto.get(j).getGenre());
                    }
                    getMovieIdx.get(i).setMoviePhotoList(photo);
                }


            }
            else{
                //영화 정보 추가(10개 넘으면 10개 까지만)
                for(int i=0;i<10;i++){
                    GetMovieInfo tmp=movieProvider.getMovieToday(getMovieIdx.get(i).getMovieIdx());
                    getMovieIdx.set(i,tmp);
                }

                for(int i=0;i<10;i++){
                    List <GetGenre> movieGenre = movieProvider.getMovieGenre(getMovieIdx.get(i).getMovieIdx());
                    List <String> genre=new ArrayList<>();

                    for(int j=0;j<movieGenre.size();j++){
                        genre.add(movieGenre.get(j).getGenre());
                    }
                    getMovieIdx.get(i).setMovieGenreList(genre);

                    //isLiked추가하는 코드
                    GetLike like=movieProvider.getLike(userIdx,getMovieIdx.get(i).getMovieIdx());
                    if(like.getIsLike()==1){
                        getMovieIdx.get(i).setLiked(true);
                    }
                    else{
                        getMovieIdx.get(i).setLiked(false);
                    }

                    //moviePhoto 추가하는 코드
                    List <GetGenre> moviePhoto=movieProvider.getMoviePhoto(getMovieIdx.get(i).getMovieIdx());
                    List <String> photo=new ArrayList<>();
                    for(int j=0;j<moviePhoto.size();j++){
                        photo.add(moviePhoto.get(j).getGenre());
                    }
                    getMovieIdx.get(i).setMoviePhotoList(photo);
                }
            }


            return new BaseResponse<>(getMovieIdx);

        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * API 설명
     * 작품 상세 조회 API(2022-01-21)
     * [HTTP METHOD] URL
     * [GET] localhost:9000/movies/1?sort=likeCnt sort=recent이면 리뷰 최신순 정렬, sort=likeCnt이면 리뷰 좋아요순 정렬
     * 개발자 : 쉐리(강혜연)
     */

    @ResponseBody
    @GetMapping("/{movieIdx}") //ex)localhost:9000/movies/1?sort=likeCnt
    public BaseResponse<GetMovieDetail> getMovieDetail(@PathVariable("movieIdx") int movieIdx,@RequestParam(name="sort") String sort) throws BaseException {
        try {
            // TODO: 2022-01-21 나중에 jwt에서 userIdx가져와야함
            int userIdx = 1;
            GetMovieDetail getMovieDetail = movieProvider.getMovieDetail(movieIdx);

            //영화 장르 추가 코드
            List<GetGenre> movieGenre = movieProvider.getMovieGenre(movieIdx);
            List<String> genre = new ArrayList<>();
            for (int j = 0; j < movieGenre.size(); j++) {
                genre.add(movieGenre.get(j).getGenre());
            }
            getMovieDetail.setMovieGenreList(genre);

            //isLiked추가하는 코드
            GetLike like = movieProvider.getLike(userIdx, movieIdx);
            if (like.getIsLike() == 1) {
                getMovieDetail.setLiked(true);
            } else {
                getMovieDetail.setLiked(false);
            }

            //moviePhoto 추가하는 코드
            List<GetGenre> moviePhoto = movieProvider.getMoviePhoto(movieIdx);
            List<String> photo = new ArrayList<>();
            for (int j = 0; j < moviePhoto.size(); j++) {
                photo.add(moviePhoto.get(j).getGenre());
            }
            getMovieDetail.setMoviePhotoList(photo);
            System.out.println("moviephoto");

            //ottList 추가하는 코드
            List<Ott> ott = movieProvider.getOtt(movieIdx);
            getMovieDetail.setOttList(ott);

            //review list 가져오는 코드
            //sort=1이면 최신, sort=2이면 좋아요순
            List<Review> reviewList = new ArrayList<Review>();
            if (sort == "recent") {
                reviewList = movieProvider.getReview_recent(movieIdx);
            } else {
                reviewList = movieProvider.getReview_like(movieIdx);
            }

            //writer 정보 채우기
            for (int k = 0; k < reviewList.size(); k++) {
                int writeridx = reviewList.get(k).getUserIdx();
                Writer writer = movieProvider.getWriter(writeridx);

                reviewList.get(k).setWriter(writer);
            }
            getMovieDetail.setReviewList(reviewList);

            return new BaseResponse<>(getMovieDetail);
        } catch (BaseException exception) {

            return new BaseResponse<>((exception.getStatus()));
        }
    }
}
