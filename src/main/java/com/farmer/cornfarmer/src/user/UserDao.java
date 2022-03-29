package com.farmer.cornfarmer.src.user;

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
        String query = "select * , (select nickname from user where user_idx = ?) as nickname,(select p.photo from movie_photo p where p.movie_idx = r.movie_idx limit 1) as movie_photo from review as r\n" +
                "left join movie as m on r.movie_idx = m.movie_idx where r.active = ? and r.user_idx = ?\n" +
                "order by " + sort + " desc";

        List<GetMyReviewRes> getMyReviewResList = jdbcTemplate.query(query,
                (rs, rowNum) -> new GetMyReviewRes(
                        rs.getString("nickname"),
                        rs.getInt("r.review_idx"),
                        rs.getInt("r.movie_idx"),
                        rs.getString("m.movie_title"),
                        rs.getString("movie_photo"),
                        rs.getString("r.contents"),
                        rs.getFloat("r.rate"),
                        rs.getString("r.created_at"),
                        rs.getInt("r.like_cnt")
                ), userIdx, 1, userIdx);
        return getMyReviewResList;
    }

    public boolean checkExistOauthid(String oauth_id) {
        String checkOauthQuery = "select exists(select oauth_id from user where oauth_id = ?)";
        Boolean result = this.jdbcTemplate.queryForObject(checkOauthQuery, boolean.class, oauth_id);
        return result;
    }


    public String checkOauthid(String oauth_id) {
        String checkOauthQuery = "select oauth_id from user where oauth_id = ?";
        String result = this.jdbcTemplate.queryForObject(checkOauthQuery, String.class, oauth_id);
        return result;
    }


    public String checkUserNickName(String oauth_id){
        String checkUserNickNameQuery = "select nickname from user where oauth_id = ?";
        String result = this.jdbcTemplate.queryForObject(checkUserNickNameQuery, String.class, oauth_id);
        return result;
    }

    public Boolean duplicateNick(String nick) {
        String checkUserOttQuery = "select exists(select nickname from user where nickname = ?)";
        Object[] checkUserOttParam = new Object[]{nick};
        Boolean result = this.jdbcTemplate.queryForObject(checkUserOttQuery, boolean.class, checkUserOttParam);
        return result;
    }

    public Boolean checkDuplicateNick(String nick, int useridx) {

        String checkUserOttQuery = "select exists(select nickname from user where nickname = ?)";
        Object[] checkUserOttParam = new Object[]{nick};
        Boolean is_exist = this.jdbcTemplate.queryForObject(checkUserOttQuery, boolean.class, checkUserOttParam);

        if (is_exist) {
            String checkidxQuery = "select user_idx from user where nickname = ?";
            Object[] checkidxParam = new Object[]{nick};
            int idx = this.jdbcTemplate.queryForObject(checkidxQuery, Integer.class, checkidxParam);

            if (idx == useridx) //같은 유저가 같은 닉네임을 사용할경우 넘기기
            {
                return false;
            } else {
                return true;
            }
        } else
            return false;
    }

    public int getUserIdx(String oauth_id) {
        String getUserIdxQuery = "select user_idx from user where  oauth_id = ?";
        return this.jdbcTemplate.queryForObject(getUserIdxQuery, Integer.class, oauth_id);
    }

    public String getUserNickName(int userIdx) {
        String getUserNickNameQuery = "select nickname from user where user_idx = ?";
        return this.jdbcTemplate.queryForObject(getUserNickNameQuery, String.class, userIdx);
    }

    public String getUserOauth_id(int userIdx) {
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
                        rs.getString("nickname")), oauth_id
        );
    }

    public GetUserInfo getUser(String oauth_id) {
        String getUserQuery = "select user_idx, oauth_channel, oauth_id, nickname from user where  oauth_id = ? ";

        return this.jdbcTemplate.queryForObject(getUserQuery,
                (rs, rowNum) -> new GetUserInfo(
                        rs.getInt("user_idx"),
                        rs.getString("oauth_channel"),
                        rs.getString("oauth_id"),
                        rs.getString("nickname")), oauth_id
        );
    }


    public int createUser(String id, String oauth_channel) {
        String createUserQuery = "insert into user (oauth_id, nickname, oauth_channel, active) values (?,?,?,?)";

        this.jdbcTemplate.update(createUserQuery, id, "", oauth_channel, false);

        String lastInsertIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInsertIdQuery, int.class);

    }

    public boolean checkUserOtt(int ott_idx, int user_idx) {
        String checkUserOttQuery = "select exists(select ott_idx from user_ott where ott_idx = ? and user_idx = ?)";
        Object[] checkUserOttParam = new Object[]{ott_idx, user_idx};
        Boolean result = this.jdbcTemplate.queryForObject(checkUserOttQuery, boolean.class, checkUserOttParam);
        return result;
    }

    public boolean checkBan(int src_user_idx, int dest_user_idx) {
        String checkBanQuery = "select exists(select * from ban where src_user_idx = ? and dest_user_idx = ?)";
        Object[] checkBanParam = new Object[]{src_user_idx, dest_user_idx};
        Boolean result = this.jdbcTemplate.queryForObject(checkBanQuery, boolean.class, checkBanParam);
        return result;
    }

    public boolean checkGenreUser(int genre_idx, int user_idx) {
        String checkUserOttQuery = "select exists(select genre_idx from user_genre where genre_idx = ? and user_idx = ?)";
        Object[] checkUserOttParam = new Object[]{genre_idx, user_idx};
        Boolean result = this.jdbcTemplate.queryForObject(checkUserOttQuery, boolean.class, checkUserOttParam);
        return result;
    }

    public int createUserInfo(PostUserReq postUserReq, String PhotoUrl, String oauth_id) {
        String createUserInfoQuery = "update user set nickname = ?, photo = ?, is_male = ?, birth = ?, active = ? where oauth_id = ?";
        Object[] createUserInfoParams = new Object[]{postUserReq.getNickname(), PhotoUrl, Boolean.parseBoolean(postUserReq.getIs_male()), postUserReq.getBirth(), true, oauth_id};

        this.jdbcTemplate.update(createUserInfoQuery, createUserInfoParams);

        String getUserQuery = "select user_idx from user where oauth_id = ?";
        String getUserParam = oauth_id;
        int userIdx = this.jdbcTemplate.queryForObject(getUserQuery, int.class, getUserParam);

        for(String ottidx : postUserReq.getOttList()) {
            int ott_idx = Integer.parseInt(ottidx);
            if (!checkUserOtt(ott_idx, userIdx)) {
                String UserOttQuery = "insert into user_ott (ott_idx, user_idx) values (?,?) ";
                Object[] createUserParams = new Object[]{ott_idx, userIdx};
                this.jdbcTemplate.update(UserOttQuery, createUserParams);
            }
        }

        for (String genreidx : postUserReq.getGenreList()) {
            int genre_idx = Integer.parseInt(genreidx);
            if (!checkGenreUser(genre_idx, userIdx)) {
                String UserOttQuery = "insert into user_genre (genre_idx, user_idx) values (?,?)";
                Object[] createUserParams = new Object[]{genre_idx, userIdx};
                this.jdbcTemplate.update(UserOttQuery, createUserParams);
            }
        }

        return userIdx;
    }

    public String getPhoto(int userIdx) {
        String getPhotoQuery = "select photo from user where user_idx = ?";
        return this.jdbcTemplate.queryForObject(getPhotoQuery, String.class, userIdx);
    }


    public boolean checkMyPhoto(int userIdx){
        String checkPhotoQuery = "select exists(select photo from user where user_idx = ?)";
        Object[] checkPhotoParam = new Object[]{userIdx};
        Boolean result = this.jdbcTemplate.queryForObject(checkPhotoQuery, boolean.class, checkPhotoParam);
        return result;
    }

    public void modifyMyInfo(int userIdx, PostUserInfoReq postUserInfoReq, String PhotoUrl){
        String modifyMyInfoQuery = "update user set nickname = ?, photo = ? where user_idx=?";
        Object[] modifyMyInfoParams = new Object[]{postUserInfoReq.getNickname(), PhotoUrl, userIdx};
        this.jdbcTemplate.update(modifyMyInfoQuery, modifyMyInfoParams);

        String deleteMyOttQuery = "delete from user_ott where user_idx=?";
        this.jdbcTemplate.update(deleteMyOttQuery, userIdx);

        String deleteMyGenreQuery = "delete from user_genre where user_idx=?";
        this.jdbcTemplate.update(deleteMyGenreQuery, userIdx);

        for (String ottidx : postUserInfoReq.getOttList()) {
            int ott_idx = Integer.parseInt(ottidx);
            if (!checkUserOtt(ott_idx, userIdx)) {
                String UserOttQuery = "insert into user_ott (ott_idx, user_idx) values (?,?)";
                Object[] createUserParams = new Object[]{ott_idx, userIdx};
                this.jdbcTemplate.update(UserOttQuery, createUserParams);
            }
        }

        for (String genreidx : postUserInfoReq.getGenreList()) {
            int genre_idx = Integer.parseInt(genreidx);
            if (!checkGenreUser(genre_idx, userIdx)) {
                String UserOttQuery = "insert into user_genre (genre_idx, user_idx) values (?,?)";
                Object[] createUserParams = new Object[]{genre_idx, userIdx};
                this.jdbcTemplate.update(UserOttQuery, createUserParams);
            }
        }


    }

    public void removeUserOtt(int userIdx){
        String deleteMyOttQuery = "delete from user_ott where user_idx=?";
        this.jdbcTemplate.update(deleteMyOttQuery, userIdx);
    }
    public void removeUserGenre(int userIdx){
        String deleteMyGenreQuery = "delete from user_genre where user_idx=?";
        this.jdbcTemplate.update(deleteMyGenreQuery, userIdx);

    }

    public int deleteUser(int userIdx){
        String inactiveQuery = "update user set active = 0 , oauth_id = ?, nickname = ?  where user_idx=?";
        Object[] inactiveParams = new Object[]{"","inactiveUser",userIdx};
        this.jdbcTemplate.update(inactiveQuery, inactiveParams);

        return userIdx;

    }
    public UserMyInfo getUserInfo(int userIdx) {
        String getPhotoQuery = "select nickname, photo, is_male, birth from user where user_idx = ?";
        return this.jdbcTemplate.queryForObject(getPhotoQuery,
                (rs, rowNum) -> new UserMyInfo(
                        rs.getString("nickname"),
                        rs.getString("photo"),
                        null,
                        null,
                        rs.getInt("is_male"),
                        rs.getString("birth"))
                , userIdx);
    }


    public GetUserSimpleInfo getUserSimpleInfo(int userIdx) {
        String getUserSimpleInfoQuery = "select nickname, photo, is_male, birth from user where user_idx = ?";
        return this.jdbcTemplate.queryForObject(getUserSimpleInfoQuery,
                (rs, rowNum) -> new GetUserSimpleInfo(
                        rs.getString("nickname"),
                        rs.getString("photo"),
                        null,
                        null,
                        rs.getInt("is_male"),
                        rs.getString("birth"))
                , userIdx);
    }

    public List<OttInfo> getOttInfo(int userIdx) {
        String getOttInfoQuery = "select ott.ott_idx, ott.name, ott.photo from ott left join user_ott on ott.ott_idx = user_ott.ott_idx where user_idx=?";
        int param = userIdx;

        return this.jdbcTemplate.query(getOttInfoQuery,
                (rs, rowNum) -> new OttInfo(
                        rs.getInt("Ott.ott_idx"),
                        rs.getString("ott.name"),
                        rs.getString("ott.photo")
                ), param);
    }

    public List<GenreInfo> getGenreInfo(int userIdx) {
        String getOttInfoQuery = "select genre.genre_idx, genre.genre_name from genre left join user_genre on genre.genre_idx = user_genre.genre_idx where user_idx=?";
        int param = userIdx;
        return this.jdbcTemplate.query(getOttInfoQuery,
                (rs, rowNum) -> new GenreInfo(
                        rs.getInt("genre.genre_idx"),
                        rs.getString("genre.genre_name")),
                param);
    }

    public List<Integer> getOtt(int userIdx) {
        String getOttQuery = "select ott_idx from user_ott where user_idx=?";
        int param = userIdx;

        return this.jdbcTemplate.query(getOttQuery,
                (rs, rowNum) -> rs.getInt("ott_idx")
                , param);
    }

    public List<Integer> getGenre(int userIdx) {
        String getGenreQuery = "select genre_idx from user_genre where user_idx=?";
        int param = userIdx;
        return this.jdbcTemplate.query(getGenreQuery,
                (rs, rowNum) ->  rs.getInt("genre_idx"),
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
                ), userIdx);
        return getMyMovieLikedRes;
    }

    public boolean checkActive(String oauth_id) {
        String checkactiveQuery = "select active from user where oauth_id = ?";
        Object[] checkactiveParam = new Object[]{oauth_id};
        boolean result = this.jdbcTemplate.queryForObject(checkactiveQuery, Boolean.class, checkactiveParam);
        return result;
    }
}
