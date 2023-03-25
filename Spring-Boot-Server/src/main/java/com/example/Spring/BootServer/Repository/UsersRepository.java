package com.example.Spring.BootServer.Repository;

import com.example.Spring.BootServer.Model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<Users,Long> {
    Optional<Users> findByEmail(String email);
    @Query(value = "SELECT * FROM users WHERE phone_number = :phone_number", nativeQuery = true)
    Optional<Users> findByPhone_number(@Param("phone_number") String phone_number);
    @Query(value = "SELECT * FROM users WHERE role = :role and email = :email", nativeQuery = true)
    Optional<Users> findByEmailAndRole(@Param("role") String role, @Param("email") String email);
    @Query(value ="SELECT * FROM users where user_id = :user_id", nativeQuery = true)
    Users selectUserByUserId(@Param("user_id") Long user);
}
