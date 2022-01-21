package com.farmer.cornfarmer.src.user;

import com.farmer.cornfarmer.src.user.model.GetMyReviewRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class UserDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<GetMyReviewRes> getMyReviews(int userIdx) {
        String query = "select * , (select p.photo from movie_photo p where p.movie_idx = r.movie_idx limit 1) as movie_photo from review as r " +
                "left join movie as m on r.movie_idx = m.movie_idx where r.active = ? and r.user_idx = ?";

        List<GetMyReviewRes> getMyReviewResList = jdbcTemplate.query(query,
                (rs, rowNum) -> new GetMyReviewRes(
                        rs.getInt("r.review_idx"),
                        rs.getInt("r.movie_idx"),
                        rs.getString("m.movie_title"),
                        rs.getString("movie_photo"),
                        rs.getString("r.contents"),
                        rs.getFloat("r.rate"),
                        rs.getString("r.created_at"),
                        rs.getInt("r.like_cnt")
                ),1,userIdx);
       return getMyReviewResList;
    }
}
