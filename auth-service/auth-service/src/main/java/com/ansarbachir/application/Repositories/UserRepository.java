/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */

package com.ansarbachir.application.Repositories;


import com.ansarbachir.application.Entities.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author ansar
 */

@Repository
 public interface UserRepository extends JpaRepository <User, Long>{

        Optional<User>  findByemail(String email);
        Optional<User>  findByUsername(String username);
}
