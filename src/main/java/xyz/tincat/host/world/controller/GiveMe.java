package xyz.tincat.host.world.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import xyz.tincat.host.world.store.Cache;
import xyz.tincat.host.world.service.DownloadService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;

/**
 * @ Date       ：Created in 15:17 2018/12/22
 * @ Modified By：
 * @ Version:     0.1
 */
@Controller
@RequestMapping("/giveme")
@Slf4j
public class GiveMe {

    @Autowired
    private DownloadService downloadService;

    private Map<String, String> map;

    public GiveMe() {
        map = Cache.getCache().createMap("giveme");
    }

    @RequestMapping("")
    public String giveme(Model model) {
        return "giveme";
    }

    @RequestMapping("req")
    @ResponseBody
    public String req(@RequestParam String path) {
        log.info("get req! file url=" + path);
        String fileName = null;
        try {
            String s = downloadService.downLoadFromUrl(path);
            fileName = s.substring(s.lastIndexOf(File.separator) + 1);
            map.put(fileName, s);
        } catch (Exception e) {
            fileName = null;
            e.printStackTrace();
        }
        return fileName;
    }

    @RequestMapping("rec")
    public void  rec(HttpServletRequest req, HttpServletResponse resp){
        String fileName = req.getParameter("file");
        if (fileName == null) {
            return;
        }
        String savePath = map.get(fileName);
        if (savePath == null) {
            log.warn("can get savePath for: {} ,maybe it's deleted.", fileName);
            return;
        }
        File file = new File(savePath);
        resp.setContentType("application/octet-stream");
        resp.setHeader("Content-Disposition","attachment;filename=" + fileName);
        resp.setContentLength((int) file.length());
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
            byte[] buffer = new byte[128];
            int count = 0;
            while ((count = fis.read(buffer)) > 0) {
                resp.getOutputStream().write(buffer, 0, count);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                resp.getOutputStream().flush();
                resp.getOutputStream().close();
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @RequestMapping("process")
    @ResponseBody
    public long getProcess(@RequestParam String path) {
        log.info("get process! file url=" + path);
        return downloadService.getProcess(path);
    }

}