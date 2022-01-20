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
        String getGenreMoviesQuery = "select * from genre where genre_idx = ?";
        int getGenreMoviesParams = genreIdx;
        return this.jdbcTemplate.query(getGenreMoviesQuery,
                (rs, rowNum) -> new GetMovieRes(
                        rs.getInt("movie_idx"),
                        rs.getString("movie_title"),
                        rs.getString("movie_photo"),
                        rs.getInt("like_cnt")),
                getGenreMoviesParams);
    }

    // genre 테이블에 genre 추가
    public int createGenre(PostGenreReq postGenreReq){
        String createGenreQuery = "insert into genre (genre_name) VALUES (?)";
        Object[] createGenreParams = new Object[]{postGenreReq.getGenreName()};
        this.jdbcTemplate.update(createGenreQuery, createGenreParams);

        String lastInserIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInserIdQuery, int.class);
    }

    // ott 테이블에 ott 추가
    public int createOtt(String ottName, String ottFileURL){
        String createOttQuery = "insert into ott (name, photo) VALUES (?, ?)";
        Object[] createOttParams = new Object[]{ottName, ottFileURL};
        this.jdbcTemplate.update(createOttQuery, createOttParams);

        String lastInserIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInserIdQuery, int.class);
    }

    // movie 테이블에 movie 추가
    public int createMovie(String title,int release_year, String synopsis, String director){
        String createMovieQuery = "insert into movie (movie_title, release_year, synopsis, director) VALUES (?, ?, ?, ?)";
        Object[] createMovieParams = new Object[]{title, release_year, synopsis, director};
        this.jdbcTemplate.update(createMovieQuery, createMovieParams);
        String lastInserIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInserIdQuery, int.class);
    }

    // movie의 genre 추가(movie_genre 테이블)
    public void createMovieGenre(int movie_idx, int genre_idx){
        String createMovieGenreQuery = "insert into movie_genre (movie_idx, genre_idx) VALUES (?, ?)";
        Object[] createMovieGenreParams = new Object[]{movie_idx, genre_idx};
        this.jdbcTemplate.update(createMovieGenreQuery, createMovieGenreParams);
    }

    // movie의 ott 추가(movie_ott 테이블)
    public void createMovieOtt(int movie_idx, int ott_idx){
        String createMovieOttQuery = "insert into movie_ott(movie_idx, ott_idx) VALUES (?, ?)";
        Object[] createMovieOttParams = new Object[]{movie_idx, ott_idx};
        this.jdbcTemplate.update(createMovieOttQuery, createMovieOttParams);
    }

    // movie의 photo 추가(movie_photo 테이블)
    public void createMoviePhoto(int movie_idx, String photo){
        String createMoviePhotoQuery = "insert into movie_photo(movie_idx, photo) VALUES (?, ?)";
        Object[] createMoviePhotoParams = new Object[]{movie_idx, photo};
        this.jdbcTemplate.update(createMoviePhotoQuery, createMoviePhotoParams);
    }
}

