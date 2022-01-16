package com.farmer.cornfarmer.src.user;

import com.farmer.cornfarmer.src.user.domain.GetUserInfo;
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

    public boolean checkKakaoOauth(int oauth_id) {
        String checkOauthQuery = "select exists(select oauth_id from User where oauth_id = ? AND oauth_channel = 'kakao')";
        int checkOauthParams = oauth_id;
        return this.jdbcTemplate.queryForObject(checkOauthQuery, boolean.class, checkOauthParams);
    }

    public GetUserInfo getKakaoUser(int oauth_id)
    {
        String getUserQuery = "select * from User where  auoth_id = ? AND oauth_channel = 'kakao'";
        int oauthId = oauth_id;

        return this.jdbcTemplate.queryForObject(getUserQuery,
                (rs, rowNum) -> new GetUserInfo(
                        rs.getInt("user_idx"),
                        rs.getString("oauth_channel"),
                        rs.getInt("oauth_id"),
                        rs.getString("nickname")),oauthId
                );
    }
}
