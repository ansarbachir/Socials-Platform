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
import com.ansarbachir.application.dto.PostMediaResponse;
import com.ansarbachir.application.dto.PostsPageResponse;
 import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    public PostsPageResponse getMyPosts(long userId, Pageable pageable) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User Not Found"));
        Page<Post> page = postRepository.findApprovedPostsByUserId(user.getId(), pageable);
        
        if (!page.isEmpty()) {
            
           
            
            List<PostDTO> list = page.getContent()
                    .stream()
                    .map(post -> PostDTO.builder()
                    .created_at(post.getCreatedAt())
                    .content(post.getContent())
                    .postId(post.getId())
                    .mediaList(post.getMediaList().stream()
                            .map(media -> {
                                PostMediaResponse mr = new PostMediaResponse();
                                mr.setId(media.getId());
                                mr.setMediaData(media.getMediaUrl()); 
                                return mr;
                            })
                            .collect(Collectors.toList()) 
                    )
                    .username(post.getUser().getUsername())
                    .build()
                    )
                    .toList();

            return new PostsPageResponse(
                    list,
                    page.getNumber(),
                    page.getTotalPages(),
                    page.getTotalElements()
            );
        }
        return new PostsPageResponse(null, 0, 0, 0);
    }

    public PostsPageResponse getPosts(long userId, Pageable pageable) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User Not Found"));
        Page<Post> page = postRepository.findApprovedPostsExcludingUser(user.getId(), pageable);
        if (!page.isEmpty()) {
            List<PostDTO> list = page.getContent()
                    .stream()
                    .map(post -> PostDTO.builder()
                    .created_at(post.getCreatedAt())
                    .content(post.getContent())
                    .postId(post.getId())
                    .mediaList(post.getMediaList().stream()
                            .map(media -> {
                                PostMediaResponse mr = new PostMediaResponse();
                                mr.setId(media.getId());
                                mr.setMediaData(media.getMediaUrl()); 
                                return mr;
                            })
                            .collect(Collectors.toList()) 
                    )
                    .username(post.getUser().getUsername())
                    .build()
                    )
                    .toList();

            return new PostsPageResponse(
                    list,
                    page.getNumber(),
                    page.getTotalPages(),
                    page.getTotalElements()
            );
        }
        return new PostsPageResponse(null, 0, 0, 0);
    }

    public PostsPageResponse getUnderApprovalPosts(Pageable pageable) {
        Page<Post> page = postRepository.findAllUnderApprovalPosts(pageable);

        if (!page.isEmpty()) {

             page.getContent()
                    .stream()
                    .forEach(p -> {
                    System.out.println("- "+p.toString() );
                    System.out.println("----------------------------------------");
                    System.out.println("* "+p.getMediaList().size());
                    });
            
            List<PostDTO> list = page.getContent()
                    .stream()
                    .map(post -> PostDTO.builder()
                    .created_at(post.getCreatedAt())
                    .content(post.getContent())
                    .postId(post.getId())
                    .mediaList(post.getMediaList().stream()
                            .map(media -> {
                                PostMediaResponse mr = new PostMediaResponse();
                                mr.setId(media.getId());
                                mr.setMediaData(media.getMediaUrl()); 
                                return mr;
                            })
                            .collect(Collectors.toList()) 
                    )
                    .username(post.getUser().getUsername())
                    .build()
                    )
                    .toList();

            return new PostsPageResponse(
                    list,
                    page.getNumber(),
                    page.getTotalPages(),
                    page.getTotalElements()
            );
        }
        return new PostsPageResponse(null, 0, 0, 0);
    }
}
