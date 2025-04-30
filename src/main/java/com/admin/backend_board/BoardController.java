package com.admin.backend_board;

import com.admin.backend_board.dto.LoginRequest;
import com.admin.backend_board.dto.PostRequest;
import com.admin.backend_board.dto.SignupRequest;
import com.admin.backend_board.entity.Member;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;

    // 로그인 페이지
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    // 로그인 처리
    @PostMapping("/login")
    public String doLogin(@ModelAttribute LoginRequest loginRequest, HttpSession session) {
        // TODO : 서비스에 로그인 요청
        Member member = boardService.login(loginRequest.email(), loginRequest.password());
        session.setAttribute("memberId", member.getId());
        return "redirect:/";
    }

    // 로그아웃 처리
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        // TODO : 세션 무효화
        session.invalidate();
        return "redirect:/";
    }

    // 회원가입 페이지
    @GetMapping("/signup")
    public String signup() {
        return "signup";
    }

    @PostMapping("/signup")
    public String doSignup(@ModelAttribute SignupRequest signupRequest) {
        // TODO : 서비스에 회원가입 요청
        boardService.signup(signupRequest.email(), signupRequest.password());
        return "redirect:/login";
    }

    // 메인 페이지
    @GetMapping("/")
    public String list(Model model) {
        // TODO: 서비스에서 게시글 목록 받아와 model에 담기
        model.addAttribute("postList", boardService.findAllPosts());
        return "index";
    }

    // 게시글 상세 페이지
    @GetMapping("/post/{postId}")
    public String detail(@PathVariable Long postId, Model model) {
        // TODO: 서비스에서 게시글, 댓글 받아와 model에 담기
        model.addAttribute("post", boardService.findPostById(postId));
        model.addAttribute("commentList", boardService.findCommentsByPostId(postId));
        return "detail";
    }

    // 게시글 작성 폼
    @GetMapping("/post/new")
    public String form() {
        return "form";
    }

    // 게시글 등록 처리
    @PostMapping("/post")
    public String create(@ModelAttribute PostRequest postRequest, HttpSession session) {
        // TODO: 서비스에 게시글 저장 요청
        Long memberId = (Long) session.getAttribute("memberId");
        boardService.savePost(postRequest, memberId);
        return "redirect:/";
    }

    // 댓글 등록 처리
    @PostMapping("/post/{postId}/comment")
    public String addComment(@PathVariable Long postId, @RequestParam String content, HttpSession session) {
        // TODO: 서비스에 댓글 저장 요청
        Long memberId = (Long) session.getAttribute("memberId");
        boardService.saveComment(content, postId, memberId);
        return "redirect:/post/" + postId;
    }
}
