package com.example.bank.service.util;

import com.example.bank.dto.UserDto;
import com.example.bank.service.UserService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

@Sql(scripts = {"/before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = {"/after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@SpringBootTest
@FieldDefaults(level = AccessLevel.PROTECTED)
public class AbstractIntegrationTest extends AbstractJUnit4SpringContextTests {

    MockMvc mvc;
    MockMvc mvcWithoutAuth;
    @Autowired
    WebApplicationContext context;
    @Autowired
    UserService userService;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
        mvcWithoutAuth = MockMvcBuilders
                .webAppContextSetup(context)
                .build();

        UserDto user = userService.getUsers().stream().findAny().get().iterator().next();
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(new UsernamePasswordAuthenticationToken(user, null));
        SecurityContextHolder.setContext(securityContext);
    }
}
