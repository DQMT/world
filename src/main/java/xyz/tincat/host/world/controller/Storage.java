package xyz.tincat.host.world.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import xyz.tincat.host.world.service.DownloadService;
import xyz.tincat.host.world.store.Cache;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;

/**
 * This page can storage something in a short time.
 *
 * @ Date       ：Created in 15:17 2018/12/22
 * @ Modified By：
 * @ Version:     0.1
 */
@Controller
@RequestMapping("/storage")
@Slf4j
public class Storage {

    @Autowired
    private DownloadService downloadService;

    private Map<String, String> map;

    public Storage() {
        map = Cache.getCache().createMap("giveme");
    }

    @RequestMapping("")
    public String storage(Model model) {
        return "storage";
    }

    @RequestMapping("upload")
    @ResponseBody
    public String upload(@RequestParam String path) {
        return "";
    }

    @RequestMapping("download")
    public void download(HttpServletRequest req, HttpServletResponse resp) {

    }

    @RequestMapping("process")
    @ResponseBody
    public long getProcess(@RequestParam String path) {
        log.debug("get process! file url=" + path);
        return downloadService.getProcess(path);
    }

}