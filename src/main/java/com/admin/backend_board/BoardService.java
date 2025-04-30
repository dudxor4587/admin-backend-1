package com.admin.backend_board;

import com.admin.backend_board.dto.PostRequest;
import com.admin.backend_board.dto.PostResponse;
import com.admin.backend_board.entity.Comment;
import com.admin.backend_board.entity.Member;
import com.admin.backend_board.entity.Post;
import com.admin.backend_board.repository.CommentRepository;
import com.admin.backend_board.repository.MemberRepository;
import com.admin.backend_board.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;

    // 로그인 처리
    @Transactional
    public Member login(String email, String password) {
        // TODO : 레포지토리에서 회원 정보 조회
        return memberRepository.findByEmailAndPassword(email, password);
    }

    // 회원가입 처리
    @Transactional
    public void signup(String email, String password) {
        // TODO : 회원 정보 저장
        memberRepository.saveMember(email, password);
    }

    // 게시글 목록 조회
    @Transactional
    public List<PostResponse> findAllPosts() {
        // TODO: 레포지토리에서 게시글 목록 조회
        List<Post> posts = postRepository.findAll();
        return posts.stream()
                .map(post -> new PostResponse(
                        post.getId(),
                        post.getTitle(),
                        post.getContent(),
                        commentRepository.findByPostId(post.getId())
                ))
                .toList();
    }

    // 게시글 상세 조회
    @Transactional
    public PostResponse findPostById(Long id) {
        // TODO: 레포지토리에서 게시글 한 건 조회
        Post post = postRepository.findPost(id);
        List<Comment> comments = commentRepository.findByPostId(id);
        return new PostResponse(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                comments
        );
    }

    // 게시글 저장
    @Transactional
    public void savePost(PostRequest postRequest, Long memberId) {
        // TODO: 레포지토리로 게시글 저장
        postRepository.savePost(postRequest.title(), postRequest.content(), memberId);
    }

    // 댓글 저장
    @Transactional
    public void saveComment(String content, Long postId, Long memberId) {
        // TODO: 레포지토리로 댓글 저장
        commentRepository.saveComment(content, postId, memberId);
    }

    // 댓글 목록 조회
    @Transactional
    public List<Comment> findCommentsByPostId(Long postId) {
        // TODO: 레포지토리에서 댓글 목록 조회
        return commentRepository.findByPostId(postId);
    }
}
