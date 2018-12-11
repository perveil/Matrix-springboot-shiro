package org.tustcs.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tustcs.shiro.pojo.ResponseBean;

@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/hello")
    public ResponseBean hello() {
        return new ResponseBean().ok().addMeta("hello", "macx!");
    }
}
