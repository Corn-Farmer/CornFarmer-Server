package com.farmer.cornfarmer.src.movie;

import com.farmer.cornfarmer.config.BaseException;
import com.farmer.cornfarmer.config.BaseResponse;
import com.farmer.cornfarmer.src.movie.model.GetKeywordRes;
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
     */
    // Body
    @ResponseBody
    @GetMapping("/keywords")    // Get 방식의 요청을 매핑하기 위한 어노테이션
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
     * [HTTP METHOD] URL
     * 개발자 : 이름
     */
}
