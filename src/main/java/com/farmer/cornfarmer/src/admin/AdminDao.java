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
}
