package xyz.tincat.host.world.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @ Date       ：Created in 15:10 2018/12/25
 * @ Modified By：
 * @ Version:     0.1
 */
@Controller
@RequestMapping("/")
@Slf4j
public class Index {

    @RequestMapping("")
    public String index(){
        return "index";
    }
}