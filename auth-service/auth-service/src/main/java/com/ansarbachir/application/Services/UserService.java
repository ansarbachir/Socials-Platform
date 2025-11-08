/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.ansarbachir.application.Services;

import com.ansarbachir.application.Entities.Role;
import com.ansarbachir.application.Entities.User;
 import com.ansarbachir.application.Repositories.UserRepository;
import com.ansarbachir.application.config.JwtUtils;
import com.ansarbachir.application.dto.LoginRequestDto;
import com.ansarbachir.application.dto.UserDTO;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
 import org.springframework.stereotype.Service;

/**
 *
 * @author ansar
 */
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;
  
    
    public String login(LoginRequestDto request){
        Optional<User> optionalUser = userRepository.findByemail(request.email());
        if(optionalUser.isEmpty()){
            return null;
        }
        User user = optionalUser.get();
        List<Role> roles = user.getRoles();
        var authToken = new UsernamePasswordAuthenticationToken(request.email(),request.password());
        var authenticate = authenticationManager.authenticate(authToken);
        var jwtAccessToken = jwtUtils.generateToken(((UserDetails) (authenticate.getPrincipal())).getUsername(),roles);
        return jwtAccessToken;
    }
    
    
     public UserDTO validateToken(String token){
        boolean isValid = jwtUtils.validateToken(token);
         if(!isValid) return null;
        return jwtUtils.getUsernameAndRoles(token);
     }
    
    
}
