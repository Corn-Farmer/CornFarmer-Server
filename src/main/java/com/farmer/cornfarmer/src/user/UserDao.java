package com.farmer.cornfarmer.src.user;

import com.farmer.cornfarmer.src.user.domain.GetUserInfo;
import com.farmer.cornfarmer.src.user.domain.PostUserReq;
import com.farmer.cornfarmer.src.user.domain.PostUserRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class UserDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
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
}
