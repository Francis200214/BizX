package com.biz.security.demo.test;

import com.biz.security.user.UserDetails;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class SecurityControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    private String username;
    private String password;

    @BeforeEach
    public void setUp() {
        username = "validUser";
        password = "validPassword";
    }

    // 测试用户登录
    @Test
    public void testLogin() throws Exception {
        mockMvc.perform(post("/api/login")
                        .header("X-Auth-Type", "USERNAME_PASSWORD")
                        .param("username", username)
                        .param("password", password))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Login successful for user")));
    }

    // 测试用户登出
    @Test
    public void testLogout() throws Exception {
        mockMvc.perform(post("/api/logout")
                        .header("X-Auth-Type", "TOKEN")
                        .header("X-Token", "eyJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE3Mjg5ODY3NTIsImV4cCI6MTcyOTA3Mjk5OSwidXNlcklkIjoiMTExIn0.xyjX837WIED4h6rf3TJzCLYoMGflGEO6DL6QmOlgPuU")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Logout successful"));
    }


    // 获取用户信息
    @Test
    public void getUserInfo() throws Exception {
        mockMvc.perform(get("/api/user/info")
                        .header("X-Auth-Type", "TOKEN")
                        .header("X-Token", "eyJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE3Mjg5ODY3NTIsImV4cCI6MTcyOTA3Mjk5OSwidXNlcklkIjoiMTExIn0.xyjX837WIED4h6rf3TJzCLYoMGflGEO6DL6QmOlgPuU")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Logout successful"));
    }

    // 测试查看新闻（无需登录）
    @Test
    public void testViewNews() throws Exception {
        mockMvc.perform(get("/api/news"))
                .andExpect(status().isOk())
                .andExpect(content().string("Here is the latest news"));
    }

    // 测试添加用户（需要 AddUser 权限）
    @Test
    public void testAddUserWithoutPermission() throws Exception {
        UserDetails newUser = UserDetails.builder().username("newUser").build();
        mockMvc.perform(post("/api/user/add")
                        .header("X-Auth-Type", "TOKEN")
                        .header("X-Token", "eyJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE3Mjg5ODY3NTIsImV4cCI6MTcyOTA3Mjk5OSwidXNlcklkIjoiMTExIn0.xyjX837WIED4h6rf3TJzCLYoMGflGEO6DL6QmOlgPuU")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newUser)))
                .andExpect(status().isOk())
                .andExpect(content().string("Access Denied: User is not authenticated"));
    }

    // 测试查询订单列表（需要 Order 角色）
    @Test
    public void testGetOrderListWithoutRole() throws Exception {
        mockMvc.perform(get("/api/orders")
                        .header("X-Auth-Type", "TOKEN")
                        .header("X-Token", "eyJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE3Mjg5ODY3NTIsImV4cCI6MTcyOTA3Mjk5OSwidXNlcklkIjoiMTExIn0.xyjX837WIED4h6rf3TJzCLYoMGflGEO6DL6QmOlgPuU")
                )
                .andExpect(status().isForbidden())
                .andExpect(content().string("Access Denied: User is not authenticated"));
    }

    // 测试删除用户（需要 DeleteUser 权限）
    @Test
    public void testDeleteUserWithoutPermission() throws Exception {
        mockMvc.perform(delete("/api/user/delete/testUser")
                        .header("X-Auth-Type", "TOKEN")
                        .header("X-Token", "eyJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE3Mjg5ODY3NTIsImV4cCI6MTcyOTA3Mjk5OSwidXNlcklkIjoiMTExIn0.xyjX837WIED4h6rf3TJzCLYoMGflGEO6DL6QmOlgPuU")
                )
                .andExpect(status().isForbidden())
                .andExpect(content().string("Access Denied: User is not authenticated"));
    }

}
