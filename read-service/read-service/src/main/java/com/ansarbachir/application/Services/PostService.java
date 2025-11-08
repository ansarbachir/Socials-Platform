/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ansarbachir.application.Services;

import com.ansarbachir.application.dto.PostDTO;
import com.ansarbachir.application.Entities.Post;
import com.ansarbachir.application.Entities.User;
import com.ansarbachir.application.Repositories.PostRepository;
import com.ansarbachir.application.Repositories.UserRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 *
 * @author ansar
 */
@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

     

    public List<PostDTO> getMyPosts(long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User Not Found"));
        Optional<List<Post>> list = postRepository.findApprovedPostsByUserId(user.getId());
        List<PostDTO> results = new ArrayList<>();
        if (!list.isEmpty()) {
            list.get().stream().forEach(
                    post -> {
                        results.add(PostDTO.builder()
                                .content(post.getTitle())
                                .postId(post.getId())
                                .username(post.getUser().getUsername())
                                .build());
                    });
        }
        return results;
    }

    public List<PostDTO> getPosts(long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User Not Found"));
        Optional<List<Post>> list = postRepository.findApprovedPostsExcludingUser(user.getId());
        List<PostDTO> results = new ArrayList<>();
        if (!list.isEmpty()) {
            list.get().stream().forEach(
                    post -> {
                        results.add(PostDTO.builder()
                                .content(post.getTitle())
                                .postId(post.getId())
                                .username(post.getUser().getUsername())
                                .build());
                    });
        }
        return results;

    }

    public List<PostDTO> getUnderApprovalPosts() {
        Optional<List<Post>> list = postRepository.findAllUnderApprovalPosts();
        List<PostDTO> results = new ArrayList<>();
        if (!list.isEmpty()) {
            list.get().stream().forEach(
                    post -> {
                        results.add(PostDTO.builder()
                                .content(post.getTitle())
                                .postId(post.getId())
                                .username(post.getUser().getUsername())
                                .build());
                    });
        }
        return results;
    }

}
