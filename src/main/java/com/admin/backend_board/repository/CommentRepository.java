package com.admin.backend_board.repository;

import com.admin.backend_board.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    // 댓글 목록 조회
    @Query("SELECT c FROM Comment c LEFT JOIN FETCH c.post WHERE c.post.id = :postId")
    List<Comment> findByPostId(@Param("postId") Long postId);

    // 댓글 저장
    @Modifying
    @Query(value= "INSERT INTO comment (content, post_id, member_id) VALUES (:content, :postId, :memberId)", nativeQuery = true)
    void saveComment(@Param("content") String content,
                        @Param("postId") Long postId,
                        @Param("memberId") Long memberId);

    // 댓글 삭제
    @Modifying
    @Query("DELETE FROM Comment c WHERE c.id = :id")
    void deleteById(@Param("id") Long id);
}
