package org.example.springsecuritytest.controller;

import org.example.springsecuritytest.model.Account;
import org.example.springsecuritytest.model.Role;
import org.example.springsecuritytest.repository.AccountRepository;
import org.example.springsecuritytest.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.Set;

@RestController
public class RegistrationController {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RoleRepository roleRepository;

    @PostMapping("/register/user")
    public Account createAccount(@RequestBody Account user) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        Set<Role> roles = new HashSet<>();
        for (Role role : user.getRoles()) {
            Role existingRole = roleRepository.findByName(role.getName());
            if (existingRole != null) {
                roles.add(existingRole);
            }
        }
        user.setRoles(roles);

        return accountRepository.save(user);
    }
}
