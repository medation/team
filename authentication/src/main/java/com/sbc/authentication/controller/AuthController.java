package com.sbc.authentication.controller;

import com.sbc.authentication.model.Role;
import com.sbc.authentication.model.User;
import com.sbc.authentication.repository.RoleRepository;
import com.sbc.authentication.security.TokenProvider;
import com.sbc.authentication.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/")
public class AuthController {

    @Autowired
    private IUserService userService;

    @Autowired
    private RoleRepository roleRepository;

    private final TokenProvider tokenProvider;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    public AuthController(PasswordEncoder passwordEncoder, TokenProvider tokenProvider, AuthenticationManager authenticationManager) {

        this.tokenProvider = tokenProvider;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;

    }

    @GetMapping("/authenticate")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public String authenticate() {
        return "Is Connected";
    }

    @PostMapping("/login")
    public String authorize(@Valid @RequestBody User user, HttpServletResponse response) {

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());

        try {
            this.authenticationManager.authenticate(authenticationToken);
            return this.tokenProvider.createToken(user.getUsername());
        }
        catch (AuthenticationException e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return null;
        }

    }

    @PostMapping("/registre")
    public String registre(@RequestBody User user) {

        if (this.userService.UserNameOrEmailExists(user.getUsername())) {
            return "EXISTS";
        }
        List<Role> roles = new ArrayList<>();
        Optional<Role> role = roleRepository.findById(1);
        roles.add(role.get());
        user.setRoles(roles);
        user.encodePassword(this.passwordEncoder);
        this.userService.save(user);

        return this.tokenProvider.createToken(user.getUsername());
    }

}
