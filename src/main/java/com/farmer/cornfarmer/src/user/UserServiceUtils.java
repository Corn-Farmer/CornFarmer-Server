package com.farmer.cornfarmer.src.user;

import com.farmer.cornfarmer.src.movie.GenreRepository;
import com.farmer.cornfarmer.src.movie.OttRepository;
import com.farmer.cornfarmer.src.movie.domain.Genre;
import com.farmer.cornfarmer.src.movie.domain.Ott;
import com.farmer.cornfarmer.src.user.domain.SocialInfo;
import com.farmer.cornfarmer.src.user.domain.User;
import com.farmer.cornfarmer.src.user.domain.UserLikeGenre;
import com.farmer.cornfarmer.src.user.domain.UserLikeOtt;
import com.farmer.cornfarmer.src.user.enums.ActiveType;
import com.farmer.cornfarmer.src.user.enums.Gender;
import com.farmer.cornfarmer.src.user.enums.UserSocialType;
import com.farmer.cornfarmer.src.user.model.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserServiceUtils {

    static int createUserInfo(OttRepository ottRepository, GenreRepository genreRepository, UserRepository userRepository, UserOttRepository userOttRepository , UserGenreRepository userGenreRepository ,PostUserReq postUserReq, String PhotoUrl, String oauth_id)
    {
        User user = userRepository.findUserBySocialInfo_OauthId(oauth_id);
        user.setNickname(postUserReq.getNickname());
        user.setPhoto(PhotoUrl);
        if(postUserReq.getIs_male().equals("0"))
        {
            user.setGender(Gender.MALE);
        }
        else
        {
            user.setGender(Gender.FEMALE);
        }
        user.setBirth(postUserReq.getBirth());
        user.setActive(ActiveType.ACTIVE);
        Long user_idx = user.getUserIdx();
        userRepository.save(user);

        userOttRepository.deleteUserLikeOttsByUser(user);
        userGenreRepository.deleteUserLikeGenresByUser(user);

        for(String ottidx : postUserReq.getOttList()) {
            int ott_idx = Integer.parseInt(ottidx);

            UserLikeOtt userLikeOtt = new UserLikeOtt();
            userLikeOtt.setUser(user);
            userLikeOtt.setOtt(ottRepository.findOttByOttIdx(Integer.toUnsignedLong(ott_idx)));
            userOttRepository.save(userLikeOtt);
        }
        for (String genreidx : postUserReq.getGenreList()) {
            int genre_idx = Integer.parseInt(genreidx);

            UserLikeGenre userLikeGenre = new UserLikeGenre();
            userLikeGenre.setUser(user);
            userLikeGenre.setGenre(genreRepository.findGenreByGenreIdx(Integer.toUnsignedLong(genre_idx)));
            userGenreRepository.save(userLikeGenre);
        }

        return user_idx.intValue();


    }

    static UserMyInfo getMyInfo(UserRepository userRepository,int userIdx)
    {
        User user = findUserByUserIdx(userRepository, userIdx);
        UserMyInfo userMyInfo = new UserMyInfo();
        userMyInfo.setNickname(user.getNickname());
        userMyInfo.setPhoto(user.getPhoto());
        userMyInfo.setBirth(String.valueOf(user.getBirth()));
        userMyInfo.setIs_male(user.getGender().ordinal());

        List<UserLikeOtt> userLikeOttList = user.getUserLikeOttList();

        for(UserLikeOtt userLikeOtt : userLikeOttList)
        {
            OttInfo ottInfo = new OttInfo(userLikeOtt.getOtt().getOttIdx().intValue(), userLikeOtt.getOtt().getName(), userLikeOtt.getOtt().getPhoto());
            userMyInfo.getOttList().add(ottInfo);
        }

        List<UserLikeGenre> userLikeGenreList = user.getUserLikeGenreList();

        for(UserLikeGenre userLikeGenre : userLikeGenreList)
        {
            GenreInfo genreInfo = new GenreInfo(userLikeGenre.getGenre().getGenreIdx().intValue(), userLikeGenre.getGenre().getName());
            userMyInfo.getGenreList().add(genreInfo);
        }

        return userMyInfo;
    }

    static void modifyMyInfo(GenreRepository genreRepository,OttRepository ottRepository,UserOttRepository userOttRepository, UserGenreRepository userGenreRepository ,UserRepository userRepository,int userIdx, PostUserInfoReq postUserInfoReq, String PhotoUrl){
            User user = findUserByUserIdx(userRepository, userIdx);
            user.setNickname(postUserInfoReq.getNickname());
            user.setPhoto(PhotoUrl);
            userRepository.save(user);

            userOttRepository.deleteUserLikeOttsByUser(user);
            userGenreRepository.deleteUserLikeGenresByUser(user);

            List<String> ottList = postUserInfoReq.getOttList();

            for(String ott : ottList)
            {
                int ott_idx = Integer.parseInt(ott);
                UserLikeOtt userLikeOtt = new UserLikeOtt();
                userLikeOtt.setUser(user);
                userLikeOtt.setOtt(ottRepository.findOttByOttIdx(Integer.toUnsignedLong(ott_idx)));
                userOttRepository.save(userLikeOtt);
            }

            List<String> genreList = postUserInfoReq.getGenreList();

            for(String genre : genreList)
            {
                int genre_idx = Integer.parseInt(genre);
                UserLikeGenre userLikeGenre = new UserLikeGenre();
                userLikeGenre.setUser(user);
                userLikeGenre.setGenre(genreRepository.findGenreByGenreIdx(Integer.toUnsignedLong(genre_idx)));
                userGenreRepository.save(userLikeGenre);
            }

    }

    static GetUserInfo toGetUserInfo(UserRepository userRepository,int user_idx)
    {
        User user = findUserByUserIdx(userRepository, user_idx);
        String oauthChannel_string;
        if(user.getSocialInfo().getOauthChannel().equals(UserSocialType.KAKAO))
        {
            oauthChannel_string = "kakao";
        }
        else if (user.getSocialInfo().getOauthChannel().equals(UserSocialType.NAVER))
        {
            oauthChannel_string = "naver";
        }
        else
        {
            oauthChannel_string = "apple";
        }
        return new GetUserInfo(user.getUserIdx().intValue(), oauthChannel_string, user.getSocialInfo().getOauthId(), user.getNickname());
    }

    static GetUserSimpleInfo toGetUserSimpleInfo(UserRepository userRepository, int user_idx)
    {
        User user = findUserByUserIdx(userRepository, user_idx);
        List<UserLikeOtt> userLikeOttList = user.getUserLikeOttList();
        List<UserLikeGenre> userLikeGenreList = user.getUserLikeGenreList();

        List<Integer> ott_list = new ArrayList<>();
        for(UserLikeOtt userLikeOtt :userLikeOttList)
        {
            ott_list.add(userLikeOtt.getOtt().getOttIdx().intValue());
        }

        List<Integer> genre_list = new ArrayList<>();

        for(UserLikeGenre userLikeGenre : userLikeGenreList)
        {
            genre_list.add(userLikeGenre.getGenre().getGenreIdx().intValue());
        }

        return new GetUserSimpleInfo(user.getNickname(), user.getPhoto(), ott_list, genre_list,user.getGender().ordinal(),user.getBirth().toString());
    }

    static boolean IsExistsUserNickName(UserRepository userRepository, String nickname){
        if(userRepository.existsUserByNickname(nickname)){
            return true;
        }
        return false;
    }

    static String checkOauthId(UserRepository userRepository, String oauth_id){
        User user = userRepository.findUserBySocialInfo_OauthId(oauth_id);
        return user.getSocialInfo().getOauthId();
    }

    static User findUserByUserIdx(UserRepository userRepository, int useridx)
    {
        return userRepository.findUserByUserIdx(Integer.toUnsignedLong(useridx));
    }

    static int deleteUser(UserRepository userRepository, int user_idx)
    {
        User user = findUserByUserIdx(userRepository, user_idx);
        user.setActive(ActiveType.INACTIVE);
        user.setSocialInfo(SocialInfo.of("",user.getSocialInfo().getOauthChannel()));
        user.setNickname("inactiveUser");

        userRepository.save(user);
        return user.getUserIdx().intValue();
    }
    static String checkUserNickname(UserRepository userRepository,String oauth_id){
        User user = userRepository.findUserBySocialInfo_OauthId(oauth_id);
        return user.getNickname();
    }

    static String getPhoto(UserRepository userRepository,int user_idx)
    {
        User user = findUserByUserIdx(userRepository, user_idx);

        return user.getPhoto();
    }

    static boolean checkExistOauthId(UserRepository userRepository,String oauth_id)
    {
        return userRepository.existsUsersBySocialInfo_OauthChannel(oauth_id);
    }
}
