package com.farmer.cornfarmer.src.admin;


import com.farmer.cornfarmer.src.admin.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class AdminDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<GetReviewRes> getAllReviews() {
        //review 테이블의 정보 저장
        String query = "select * , (select p.photo from movie_photo p where p.movie_idx = r.movie_idx limit 1) as movie_photo from review as r " +
                "left join movie as m on r.movie_idx = m.movie_idx " +
                "left join user u on r.user_idx = u.user_idx where r.active = ?";
        List<GetReviewRes> getReviewResList = jdbcTemplate.query(query,
                (rs, rowNum) -> new GetReviewRes(
                        rs.getInt("r.review_idx"),
                        rs.getInt("r.movie_idx"),
                        rs.getInt("r.user_idx"),
                        rs.getString("r.contents"),
                        rs.getFloat("r.rate"),
                        rs.getInt("r.like_cnt"),
                        rs.getString("r.created_at"),
                        rs.getString("m.movie_title"),
                        rs.getString("movie_photo"),
                        rs.getString("u.nickname")
                ), 1);

        return getReviewResList;
    }

    public int deleteReview(int reviewIdx) {
        String deleteReviewQuery = "update review set active = ? where review_idx = ?";
        Object[] deleteReviewParams = new Object[]{0, reviewIdx};
        int result = this.jdbcTemplate.update(deleteReviewQuery, deleteReviewParams);
        return result;
    }

    public int getReviewIdx(int reviewIdx) {
        String getReviewIdxQuery = "select count(*) from review where review_idx = ? and active = ?";
        return this.jdbcTemplate.queryForObject(getReviewIdxQuery, int.class, reviewIdx, 1);
    }

    // ott 테이블에 존재하는 전체 ott 정보 조회
    public List<GetOttRes> getOtts() {
        String getOttsQuery = "select * from ott";
        return this.jdbcTemplate.query(getOttsQuery,
                (rs, rowNum) -> new GetOttRes(
                        rs.getInt("ott_idx"),
                        rs.getString("name"),
                        rs.getString("photo"))
        );
    }

    // genre 테이블에 존재하는 전체 genre 정보 조회
    public List<GetGenreRes> getGenres() {
        String getGenresQuery = "select * from genre";
        return this.jdbcTemplate.query(getGenresQuery,
                (rs, rowNum) -> new GetGenreRes(
                        rs.getInt("genre_idx"),
                        rs.getString("genre_name"))
        );
    }

    // 특정 장르에 해당하는 영화 전체 조회
    public List<GetMovieRes> getGenreMovies(int genreIdx) {
        String getGenreMoviesQuery = "select movie.movie_idx, movie_title, photo, like_cnt " +
                "from (select * from movie_genre where genre_idx = ?)genre " +
                "natural join movie join movie_photo mp on movie.movie_idx = mp.movie_idx group by movie.movie_idx";
        int getGenreMoviesParams = genreIdx;
        return this.jdbcTemplate.query(getGenreMoviesQuery,
                (rs, rowNum) -> new GetMovieRes(
                        rs.getInt("movie_idx"),
                        rs.getString("movie_title"),
                        rs.getString("photo"),
                        rs.getInt("like_cnt")),
                getGenreMoviesParams);
    }

    // genre 테이블에 genre 추가
    public int createGenre(PostGenreReq postGenreReq) {
        String createGenreQuery = "insert into genre (genre_name) values (?)";
        Object[] createGenreParams = new Object[]{postGenreReq.getGenreName()};
        this.jdbcTemplate.update(createGenreQuery, createGenreParams);

        String lastInsertIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInsertIdQuery, int.class);
    }

    // ott 테이블에 ott 추가
    public int createOtt(String ottName, String ottFileURL) {
        String createOttQuery = "insert into ott (name, photo) values (?, ?)";
        Object[] createOttParams = new Object[]{ottName, ottFileURL};
        this.jdbcTemplate.update(createOttQuery, createOttParams);

        String lastInsertIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInsertIdQuery, int.class);
    }

    // movie 테이블에 movie 추가
    public int createMovie(String title, int release_year, String synopsis, String director) {
        String createMovieQuery = "insert into movie (movie_title, release_year, synopsis, director) values (?, ?, ?, ?)";
        Object[] createMovieParams = new Object[]{title, release_year, synopsis, director};
        this.jdbcTemplate.update(createMovieQuery, createMovieParams);
        String lastInsertIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInsertIdQuery, int.class);
    }

    // movie의 genre 추가(movie_genre 테이블)
    public void createMovieGenre(int movie_idx, int genre_idx) {
        String createMovieGenreQuery = "insert into movie_genre (movie_idx, genre_idx) values (?, ?)";
        Object[] createMovieGenreParams = new Object[]{movie_idx, genre_idx};
        this.jdbcTemplate.update(createMovieGenreQuery, createMovieGenreParams);
    }

    // movie의 ott 추가(movie_ott 테이블)
    public void createMovieOtt(int movie_idx, int ott_idx) {
        String createMovieOttQuery = "insert into movie_ott(movie_idx, ott_idx) values (?, ?)";
        Object[] createMovieOttParams = new Object[]{movie_idx, ott_idx};
        this.jdbcTemplate.update(createMovieOttQuery, createMovieOttParams);
    }

    // movie의 photo 추가(movie_photo 테이블)
    public void createMoviePhoto(int movie_idx, String photo) {
        String createMoviePhotoQuery = "insert into movie_photo(movie_idx, photo) values (?, ?)";
        Object[] createMoviePhotoParams = new Object[]{movie_idx, photo};
        this.jdbcTemplate.update(createMoviePhotoQuery, createMoviePhotoParams);
    }

    // keyword의 movie 추가(keyword_movie 테이블)
    public void createMovieKeyword(int keyword_idx, int movie_idx) {
        String createMovieKeywordQuery = "insert into keyword_movie(keyword_idx, movie_idx) values (?, ?)";
        Object[] createMovieKeywordParams = new Object[]{keyword_idx, movie_idx};
        this.jdbcTemplate.update(createMovieKeywordQuery, createMovieKeywordParams);
    }

    // keyword 테이블에 keyword 추가
    public int createKeyword(String keywordName) {
        String createKeywordQuery = "insert into keyword (keyword)\n" +
                "SELECT ? FROM DUAL\n" +
                "WHERE NOT EXISTS\n" +
                "(SELECT keyword FROM keyword WHERE keyword = ?)";
        Object[] createKeywordParams = new Object[]{keywordName, keywordName};
        this.jdbcTemplate.update(createKeywordQuery, createKeywordParams);

        String lastInsertIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInsertIdQuery, int.class);
    }

    public List<GetUserRes> getUser() {
        String getUserQuery = "select user_idx, nickname, photo from user";
        return this.jdbcTemplate.query(getUserQuery,
                (rs, rowNum) -> new GetUserRes(
                        rs.getInt("user_idx"),
                        rs.getString("nickname"),
                        rs.getString("photo"), null, null)
        );
    }

    public List<GetGenreRes> getUserGenre(int user_idx) {
        String getUserGenreQuery = "select user_genre.genre_idx, genre_name\n" +
                "from (select  * from user_genre where user_idx = ?)user_genre\n" +
                "natural join genre";
        return this.jdbcTemplate.query(getUserGenreQuery,
                (rs, rowNum) -> new GetGenreRes(
                        rs.getInt("genre_idx"),
                        rs.getString("genre_name")),
                user_idx
        );
    }

    public List<GetOttRes> getUserOtt(int user_idx) {
        String getUserOttQuery = "select user_ott.ott_idx, name, photo\n" +
                "from (select  * from user_ott where user_idx = ?)user_ott\n" +
                "natural join ott";
        return this.jdbcTemplate.query(getUserOttQuery,
                (rs, rowNum) -> new GetOttRes(
                        rs.getInt("ott_idx"),
                        rs.getString("name"),
                        rs.getString("photo")),
                user_idx
        );
    }

    public int getMovieIdx(int movieIdx) {
        String getMovieIdxQuery = "select count(*) from movie where movie_idx = ?";
        return this.jdbcTemplate.queryForObject(getMovieIdxQuery, int.class, movieIdx);
    }

    public List<GetKeywordRes> getKeywords() {
        // 수정
        String getKeywordsQuery = "select * from keyword";
        return this.jdbcTemplate.query(getKeywordsQuery,
                (rs, rowNum) -> new GetKeywordRes(
                        rs.getInt("keyword_idx"),
                        rs.getString("keyword"),
                        null)
        );
    }

    public List<Movie> getKeywordMovies(int keywordIdx) {
        //수정 필요
        String getKeywordMoviesQuery = "select keyword.movie_idx as movie_idx,movie_title, photo\n" +
                "from (select * from keyword_movie where keyword_idx = ?) keyword\n" +
                "natural join keyword_movie natural join movie\n" +
                "    left join movie_photo mp on keyword_movie.movie_idx = mp.movie_idx group by movie.movie_idx";
        return this.jdbcTemplate.query(getKeywordMoviesQuery,
                (rs, rowNum) -> new Movie(
                        rs.getInt("movie_idx"),
                        rs.getString("movie_title"),
                        rs.getString("photo")),
                keywordIdx
        );
    }

    public int deleteMovie(int movieIdx) {
        String deleteMovieQuery = "delete from movie_ott where movie_idx = ?";
        int result1 = this.jdbcTemplate.update(deleteMovieQuery, movieIdx);
        deleteMovieQuery = "delete from movie_genre where movie_idx = ?";
        int result2 = this.jdbcTemplate.update(deleteMovieQuery, movieIdx);
        deleteMovieQuery = "delete from movie_photo where movie_idx = ?";
        int result3 = this.jdbcTemplate.update(deleteMovieQuery, movieIdx);
        deleteMovieQuery = "delete from user_movie where movie_idx = ?";
        int result4 = this.jdbcTemplate.update(deleteMovieQuery, movieIdx);
        deleteMovieQuery = "delete from keyword_movie where movie_idx = ?";
        int result5 = this.jdbcTemplate.update(deleteMovieQuery, movieIdx);
        deleteMovieQuery = "delete from movie where movie_idx = ?";
        int result6 = this.jdbcTemplate.update(deleteMovieQuery, movieIdx);

        return (result1 | result2 | result3 | result4 | result5) & result6;
    }

    public int getKeywordIdx(int keywordIdx) {
        String getKeywordIdxQuery = "select count(*) from keyword where keyword_idx = ?";
        return this.jdbcTemplate.queryForObject(getKeywordIdxQuery, int.class, keywordIdx);
    }

    public int deleteKeyword(int keywordIdx) {
        String deleteKeywordQuery = "delete from keyword_movie where keyword_idx = ?";
        int result1 = this.jdbcTemplate.update(deleteKeywordQuery, keywordIdx);
        deleteKeywordQuery = "delete from keyword where keyword_idx = ?";
        int result2 = this.jdbcTemplate.update(deleteKeywordQuery, keywordIdx);
        return result1 | result2;
    }
}

