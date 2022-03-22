package com.farmer.cornfarmer.src.user;

import com.farmer.cornfarmer.src.user.domain.User;
import com.farmer.cornfarmer.src.user.domain.UserLikeOtt;
import com.farmer.cornfarmer.src.user.domain.UserLikeOttPK;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserOttRepository extends JpaRepository<UserLikeOtt, UserLikeOttPK>{
    void deleteUserLikeOttsByUser(User user);
}
