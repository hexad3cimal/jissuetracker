package com.jissuetracker.webapp.services;

import com.jissuetracker.webapp.dao.UserDao;
import com.jissuetracker.webapp.models.User;
import com.jissuetracker.webapp.utils.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jovin on 10/2/16.
 */

//Custom authentication handler
@Transactional
@Service("loginService")
public class LoginService implements UserDetailsService {
    @Autowired
    UserDao userDao;


    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        try {
            User user = userDao.getUserByEmail(userName);
            if (NotEmpty.notEmpty(user)) {
                String role = user.getRoles().getRolename();
                List<GrantedAuthority> rolesList = new ArrayList<GrantedAuthority>();
                rolesList.add(new SimpleGrantedAuthority(role));

                return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
                        true, true, true, true, rolesList);
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }
}
