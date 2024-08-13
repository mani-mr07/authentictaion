package com.loginauthenication.authentication.Repository;

import com.loginauthenication.authentication.Service.emailService;
import com.loginauthenication.authentication.models.userDetails;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface userrepository extends JpaRepository<userDetails,Long> {

    Optional<userDetails> findByUserEmail(String email);

    @Transactional
    @Modifying
    @Query("UPDATE userDetails o SET o.otp =0  WHERE o.createdAt < :expiryTime")
    void deleteExpiredOtps(LocalDateTime expiryTime);
}
