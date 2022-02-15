package com.example.bank.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
@Tag(name = "IndexController")
public class IndexController {

    @GetMapping
    public String index() {
        return "index.html";
    }
}
