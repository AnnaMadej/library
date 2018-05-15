package com.aniamadej.Library.Models.Security;

import com.aniamadej.Library.Models.Entities.UserModel;
import com.aniamadej.Library.Models.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class BasicUserDetailsService implements UserDetailsService{

    @Autowired
    UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserModel userModel = userRepository.findByLogin(username);
        if (userModel==null){
            throw new UsernameNotFoundException("there is no such user: " + username);
        }

        List<GrantedAuthority> grantedAuthorityList = new LinkedList<>();
        grantedAuthorityList.add(new SimpleGrantedAuthority("ROLE_USER"));
        UserDetails user = new User(userModel.getLogin(), userModel.getPassword(), grantedAuthorityList);

        return user;
    }
}
