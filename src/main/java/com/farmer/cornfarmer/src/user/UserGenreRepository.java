package com.farmer.cornfarmer.src.user;

import com.farmer.cornfarmer.src.user.domain.User;
import com.farmer.cornfarmer.src.user.domain.UserLikeGenre;
import com.farmer.cornfarmer.src.user.domain.UserLikeGenrePK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserGenreRepository extends JpaRepository<UserLikeGenre, UserLikeGenrePK> {
    void deleteUserLikeGenresByUser(User user);
}
