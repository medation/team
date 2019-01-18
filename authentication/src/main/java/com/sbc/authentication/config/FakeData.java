package com.sbc.authentication.config;

import com.sbc.authentication.model.Role;
import com.sbc.authentication.model.User;
import com.sbc.authentication.repository.RoleRepository;
import com.sbc.authentication.repository.UserRepository;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Component
public class FakeData implements ApplicationRunner {

    @Autowired
    public RoleRepository roleRepository;

    @Autowired
    public UserRepository userRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        Role user = new Role(1,"USER");
        roleRepository.save(user);


        User userTest = new User("Mohamed","Elhachimi","userTest","passTest","test@mail.com");
        userTest.encodePassword(new BCryptPasswordEncoder(12));
        List<Role> roles = new ArrayList<>();
        Role admin = new Role(2,"ADMIN");
        roles.add(admin);
        userTest.setRoles(roles);
        userRepository.save(userTest);

    }
}