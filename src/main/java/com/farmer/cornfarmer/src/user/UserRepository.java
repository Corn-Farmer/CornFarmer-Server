package com.farmer.cornfarmer.src.user;

import com.farmer.cornfarmer.src.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
}
