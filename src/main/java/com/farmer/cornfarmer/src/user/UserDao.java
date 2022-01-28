package com.farmer.cornfarmer.src.user;

import com.farmer.cornfarmer.src.user.model.*;
import com.farmer.cornfarmer.src.user.domain.GetUserInfo;
import com.farmer.cornfarmer.src.user.domain.PostUserReq;
import com.farmer.cornfarmer.src.user.domain.PostUserRes;
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

    public List<GetMyReviewRes> getMyReviews(int userIdx, String sort) {
        String query = "select * , (select p.photo from movie_photo p where p.movie_idx = r.movie_idx limit 1) as movie_photo from review as r " +
                "left join movie as m on r.movie_idx = m.movie_idx where r.active = ? and r.user_idx = ? " +
                "order by " + sort + " desc";

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
  
//    public boolean checkKakaoOauth(String oauth_id) {
//        String checkOauthQuery = "select exists(select oauth_id from User where oauth_id = ? AND oauth_channel = 'kakao')";
//        Boolean result = this.jdbcTemplate.queryForObject(checkOauthQuery, boolean.class, oauth_id);
//        return result;
//    }

    public boolean checkOauth(String oauth_id) {
        String checkOauthQuery = "select exists(select oauth_id from User where oauth_id = ?)";
        Boolean result = this.jdbcTemplate.queryForObject(checkOauthQuery, boolean.class, oauth_id);
        return result;
    }

    public boolean checkNickname(String oauth_id) {
        String checkOauthQuery = "select exists(select nickname from User where oauth_id = ?)";
        Boolean result = this.jdbcTemplate.queryForObject(checkOauthQuery, boolean.class, oauth_id);
        return result;
    }

    public int getUserIdx(String oauth_id)
    {
        String getUserIdxQuery = "select user_idx from User where  oauth_id = ?";
        return this.jdbcTemplate.queryForObject(getUserIdxQuery, Integer.class, oauth_id);
    }

    public GetUserInfo getKakaoUser(String oauth_id)
    {
        String getUserQuery = "select * from User where  oauth_id = ? and oauth_channel = 'kakao'";

        return this.jdbcTemplate.queryForObject(getUserQuery,
                (rs, rowNum) -> new GetUserInfo(
                        rs.getInt("user_idx"),
                        rs.getString("oauth_channel"),
                        rs.getInt("oauth_id"),
                        rs.getString("nickname")),oauth_id
                );
    }

    public GetUserInfo getUser(String oauth_id)
    {
        String getUserQuery = "select * from User where  oauth_id = ? ";

        return this.jdbcTemplate.queryForObject(getUserQuery,
                (rs, rowNum) -> new GetUserInfo(
                        rs.getInt("user_idx"),
                        rs.getString("oauth_channel"),
                        rs.getInt("oauth_id"),
                        rs.getString("nickname")),oauth_id
        );
    }



    public int createUser(String id, String oauth_channel){
        String createUserQuery = "insert into User (oauth_id, oauth_channel, active) values (?,?,?)";
        Object[] createUserParams = new Object[]{id, oauth_channel, false };

        this.jdbcTemplate.update(createUserQuery, createUserParams);
        String lastInsertIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInsertIdQuery, int.class);

    }

    public int createUserInfo(PostUserReq postUserReq){
        String createUserInfoQuery = "update User set (nickname, photo, is_male, birth, ottList, genreList, active) values(?,?,?,?,?,?) where oauth_id=?";
        Object[] createUserInfoParams = new Object[]{postUserReq.getNickname(), postUserReq.getPhoto(), postUserReq.is_male(), postUserReq.getBirth(), postUserReq.getOttList(), postUserReq.getGenreList(), true,postUserReq.getOauth_id()};
        this.jdbcTemplate.update(createUserInfoQuery, createUserInfoParams);

        String getUserQuery = "select user_idx from User where  oauth_id = ? ";

        return this.jdbcTemplate.queryForObject(getUserQuery, int.class, postUserReq.getOauth_id());
    }

    public List<GetMyMovieLikedRes> getMyMoviesLiked(int userIdx) {
        String query = "select o.movie_idx,group_concat(o.genre_name) as genre_name,o.movie_title,o.like_cnt,o.movie_photo from " +
                "(select um.movie_idx,m.movie_title,m.like_cnt,ge.genre_name,um.created_at,(select p.photo from movie_photo p where p.movie_idx = um.movie_idx limit 1) as movie_photo " +
                "from user_movie um left join movie m on um.movie_idx = m.movie_idx " +
                "left join (select mg.movie_idx,g.genre_idx,g.genre_name from movie_genre mg left join genre g on mg.genre_idx = g.genre_idx) as ge " +
                "on um.movie_idx = ge.movie_idx where um.user_idx = ? ) o group by movie_idx order by o.created_at desc";

        List<GetMyMovieLikedRes> getMyMovieLikedRes = jdbcTemplate.query(query,
                (rs, rowNum) -> new GetMyMovieLikedRes(
                        rs.getInt("o.movie_idx"),
                        rs.getString("o.movie_title"),
                        rs.getString("o.movie_photo"),
                        rs.getString("genre_name"),
                        rs.getInt("o.like_cnt")
                ),userIdx);
        return getMyMovieLikedRes;
    }
}
