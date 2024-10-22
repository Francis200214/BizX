package com.biz.security.demo.test;

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

    @BeforeEach
    public void setUp() {
        username = "validUser";
        password = "validPassword";
    }

    /**
     * 测试登录成功
     */
    @Test
    public void loginSuccessful() throws Exception {
        mockMvc.perform(post("/api/login")
                        .header("X-Auth-Type", "USERNAME_PASSWORD")
                        .param("username", username)
                        .param("password", password))
                .andExpect(status().isOk());
    }

    /**
     * 测试认证类型失败
     */
    @Test
    public void loginAuthTypeFail() throws Exception {
        mockMvc.perform(post("/api/login")
                        .header("X-Auth-Type", "USERNAME_PASSWORD1")
                        .param("username", username)
                        .param("password", password))
                .andExpect(status().isOk());
    }


}
