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
                ),1);

       return getReviewResList;
    }

    public int deleteReview(int reviewIdx) {
        String deleteReviewQuery = "update review set active = ? where review_idx = ?";
        Object[] deleteReviewParams = new Object[]{ 0, reviewIdx};
        int result = this.jdbcTemplate.update(deleteReviewQuery,deleteReviewParams);
        return result;
    }

    public int getReviewIdx(int reviewIdx) {
        String getReviewIdxQuery = "select count(*) from review where review_idx = ? and active = ?";
        return this.jdbcTemplate.queryForObject(getReviewIdxQuery,int.class,reviewIdx,1);
    }
}
