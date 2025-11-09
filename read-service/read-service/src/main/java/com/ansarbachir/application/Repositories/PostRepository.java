/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */

package com.ansarbachir.application.Repositories;


import com.ansarbachir.application.Entities.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author ansar
 */

@Repository
 public interface PostRepository extends JpaRepository <Post, Long>{
  
    @Query("SELECT p FROM Post p LEFT JOIN FETCH p.mediaList WHERE p.user.id = :userId AND p.status = 'APPROVED'")
    Page<Post> findApprovedPostsByUserId(@Param("userId") Long userId,Pageable pageable);

    
    @Query("SELECT p FROM Post p LEFT JOIN FETCH p.mediaList WHERE p.status = 'APPROVED' AND p.user.id <> :userId")
    Page<Post> findApprovedPostsExcludingUser(@Param("userId") Long userId,Pageable pageable);
    
    @Query("SELECT p FROM Post p LEFT JOIN FETCH p.mediaList WHERE p.status = 'UNDER_APPROVAL'")
    Page<Post> findAllUnderApprovalPosts(Pageable pageable);


}
