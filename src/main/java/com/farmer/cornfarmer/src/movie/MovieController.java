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
     *
     *
     *
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
     *
     *
     * 개발자 : 쉐리(강혜연)
     */
    @ResponseBody
    @GetMapping("keywords/{keywordIdx}") // (GET) 127.0.0.1:9000/app/users/:userIdx
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
            }
            getKeywordRes.setMovieList(movieinfoss);



            return new BaseResponse<>(getKeywordRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }

    }

    /**
     * API 설명
     * [HTTP METHOD] URL
     * 개발자 : 이름
     */
}
