package xyz.tincat.host.world.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import xyz.tincat.host.world.config.WorldResources;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * A simple script used to disable Windows Screen Saver
 * @ Date       ：Created in 10:14 2019/2/15
 * @ Modified By：
 * @ Version:     0.1
 */
@Controller
@RequestMapping("/pbkiller")
@Slf4j
public class PbKiller {

    private static String PBKILLER_FILE = "pbkiller.vbs";
    @RequestMapping("")
    public String pbkiller(Model model) {
        return "pbkiller";
    }

    @RequestMapping("rec")
    public void  rec(HttpServletRequest req, HttpServletResponse resp){
        String fileName = req.getParameter("file");
        if (!"pbkiller".equals(fileName)) {
            return;
        }
        InputStream fis = WorldResources.getResourcesFile(PBKILLER_FILE);
        resp.setContentType("application/octet-stream");
        resp.setHeader("Content-Disposition","attachment;filename=" + PBKILLER_FILE);
        try {
            resp.setContentLength(fis.available());
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
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
                if (fis != null) {
                    fis.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}