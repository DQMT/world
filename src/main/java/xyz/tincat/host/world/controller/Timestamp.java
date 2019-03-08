package xyz.tincat.host.world.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @ Date       ：Created in 16:21 2019/3/8
 * @ Modified By：
 * @ Version:     0.1
 */
@Controller
@RequestMapping("/timestamp")
@Slf4j
public class Timestamp {

    @RequestMapping("")
    public String timestamp(Model model) {
        model.addAttribute("timezone", System.getProperty("user.timezone"));
        return "timestamp";
    }
}