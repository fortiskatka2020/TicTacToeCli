package com.tictactoecli.tictactoecli;

import com.tictactoecli.tictactoecli.components.MyRunner;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.util.TestContextResourceUtils;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Scanner;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@TestConfiguration
class TictactoecliApplicationTests {

    @MockBean
    private MyRunner myRunner;

    @Test
    void contextLoads() {

    }

}
