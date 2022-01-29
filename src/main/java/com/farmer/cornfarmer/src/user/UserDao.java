package com.farmer.cornfarmer.src.user;

import com.farmer.cornfarmer.src.movie.model.Ott;
import com.farmer.cornfarmer.src.user.model.PostUserInfoReq;
import com.farmer.cornfarmer.src.user.model.*;
import com.farmer.cornfarmer.src.user.model.GetUserInfo;
import com.farmer.cornfarmer.src.user.model.PostUserReq;
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
        String checkOauthQuery = "select exists(select oauth_id from user where oauth_id = ?)";
        Boolean result = this.jdbcTemplate.queryForObject(checkOauthQuery, boolean.class, oauth_id);
        return result;
    }

    public boolean checkNickname(String oauth_id) {
        String checkOauthQuery = "select exists(select nickname from user where oauth_id = ?)";
        Boolean result = this.jdbcTemplate.queryForObject(checkOauthQuery, boolean.class, oauth_id);
        return result;
    }
    public String checkUserNickName(String oauth_id){
        String checkUserNickNameQuery = "select nickname from user where oauth_id = ?";
        String result = this.jdbcTemplate.queryForObject(checkUserNickNameQuery, String.class, oauth_id);
        System.out.println(result);
        return result;
    }


    public int getUserIdx(String oauth_id)
    {
        String getUserIdxQuery = "select user_idx from user where  oauth_id = ?";
        return this.jdbcTemplate.queryForObject(getUserIdxQuery, Integer.class, oauth_id);
    }

    public String getUserNickName(int userIdx){
        String getUserNickNameQuery = "select nickname from user where user_idx = ?";
        return this.jdbcTemplate.queryForObject(getUserNickNameQuery, String.class, userIdx);
    }

    public String getUserOauth_id(int userIdx){
        String getUserOauthidQuery = "select oauth_id from user where user_idx = ?";
        return this.jdbcTemplate.queryForObject(getUserOauthidQuery, String.class, userIdx);

    }

    public GetUserInfo getKakaoUser(String oauth_id)
    {
        String getUserQuery = "select * from user where  oauth_id = ? and oauth_channel = 'kakao'";

        return this.jdbcTemplate.queryForObject(getUserQuery,
                (rs, rowNum) -> new GetUserInfo(
                        rs.getInt("user_idx"),
                        rs.getString("oauth_channel"),
                        rs.getString("oauth_id"),
                        rs.getString("nickname")),oauth_id
                );
    }

    public GetUserInfo getUser(String oauth_id)
    {
        String getUserQuery = "select user_idx, oauth_channel, oauth_id, nickname from user where  oauth_id = ? ";

        return this.jdbcTemplate.queryForObject(getUserQuery,
                (rs, rowNum) -> new GetUserInfo(
                        rs.getInt("user_idx"),
                        rs.getString("oauth_channel"),
                        rs.getString("oauth_id"),
                        rs.getString("nickname")),oauth_id
        );
    }



    public int createUser(String id, String oauth_channel){
        String createUserQuery = "insert into user (oauth_id, nickname, oauth_channel, active) values (?,?,?,?)";

        this.jdbcTemplate.update(createUserQuery, id, "", oauth_channel, false);

        String lastInsertIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInsertIdQuery, int.class);

    }

    public int createUserInfo(PostUserReq postUserReq){
        String createUserInfoQuery = "update user set nickname = ?, photo = ?, is_male = ?, birth = ?, active = ? where oauth_id = ?";
        Object[] createUserInfoParams = new Object[]{postUserReq.getNickname(), postUserReq.getPhoto(), postUserReq.is_male(), postUserReq.getBirth(),  true,postUserReq.getOauth_id()};

        this.jdbcTemplate.update(createUserInfoQuery, createUserInfoParams);
//////////
        String getUserQuery = "select user_idx from user where oauth_id = ?";
        String getUserParam = postUserReq.getOauth_id();
        int userIdx = this.jdbcTemplate.queryForObject(getUserQuery, int.class, getUserParam );
        for(int i=0; i<postUserReq.getOttList().size(); i++) {
            String UserOttQuery = "insert into user_ott (ott_idx, user_idx) values (?,?)";
            Object[] createUserParams = new Object[]{postUserReq.getOttList().get(i), userIdx };
            this.jdbcTemplate.update(UserOttQuery, createUserParams);
        }

        for(int i=0; i<postUserReq.getGenreList().size(); i++) {
            String UserOttQuery = "insert into user_genre (genre_idx, user_idx) values (?,?)";
            Object[] createUserParams = new Object[]{postUserReq.getGenreList().get(i), userIdx };

            this.jdbcTemplate.update(UserOttQuery, createUserParams);
        }

        return userIdx;
    }

    public String getPhoto(int userIdx){
        String getPhotoQuery = "select photo from user where user_idx = ?";
        return this.jdbcTemplate.queryForObject(getPhotoQuery, String.class, userIdx);
    }

    public void modifyMyInfo(int userIdx, PostUserInfoReq postUserInfoReq){
        String modifyMyInfoQuery = "update user set nickname = ?, photo = ? where user_idx=?";
        Object[] modifyMyInfoParams = new Object[]{postUserInfoReq.getNickname(), postUserInfoReq.getPhoto(), userIdx};
        this.jdbcTemplate.update(modifyMyInfoQuery, modifyMyInfoParams);

        String deleteMyOttQuery = "delete from user_ott where user_idx=?";
        this.jdbcTemplate.update(deleteMyOttQuery, userIdx);

        String deleteMyGenreQuery = "delete from user_genre where user_idx=?";
        this.jdbcTemplate.update(deleteMyGenreQuery, userIdx);

        for(int i=0; i<postUserInfoReq.getUserOtt().size(); i++) {
            String UserOttQuery = "insert into user_ott (ott_idx, user_idx) values (?,?)";
            Object[] createUserParams = new Object[]{postUserInfoReq.getUserOtt().get(i), userIdx };

            this.jdbcTemplate.update(UserOttQuery, createUserParams);
        }

        for(int i=0; i<postUserInfoReq.getGenreList().size(); i++) {
            String UserOttQuery = "insert into user_genre (genre_idx, user_idx) values (?,?)";
            Object[] createUserParams = new Object[]{postUserInfoReq.getGenreList().get(i), userIdx };

            this.jdbcTemplate.update(UserOttQuery, createUserParams);
        }


    }

    public int inactive(int userIdx){
        String inactiveQuery = "update user set active = ?  where user_idx=?";
        Object[] inactiveParams = new Object[]{false, userIdx};
        this.jdbcTemplate.update(inactiveQuery, inactiveParams);

        return userIdx;

    }

    public List<OttInfo> getOttInfo(int userIdx){
        String getOttInfoQuery = "select ott.ott_idx, ott.name, ott.photo from ott left join user_ott on ott.ott_idx = user_ott.ott_idx where user_idx=?";
        int param = userIdx;

        return this.jdbcTemplate.query(getOttInfoQuery,
                (rs, rowNum)-> new OttInfo(
                        rs.getInt("Ott.ott_idx"),
                        rs.getString("ott.name"),
                        rs.getString("ott.photo")
                ), param);
    }
    public List<GenreInfo> getGenreInfo(int userIdx){
        String getOttInfoQuery = "select genre.genre_idx, genre.genre_name from genre left join user_genre on genre.genre_idx = user_genre.genre_idx where user_idx=?";
        int param = userIdx;
        return this.jdbcTemplate.query(getOttInfoQuery,
                (rs, rowNum)-> new GenreInfo(
                        rs.getInt("genre.genre_idx"),
                        rs.getString("genre.genre_name")),
                param);
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
