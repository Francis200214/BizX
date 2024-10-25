package com.biz.security.demo.test;

import com.biz.common.jwt.JwtCreateHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * 认证测试
 *
 * @author francis
 * @create 2024-10-18
 **/
@SpringBootTest
@AutoConfigureMockMvc
public class AuthenticationTest {

    @Autowired
    private MockMvc mockMvc;


    private String username;
    private String password;
    private String token;

    @BeforeEach
    public void setUp() {
        username = "validUser";
        password = "validPassword";
        token = JwtCreateHelper.builder()
                .secret("secret_secret_secret_secret")
                .data("userId", "111")
                .build()
                .createToken();
    }

    /**
     * 测试用户名密码认证成功
     */
    @Test
    public void loginByUPSuccessful() throws Exception {
        mockMvc.perform(post("/api/login")
                        .header("X-Auth-Type", "USERNAME_PASSWORD")
                        .param("username", username)
                        .param("password", password))
                .andExpect(status().isOk());
    }


    /**
     * 测试用户名密码认证 用户名为null
     */
    @Test
    public void loginUsernameIsNull() throws Exception {
        mockMvc.perform(post("/api/login")
                        .header("X-Auth-Type", "USERNAME_PASSWORD")
                        .param("username", (String) null)
                        .param("password", password))
                .andExpect(status().isOk());
    }


    /**
     * 测试用户名密码认证 密码为null
     */
    @Test
    public void loginPasswordIsNull() throws Exception {
        mockMvc.perform(post("/api/login")
                        .header("X-Auth-Type", "USERNAME_PASSWORD")
                        .param("username", username)
                        .param("password", (String) null))
                .andExpect(status().isOk());
    }


    /**
     * 测试Token认证 成功
     */
    @Test
    public void loginByTokenSuccessful() throws Exception {
        mockMvc.perform(post("/api/tokenLogin")
                        .header("X-Auth-Type", "TOKEN")
                        .header("X-Token", token))
                .andExpect(status().isOk());
    }


    /**
     * 测试Token认证 token为null
     */
    @Test
    public void loginTokenIsNull() throws Exception {
        mockMvc.perform(post("/api/tokenLogin")
                        .header("X-Auth-Type", "TOKEN"))
                .andExpect(status().isOk());
    }



    /**
     * 测试未知认证
     */
    @Test
    public void loginNotFoundAuthType() throws Exception {
        mockMvc.perform(post("/api/login")
                        .header("X-Auth-Type", "USERNAME_PASSWORD1")
                        .param("username", username)
                        .param("password", password))
                .andExpect(status().isOk());
    }


    /**
     * 测试没有认证类型
     */
    @Test
    public void loginAuthTypeIsNull() throws Exception {
        mockMvc.perform(post("/api/login")
                        .param("username", username)
                        .param("password", password))
                .andExpect(status().isOk());
    }



}
