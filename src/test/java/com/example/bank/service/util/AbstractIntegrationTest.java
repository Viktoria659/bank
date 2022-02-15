package com.example.bank.service.util;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

@Sql(scripts = {"/before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
//@Sql(scripts = {"/after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@SpringBootTest
public class AbstractIntegrationTest {
}
