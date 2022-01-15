package com.farmer.cornfarmer.src.admin;

import com.farmer.cornfarmer.src.admin.model.GetReviewRes;
import com.farmer.cornfarmer.src.admin.model.Movie;
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
        String query = "select * from review where active = ?";
        List<GetReviewRes> getReviewResList = jdbcTemplate.query(query,
                (rs, rowNum) -> new GetReviewRes(
                        rs.getInt("review_idx"),
                        rs.getInt("movie_idx"),
                        rs.getInt("user_idx"),
                        rs.getString("contents"),
                        rs.getFloat("rate"),
                        rs.getInt("like_cnt"),
                        rs.getString("created_at")
                ),1);

        //userNickname 과 movie 정보 저장
        for(GetReviewRes g : getReviewResList){
            String nickName = this.jdbcTemplate.queryForObject("select nickname from user where user_idx = ?",
                    String.class,g.getUserIdx());
            g.setUserNickName(nickName);
            Movie movie = this.jdbcTemplate.queryForObject("select movie_idx,movie_title from movie where movie_idx = ?",
                    (rs, rowNum) -> new Movie(
                            rs.getInt("movie_idx"),
                            rs.getString("movie_title")
                    ),g.getMovie().getMovieIdx());
            List<String> moviePhoto = this.jdbcTemplate.query("select photo from movie_photo where movie_idx = ?",
                    (rs, rowNum) -> new String(
                            rs.getString("photo")
                    ),movie.getMovieIdx());
            if(moviePhoto.isEmpty()){   //반환 값이 없을 경우
                moviePhoto.add("");
            }
            movie.setMoviePhoto(moviePhoto.get(0));
            g.setMovie(movie);
        }

       return getReviewResList;
    }
}
