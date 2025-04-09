package com.lyranxi.link.user.service;


import com.lyranxi.link.user.UserApplication;
import com.lyranxi.link.user.biz.UserBiz;
import com.lyranxi.link.user.bo.user.UserRegisterBO;
import com.lyranxi.link.web.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@Slf4j
@SpringBootTest(classes = UserApplication.class)
public class UserServiceTest {

    @Autowired
    private UserBiz userBiz;

    @Autowired
    private UserService userService;


    @Test
    public void signup_UniqueUsernameAndPhone_Success() {
        UserRegisterBO signupBO = new UserRegisterBO();
        signupBO.setTenantId(1L);
        signupBO.setStoreId(1L);
        signupBO.setUsername("user2");
        signupBO.setPhone("12345678901");
        signupBO.setPassword("password");
        signupBO.setOperatorId(1L);

    }

    public static void main(String[] args) {
        System.out.println(1660963 / 1024 / 1024);
        System.out.println(1024 * 1024);
    }

}
