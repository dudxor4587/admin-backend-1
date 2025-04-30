package com.admin.backend_board.repository;

import com.admin.backend_board.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MemberRepository extends JpaRepository<Member, Long> {
    // 이메일, 비밀번호로 회원 조회
    @Query("SELECT m FROM Member m WHERE m.email = :email AND m.password = :password")
    Member findByEmailAndPassword(@Param("email") String email,
                       @Param("password") String password);

    // 회원 저장
    @Modifying
    @Query(value = "INSERT INTO member (email, password) VALUES (:email, :password)", nativeQuery = true)
    void saveMember(@Param("email") String email,
                      @Param("password") String password);
}
