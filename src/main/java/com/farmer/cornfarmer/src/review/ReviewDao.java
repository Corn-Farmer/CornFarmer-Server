package com.farmer.cornfarmer.src.review;

import com.farmer.cornfarmer.src.review.model.*;
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
        String createReviewQuery = "insert into review (movie_idx,user_idx,rate,contents,active,created_at) values (?,?,?,?,?,?)";
        Object[] createReviewParams = new Object[]{postReviewReq.getMovieIdx(),userIdx,postReviewReq.getRate(),postReviewReq.getContent(),1, LocalDateTime.now()};
        this.jdbcTemplate.update(createReviewQuery,createReviewParams);

        String lastInsertIdQuery = "select last_insert_id()"; // 가장 마지막에 삽입된(생성된) id값은 가져온다.
        int lastReviewIdx = this.jdbcTemplate.queryForObject(lastInsertIdQuery, int.class);
        PostReviewRes postReviewRes = new PostReviewRes(lastReviewIdx);
        return postReviewRes;
    }

    public int getUserIdx(int reviewIdx) {
        String getUserIdxQuery = "select user_idx from review where review_idx = ?";
        return this.jdbcTemplate.queryForObject(getUserIdxQuery,int.class,reviewIdx);
    }

    public int modifyReview(int reviewIdx, PutReviewReq putReviewReq) {
        String modifyReviewQuery = "update review set contents = ?, rate = ?,updated_at = ? where review_idx = ?";
        Object[] modifyReviewParams = new Object[]{putReviewReq.getContent(),putReviewReq.getRate(),LocalDateTime.now(),reviewIdx};
        int result = this.jdbcTemplate.update(modifyReviewQuery,modifyReviewParams);
        return result;
    }

    public int deleteReview(int reviewIdx) {
        String deleteReviewQuery = "update review set active = ? where review_idx = ?";
        Object[] deleteReviewParams = new Object[]{ 0, reviewIdx};
        int result = this.jdbcTemplate.update(deleteReviewQuery,deleteReviewParams);
        return result;
    }

    public boolean checkReviewLike(int reviewIdx, int userIdx) {
        String query = "select exists(select * from user_review where review_idx = ? and user_idx = ?)";
        Object[] params = new Object[]{reviewIdx,userIdx};
        boolean result = this.jdbcTemplate.queryForObject(query,boolean.class,params);
        return result;
    }

    public int createReviewLike(int reviewIdx,int userIdx) {
        String query = "insert into user_review(review_idx,user_idx) values(?,?)";
        int result1 = this.jdbcTemplate.update(query,reviewIdx,userIdx);
        query = "update review set like_cnt = like_cnt + 1 where review_idx = ?";
        int resutl2 = this.jdbcTemplate.update(query,reviewIdx);
        return result1 & resutl2;
    }

    public int deleteReviewLike(int reviewIdx, int userIdx) {
        String query = "delete from user_review where review_idx = ? and user_idx = ?";
        int result1 = this.jdbcTemplate.update(query,reviewIdx,userIdx);
        query = "update review set like_cnt = like_cnt - 1 where review_idx = ?";
        int resutl2 = this.jdbcTemplate.update(query,reviewIdx);
        return result1 & resutl2;
    }

    public boolean checkReviewExist(int reviewIdx) {
        String query = "select active from review where review_idx = ?";
        boolean result = this.jdbcTemplate.queryForObject(query,boolean.class,reviewIdx);
        return result;
    }

    public PostReportRes createReviewReport(int reviewIdx, int userIdx, PostReportReq postReportReq) {
        String query = "insert into report (review_idx,user_idx,contents,created_at) values (?,?,?,?) ";
        Object[] params = new Object[]{reviewIdx,userIdx,postReportReq.getReport(),LocalDateTime.now()};
        this.jdbcTemplate.update(query,params);

        String lastInsertIdQuery = "select last_insert_id()"; // 가장 마지막에 삽입된(생성된) id값은 가져온다.
        int lastReviewIdx = this.jdbcTemplate.queryForObject(lastInsertIdQuery, int.class);
        PostReportRes postReportRes = new PostReportRes(lastReviewIdx);
        return postReportRes;
    }
}
