package com.vn.DATN.Controller;

import com.vn.DATN.Common.BasicBeanRemote;
import com.vn.DATN.Service.UsersService;
import com.vn.DATN.entity.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UsersController {

    private final BasicBeanRemote basicBeanRemote;
    private final UsersService usersService;

    @GetMapping("/list")
    @PreAuthorize("hasAuthority('READ_ACCESS')")
    public ResponseEntity<List<Users>> getAllUsers() {
        List<Users> users = basicBeanRemote.findAll(Users.class);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/me")
    public ResponseEntity<String> getCurrentUser() {
        return ResponseEntity.ok("This is your profile info");
    }

    @PostMapping("/{username}/addRole/{roleName}")
    @PreAuthorize("hasAuthority('UPDATE_ACCESS')")
    public ResponseEntity<Users> addRoleToUser(@PathVariable String username, @PathVariable String roleName) {
        Users updatedUser = usersService.addRoleToUser(username, roleName);
        return ResponseEntity.ok(updatedUser);
    }
}