/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */

package com.ansarbachir.application.Repositories;


import com.ansarbachir.application.Entities.Post;
import java.util.List;
import java.util.Optional;
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

    @Query("SELECT p FROM Post p WHERE p.user.id = :userId AND p.status = 'APPROVED'")
    Optional<List<Post>> findApprovedPostsByUserId(@Param("userId") Long userId);
    
    
    @Query("SELECT p FROM Post p WHERE p.status = 'APPROVED' AND p.user.id <> :userId")
    Optional<List<Post>> findApprovedPostsExcludingUser(@Param("userId") Long userId);
    
    @Query("SELECT p FROM Post p WHERE p.status = 'UNDER_APPROVAL'")
    Optional<List<Post>> findAllUnderApprovalPosts();


}
