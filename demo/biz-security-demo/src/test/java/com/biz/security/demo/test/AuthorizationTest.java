package com.biz.security.demo.test;

import com.biz.common.jwt.JwtCreateHelper;
import com.biz.security.user.UserDetails;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * 权限测试
 *
 * @author francis
 * @create 2024-10-25
 * @since 1.0.1
 **/
@SpringBootTest
@AutoConfigureMockMvc
public class AuthorizationTest {


    @Autowired
    private MockMvc mockMvc;


    private String token;

    @BeforeEach
    public void setUp() {
        token = JwtCreateHelper.builder()
                .secret("secret_secret_secret_secret")
                .data("userId", "111")
                .build()
                .createToken();
    }

    /**
     * 测试有权限 （需要 AddUser 权限）
     */
    @Test
    public void testHavePermission() throws Exception {
        mockMvc.perform(get("/api/user/add")
                        .header("X-Auth-Type", "TOKEN")
                        .header("X-Token", token)
                )
                .andExpect(status().isOk());
    }

    /**
     * 测试没有权限 （需要 ChangeUser 权限）
     */
    @Test
    public void testHaveNotPermission() throws Exception {
        mockMvc.perform(put("/api/user/change")
                        .header("X-Auth-Type", "TOKEN")
                        .header("X-Token", token)
                        .param("username", "testUser")
                )
                .andExpect(status().isOk());
    }


    /**
     * 测试有角色（需要 USER 权限）
     */
    @Test
    public void testHaveRole() throws Exception {
        mockMvc.perform(get("/api/users")
                        .header("X-Auth-Type", "TOKEN")
                        .header("X-Token", token)
                )
                .andExpect(status().isOk());
    }

    /**
     * 测试没有角色（需要 ORDER 角色）
     */
    @Test
    public void testHaveNotRole() throws Exception {
        mockMvc.perform(get("/api/orders")
                        .header("X-Auth-Type", "TOKEN")
                        .header("X-Token", token)
                )
                .andExpect(status().isOk());
    }



    /**
     * 测试不需要登录（无需登录）
     */
    @Test
    public void testMustLogin() throws Exception {
        mockMvc.perform(get("/api/news"))
                .andExpect(status().isOk())
                .andExpect(content().string("Here is the latest news"));
    }


    /**
     * 测试不需要登录（无需登录）
     */
    @Test
    public void testNoLogin() throws Exception {
        mockMvc.perform(get("/api/news"))
                .andExpect(status().isOk())
                .andExpect(content().string("Here is the latest news"));
    }

}
