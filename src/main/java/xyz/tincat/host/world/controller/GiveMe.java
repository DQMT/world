package xyz.tincat.host.world.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import xyz.tincat.host.world.util.DownloadUtil;

import java.io.IOException;
import java.util.Map;

/**
 * @ Date       ：Created in 15:17 2018/12/22
 * @ Modified By：
 * @ Version:     0.1
 */
@Controller
@RequestMapping("/world")
public class GiveMe {

    @Autowired
    private DownloadUtil downloadUtil;

    @RequestMapping("giveme")
    public String giveme(Model model) {
        model.addAttribute("s", "Give");
        model.addAttribute("k", "Me");
        return "giveme";
    }

    @RequestMapping("req")
    public String req(Model model,@RequestParam String path) {
        System.out.println("get req! fpath="+path );
        try {
            downloadUtil.downLoadFromUrl(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "giveme";
    }
}