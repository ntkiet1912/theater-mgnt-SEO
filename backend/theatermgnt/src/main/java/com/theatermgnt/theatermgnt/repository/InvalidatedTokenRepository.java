package com.theatermgnt.theatermgnt.repository;

import com.theatermgnt.theatermgnt.entity.InvalidatedToken;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.Instant;

@Repository
public interface InvalidatedTokenRepository extends JpaRepository<InvalidatedToken, String> {
    @Modifying
    @Transactional
    @Query("DELETE FROM InvalidatedToken it WHERE it.expiryTime < :now")
    void deleteAllExpiredTokens(Instant now);

}
