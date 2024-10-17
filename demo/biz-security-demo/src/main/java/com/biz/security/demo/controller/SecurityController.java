package com.biz.security.demo.controller;

import com.biz.common.jwt.JwtCreateHelper;
import com.biz.security.authorization.enums.SecuredAccess;
import com.biz.security.user.UserDetails;
import com.biz.security.user.store.SecurityContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequestMapping("/api")
class SecurityController {


    @Autowired
    private SecurityContextHolder securityContextHolder;


    // 用户登录接口
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam("username") String username, @RequestParam("password") String password) {
        String token = JwtCreateHelper.builder()
                .data("userId", "111")
                .secret("secret_secret_secret_secret")
                .build()
                .createToken();
        log.info("token {}", token);
        try {

            return ResponseEntity.ok("Login successful for user: " + username);
        } catch (Exception e) {
            log.error("Login failed: {}", e.getMessage());
            return ResponseEntity.status(401).body("Login failed: " + e.getMessage());
        }
    }

    // 用户登出接口
    @PostMapping("/logout")
    public ResponseEntity<String> logout() {
        log.info("Logging out user...");
        return ResponseEntity.ok("Logout successful");
    }

    // 添加用户（需要 AddUser 权限）
    @SecuredAccess(hasAuthority = "AddUser")
    @PostMapping("/user/add")
    public ResponseEntity<String> addUser(@RequestBody UserDetails newUser) {
        log.info("Adding user: {}", newUser);
        return ResponseEntity.ok("User added successfully: " + newUser.getUsername());
    }

    // 删除用户（需要 DeleteUser 权限）
    @SecuredAccess(hasAuthority = "DeleteUser")
    @DeleteMapping("/user/delete/{username}")
    public ResponseEntity<String> deleteUser(@PathVariable String username) {
        log.info("Deleting user: {}", username);
        return ResponseEntity.ok("User deleted successfully: " + username);
    }

    // 查看新闻（不需要登录就可以访问）
    @GetMapping("/news")
    @SecuredAccess(requiresAuthentication = false)
    public ResponseEntity<String> viewNews() {
        log.info("Viewing news...");
        return ResponseEntity.ok("Here is the latest news");
    }

    // 查询订单列表（需要 Order 角色）
    @SecuredAccess(hasRole = "Order")
    @GetMapping("/orders")
    public ResponseEntity<String> getOrderList() {
        log.info("Getting order list...");
        return ResponseEntity.ok("Order list: [Order1, Order2, Order3]");
    }

    // 获取当前用户信息（需要登录）
    @SecuredAccess
    @GetMapping("/user/info")
    public ResponseEntity<UserDetails> getUserInfo() {
        log.info("Getting user info...");
        UserDetails currentUser = securityContextHolder.getContext();
        if (currentUser == null) {
            return ResponseEntity.status(403).body(null);
        }
        return ResponseEntity.ok(currentUser);
    }

}
