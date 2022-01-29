package com.farmer.cornfarmer.src.movie;


import com.farmer.cornfarmer.src.movie.model.*;
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
        String getUserQuery = "select keyword from keyword where keyword_Idx = ?"; // 해당 userIdx를 만족하는 유저를 조회하는 쿼리문
        int getUserParams = keywordIdx;
        return this.jdbcTemplate.queryForObject(getUserQuery,
                (rs, rowNum) -> new GetKeywordRecommandRes(
                        rs.getString("keyword")), // RowMapper(위의 링크 참조): 원하는 결과값 형태로 받기
                getUserParams); // 한 개의 회원정보를 얻기 위한 jdbcTemplate 함수(Query, 객체 매핑 정보, Params)의 결과 반환
    }

    public List<GetGenre> getMovieGenre(int movieIdx) {
        String getUserQuery = "select genre_name from genre where genre_idx in( select genre_idx from movie_genre where movie_idx=?)"; // 해당 userIdx를 만족하는 유저를 조회하는 쿼리문
        int getUserParams = movieIdx;
        return this.jdbcTemplate.query(getUserQuery,
                (rs, rowNum) -> new GetGenre(
                        rs.getString("genre_name")),// RowMapper(위의 링크 참조): 원하는 결과값 형태로 받기
                getUserParams); // 한 개의 회원정보를 얻기 위한 jdbcTemplate 함수(Query, 객체 매핑 정보, Params)의 결과 반환

    }

    public List<GetMovieInfo> getMovieInfo(int movieIdx) {

        String getUserQuery = "Select movie.movie_idx,movie_title from movie where movie.movie_idx in (select movie_idx from keyword_movie where keyword_idx=?);"; // 해당 userIdx를 만족하는 유저를 조회하는 쿼리문
        int getUserParams = movieIdx;
        return this.jdbcTemplate.query(getUserQuery,
                (rs, rowNum) -> new GetMovieInfo(
                        rs.getInt("movie.movie_idx"),
                        rs.getString("movie_title")),
                // RowMapper(위의 링크 참조): 원하는 결과값 형태로 받기
                getUserParams); // 한 개의 회원정보를 얻기 위한 jdbcTemplate 함수(Query, 객체 매핑 정보, Params)의 결과 반환

    }

    public GetLike getLike(int userIdx, int movieIdx) {
        String getUserQuery = "select exists(select movie_idx from user_movie where user_idx=? and movie_idx=?) as ex;"; // 해당 userIdx를 만족하는 유저를 조회하는 쿼리문
        int getUserParams = userIdx;
        int param=movieIdx;
        return this.jdbcTemplate.queryForObject(getUserQuery,
                (rs, rowNum) -> new GetLike(
                        rs.getInt("ex")), // RowMapper(위의 링크 참조): 원하는 결과값 형태로 받기
                getUserParams,param); // 한 개의 회원정보를 얻기 위한 jdbcTemplate 함수(Query, 객체 매핑 정보, Params)의 결과 반환
    }

    public void deleteFromWish(int userIdx,int movieIdx){
        String getUserQuery="delete from user_movie where movie_idx=? and user_idx=?;";
        String query="Update movie SET like_cnt=like_cnt-1 where movie_idx=?;";
        int result=jdbcTemplate.update(getUserQuery,movieIdx,userIdx);
        int res=jdbcTemplate.update(query,movieIdx);
    }
    public void addFromWish(int userIdx,int movieIdx){
        String getUserQuery="insert Into user_movie(user_idx,movie_idx,created_at) values (?,?,?);";
        java.util.Date date=new java.util.Date();
        java.sql.Timestamp time=new java.sql.Timestamp(date.getTime());
        int result=jdbcTemplate.update(getUserQuery,userIdx,movieIdx,time);
        String query="update movie set like_cnt=like_cnt+1 where movie_idx=?;";
        int res=jdbcTemplate.update(query,movieIdx);
    }

    public List<GetMovieInfo> getMovieIdx_Today(){
        String query="select movie_idx from user_movie group by day(created_at),movie_idx order by day(created_at) desc,count(*) desc;";
        return this.jdbcTemplate.query(query,
                (rs, rowNum) -> new GetMovieInfo(
                        rs.getInt("user_movie.movie_idx"))// RowMapper(위의 링크 참조): 원하는 결과값 형태로 받기
        );
    }

    public List<GetMovieInfo> getMovieIdxRand(){
        String query="select movie_idx from movie order by rand();";
        return this.jdbcTemplate.query(query,
                (rs, rowNum) -> new GetMovieInfo(
                        rs.getInt("movie_idx"))// RowMapper(위의 링크 참조): 원하는 결과값 형태로 받기
        );
    }

    public GetMovieInfo getMovieToday(int movieIdx) {
        String getUserQuery = "select movie_idx,movie_title from movie where movie_idx=?";
        int param=movieIdx;
        return this.jdbcTemplate.queryForObject(getUserQuery,
                (rs, rowNum) -> new GetMovieInfo(
                        rs.getInt("movie_idx"),
                        rs.getString("movie_title")),
                // RowMapper(위의 링크 참조): 원하는 결과값 형태로 받기
                param); // 한 개의 회원정보를 얻기 위한 jdbcTemplate 함수(Query, 객체 매핑 정보, Params)의 결과 반환

    }

    public List<GetGenre> getMoviePhoto(int movieIdx){
        String getUserQuery = "select photo from movie_photo where movie_idx=?";
        int param=movieIdx;
        return this.jdbcTemplate.query(getUserQuery,
                (rs, rowNum) -> new GetGenre(
                        rs.getString("photo")),
                // RowMapper(위의 링크 참조): 원하는 결과값 형태로 받기
                param); // 한 개의 회원정보를 얻기 위한 jdbcTemplate 함수(Query, 객체 매핑 정보, Params)의 결과 반환
    }

    public GetMovieDetail getMovieDetail(int movieIdx) {
        String getUserQuery = "select movie_title,release_year,like_cnt,synopsis from movie left join movie_ott on movie.movie_idx=movie_ott.movie_idx where movie.movie_idx=? group by movie_title;";
        int param=movieIdx;
        return this.jdbcTemplate.queryForObject(getUserQuery,
                (rs, rowNum) -> new GetMovieDetail(
                        rs.getString("movie_title"),
                        rs.getInt("release_year"),
                        rs.getInt("like_cnt"),
                        rs.getString("synopsis")),
                // RowMapper(위의 링크 참조): 원하는 결과값 형태로 받기
                param); // 한 개의 회원정보를 얻기 위한 jdbcTemplate 함수(Query, 객체 매핑 정보, Params)의 결과 반환

    }

    public List<Ott> getOtt(int movieIdx){
        String getUserQuery = "select ott.ott_idx,ott.name,photo from movie_ott left join ott on ott.ott_idx=movie_ott.ott_idx where movie_idx=?;";
        int param=movieIdx;
        return this.jdbcTemplate.query(getUserQuery,
                (rs, rowNum) -> new Ott(
                        rs.getInt("ott.ott_idx"),
                        rs.getString("ott.name"),
                        rs.getString("photo")),
                param);
    }
    public List<Review> getReview_recent(int movieIdx){
        String getUserQuery = "select review_idx,user_idx,contents,rate,like_cnt,created_at from review where movie_idx=? order by created_at desc;";
        int param=movieIdx;
        return this.jdbcTemplate.query(getUserQuery,
                (rs, rowNum) -> new Review(
                        rs.getInt("review_idx"),
                        rs.getInt("user_idx"),
                        rs.getString("contents"),
                        rs.getFloat("rate"),
                        rs.getInt("like_cnt"),
                        rs.getString("created_at")),
                param);
    }

    public List<Review> getReview_like(int movieIdx){
        String getUserQuery = "select review_idx,user_idx,contents,rate,like_cnt,created_at from review where movie_idx=? order by like_cnt desc;";
        int param=movieIdx;
        return this.jdbcTemplate.query(getUserQuery,
                (rs, rowNum) -> new Review(
                        rs.getInt("review_idx"),
                        rs.getInt("user_idx"),
                        rs.getString("contents"),
                        rs.getFloat("rate"),
                        rs.getInt("like_cnt"),
                        rs.getString("created_at")),
                param);
    }

    public Writer getWriter(int userIdx) {
        String getUserQuery = "select user_idx,nickname,photo from user where user_idx=?;";
        int param=userIdx;
        return this.jdbcTemplate.queryForObject(getUserQuery,
                (rs, rowNum) -> new Writer(
                        rs.getInt("user_idx"),
                        rs.getString("nickname"),
                        rs.getString("photo")),
                // RowMapper(위의 링크 참조): 원하는 결과값 형태로 받기
                param); // 한 개의 회원정보를 얻기 위한 jdbcTemplate 함수(Query, 객체 매핑 정보, Params)의 결과 반환

    }
    public List<GetMovieInfo> getMovieIdx_Search(String keyword,String sort){
        String query="";
        String param="%"+keyword+"%";
        if(sort.equals("likeCnt")){ //좋아요순
            query="SELECT movie.movie_idx FROM cornFarmer.movie where movie.movie_title like ? order by like_Cnt desc;";

        }
        else if(sort.equals("recent")){ //최신순
            query="SELECT movie.movie_idx FROM cornFarmer.movie where movie.movie_title like ? order by release_year desc;";
        }
        else{ //후기 많은 순
            query="SELECT movie.movie_idx FROM cornFarmer.movie left join review on (review.movie_idx=movie.movie_idx) where movie.movie_title like ? group by movie.movie_idx order by count(*) desc;";
        }
        return this.jdbcTemplate.query(query,
                (rs, rowNum) -> new GetMovieInfo(
                        rs.getInt("movie.movie_idx")),param// RowMapper(위의 링크 참조): 원하는 결과값 형태로 받기
        );
    }
    public GetLike getLikeCnt(int movieIdx) {
        String getUserQuery = "select like_cnt from movie where movie_idx=?;";
        int param=movieIdx;
        return this.jdbcTemplate.queryForObject(getUserQuery,
                (rs, rowNum) -> new GetLike(
                        rs.getInt("like_cnt")),
                // RowMapper(위의 링크 참조): 원하는 결과값 형태로 받기
                param); // 한 개의 회원정보를 얻기 위한 jdbcTemplate 함수(Query, 객체 매핑 정보, Params)의 결과 반환

    }
}
