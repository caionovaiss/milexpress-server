package com.milexpress.milexpressserver.repository;

import com.milexpress.milexpressserver.model.db.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    User findByEmail(String email);
}
