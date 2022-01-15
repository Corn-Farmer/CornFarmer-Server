package com.farmer.cornfarmer.src.review;

import com.farmer.cornfarmer.src.review.model.PostReviewReq;
import com.farmer.cornfarmer.src.review.model.PostReviewRes;
import com.farmer.cornfarmer.src.review.model.PutReviewReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.time.LocalDateTime;

@Repository
public class ReviewDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public PostReviewRes createReview(int userIdx, PostReviewReq postReviewReq) {
        String createReviewQuery = "INSERT INTO review (movie_idx,user_idx,rate,contents,active,created_at) VALUES (?,?,?,?,?,?)";
        Object[] createReviewParams = new Object[]{postReviewReq.getMovieIdx(),userIdx,postReviewReq.getRate(),postReviewReq.getContent(),1, LocalDateTime.now()};
        this.jdbcTemplate.update(createReviewQuery,createReviewParams);

        String lastInsertIdQuery = "SELECT last_insert_id()"; // 가장 마지막에 삽입된(생성된) id값은 가져온다.
        int lastReviewIdx = this.jdbcTemplate.queryForObject(lastInsertIdQuery, int.class);
        PostReviewRes postReviewRes = new PostReviewRes(lastReviewIdx);
        return postReviewRes;
    }

    public int getUserIdx(int reviewIdx) {
        String getUserIdxQuery = "SELECT user_idx FROM review WHERE review_idx = ?";
        return this.jdbcTemplate.queryForObject(getUserIdxQuery,int.class,reviewIdx);
    }

    public int modifyReview(int reviewIdx, PutReviewReq putReviewReq) {
        String modifyReviewQuery = "UPDATE review SET contents = ?, rate = ? WHERE review_idx = ?";
        Object[] modifyReviewParams = new Object[]{putReviewReq.getContent(),putReviewReq.getRate(),reviewIdx};
        int result = this.jdbcTemplate.update(modifyReviewQuery,modifyReviewParams);
        return result;
    }

    public int deleteReview(int reviewIdx) {
        String deleteReviewQuery = "UPDATE review SET active = ? WHERE review_idx = ?";
        Object[] deleteReviewParams = new Object[]{ 0, reviewIdx};
        int result = this.jdbcTemplate.update(deleteReviewQuery,deleteReviewParams);
        return result;
    }
}
