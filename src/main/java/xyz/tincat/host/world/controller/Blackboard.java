package xyz.tincat.host.world.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * A simple blackboard to keep some short message for you
 *
 * @ Date       ：Created in 15:42 2019/1/15
 * @ Modified By：
 * @ Version:     0.1
 */
@Controller
@RequestMapping("/blackboard")
@Slf4j
public class Blackboard {
    private static StringBuilder stringBuilder;

    @RequestMapping("")
    public String blackboard(Model model) {
        model.addAttribute("s", ShortTest.get());
        model.addAttribute("k", ShortTest.getKey());
        return "blackboard";
    }

    @RequestMapping("go")
    public void go(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        String s = httpServletRequest.getParameter("s");
        ShortTest.set(s);
    }

    @RequestMapping("get")
    @ResponseBody
    public String gets(){
        String s = ShortTest.get();
        return s;
    }

    @RequestMapping("set")
    @ResponseBody
    public String sets(HttpServletRequest httpServletRequest){
        String s = httpServletRequest.getParameter("s");
        if (s == null) {
            return "null";
        }
        ShortTest.set(s);
        return "done";
    }

    @RequestMapping("snk")
    public void sneak(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        String s = httpServletRequest.getParameter("s");
        if (stringBuilder == null) {
            stringBuilder = new StringBuilder();
        }
        if (s == null) {
            stringBuilder = new StringBuilder();
            return;
        }
        if (s.startsWith("^")) {
            stringBuilder = new StringBuilder(s);
            ShortTest.setTIme();
        } else {
            stringBuilder.append(s);
        }
        if (s.endsWith("$")) {
            ShortTest.set(stringBuilder.toString().substring(1, stringBuilder.length() - 1));
        }
    }
}

class ShortTest {
    private static String s;
    private static long datetime;

    public static String get() {
        if (datetime == 0) {
            datetime = System.currentTimeMillis();
            s = "";
            return "";
        }
        if (System.currentTimeMillis() - datetime > 1000 * 60 * 30) {
            s = "";
            return "";
        }
        return s;
    }

    public static void set(String string) {
        s = string;
        datetime = System.currentTimeMillis();
    }

    public static void setTIme() {
        datetime = System.currentTimeMillis();
    }

    public static int getKey() {
        return (int) (datetime / (1000 * 60 * 33));
    }
}