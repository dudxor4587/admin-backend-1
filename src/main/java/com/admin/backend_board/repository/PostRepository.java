package com.admin.backend_board.repository;

import com.admin.backend_board.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    // 게시글 목록 조회
    @Query("SELECT p FROM Post p")
    List<Post> findAll();

    // 게시글 단건 조회
    @Query("SELECT p FROM Post p WHERE p.id = :id")
    Post findPost(@Param("id") Long id);

    // 게시글 저장
    @Modifying
    @Query(value = "INSERT INTO post (title, content, member_id) VALUES (:title, :content, :memberId)", nativeQuery = true)
    void savePost(@Param("title") String title,
                    @Param("content") String content,
                    @Param("memberId") Long memberId);

    // 게시글 삭제
    @Modifying
    @Query("DELETE FROM Post p WHERE p.id = :id")
    void deleteById(@Param("id") Long id);
}
