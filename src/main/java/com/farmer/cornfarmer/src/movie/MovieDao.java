package com.farmer.cornfarmer.src.movie;


import com.farmer.cornfarmer.src.movie.model.GetGenre;
import com.farmer.cornfarmer.src.movie.model.GetKeywordRecommandRes;
import com.farmer.cornfarmer.src.movie.model.GetKeywordRes;
import com.farmer.cornfarmer.src.movie.model.GetMovieInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class MovieDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<GetKeywordRes> getKeywords() {
        String getUsersQuery = "select * from keyword"; //User 테이블에 존재하는 모든 회원들의 정보를 조회하는 쿼리
        return this.jdbcTemplate.query(getUsersQuery,
                (rs, rowNum) -> new GetKeywordRes(
                        rs.getInt("keyword_Idx"),
                        rs.getString("keyword")) // RowMapper(위의 링크 참조): 원하는 결과값 형태로 받기
        ); // 복수개의 회원정보들을 얻기 위해 jdbcTemplate 함수(Query, 객체 매핑 정보)의 결과 반환(동적쿼리가 아니므로 Parmas부분이 없음)
    }
    // 해당 userIdx를 갖는 유저조회
    public GetKeywordRecommandRes getKeyword(int keywordIdx) {
        System.out.println("movidDao실행됨");
        String getUserQuery = "select keyword from keyword where keyword_Idx = ?"; // 해당 userIdx를 만족하는 유저를 조회하는 쿼리문
        int getUserParams = keywordIdx;
        return this.jdbcTemplate.queryForObject(getUserQuery,
                (rs, rowNum) -> new GetKeywordRecommandRes(
                        rs.getString("keyword")), // RowMapper(위의 링크 참조): 원하는 결과값 형태로 받기
                getUserParams); // 한 개의 회원정보를 얻기 위한 jdbcTemplate 함수(Query, 객체 매핑 정보, Params)의 결과 반환
    }

    public List<GetGenre> getMovieGenre(int movieIdx) {
        System.out.println("movidDao실행됨");
        String getUserQuery = "Select genre_name from genre where genre_idx IN( select genre_idx from movie_genre where movie_idx=?)"; // 해당 userIdx를 만족하는 유저를 조회하는 쿼리문
        int getUserParams = movieIdx;
        return this.jdbcTemplate.query(getUserQuery,
                (rs, rowNum) -> new GetGenre(
                        rs.getString("genre_name")),// RowMapper(위의 링크 참조): 원하는 결과값 형태로 받기
                getUserParams); // 한 개의 회원정보를 얻기 위한 jdbcTemplate 함수(Query, 객체 매핑 정보, Params)의 결과 반환

    }

    public List<GetMovieInfo> getMovieInfo(int movieIdx) {

        String getUserQuery = "Select movie.movie_idx,movie_title,photo from movie join movie_photo on movie.movie_idx = movie_photo.movie_idx where movie.movie_idx in (select movie_idx from keyword_movie where keyword_idx=?);"; // 해당 userIdx를 만족하는 유저를 조회하는 쿼리문
        int getUserParams = movieIdx;
        return this.jdbcTemplate.query(getUserQuery,
                (rs, rowNum) -> new GetMovieInfo(
                        rs.getInt("movie.movie_idx"),
                        rs.getString("movie_title"),
                        rs.getString("photo")),// RowMapper(위의 링크 참조): 원하는 결과값 형태로 받기
                getUserParams); // 한 개의 회원정보를 얻기 위한 jdbcTemplate 함수(Query, 객체 매핑 정보, Params)의 결과 반환

    }

}
