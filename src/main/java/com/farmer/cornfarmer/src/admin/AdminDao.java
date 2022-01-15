package com.farmer.cornfarmer.src.admin;

import com.farmer.cornfarmer.src.admin.model.GetGenreRes;
import com.farmer.cornfarmer.src.admin.model.GetOttRes;
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
        String getOttsQuery = "select * from ott"; //User 테이블에 존재하는 모든 회원들의 정보를 조회하는 쿼리
        return this.jdbcTemplate.query(getOttsQuery,
                (rs, rowNum) -> new GetOttRes(
                        rs.getInt("ottIdx"),
                        rs.getString("name"),
                        rs.getString("photo"))
        );
    }

    // genre 테이블에 존재하는 전체 genre 정보 조회
    public List<GetGenreRes> getGenres() {
        String getGenresQuery = "select * from genre"; //User 테이블에 존재하는 모든 회원들의 정보를 조회하는 쿼리
        return this.jdbcTemplate.query(getGenresQuery,
                (rs, rowNum) -> new GetGenreRes(
                        rs.getInt("genreIdx"),
                        rs.getString("genreName"))
        );
    }
}
