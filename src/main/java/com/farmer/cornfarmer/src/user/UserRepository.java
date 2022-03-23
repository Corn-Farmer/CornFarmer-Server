package com.farmer.cornfarmer.src.user;

import com.farmer.cornfarmer.src.user.domain.User;
import com.farmer.cornfarmer.src.user.enums.UserSocialType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsUserByNickname(String nickname);
    boolean existsUsersBySocialInfo_OauthId(String oauth_id);
    User findUserByUserIdx(Long user_idx);
    User findUserBySocialInfo_OauthId(String oauth_id);

}
