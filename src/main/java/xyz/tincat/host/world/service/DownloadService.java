package xyz.tincat.host.world.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.tika.mime.MimeTypeException;
import org.apache.tika.mime.MimeTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.tincat.host.world.config.WorldConstants;
import xyz.tincat.host.world.store.Cache;
import xyz.tincat.host.world.util.FileUtil;
import xyz.tincat.host.world.util.HttpDownloader;
import xyz.tincat.host.world.util.StringUtil;

import java.io.*;
import java.net.URLConnection;
import java.util.Map;

/**
 * @ Date       ：Created in 17:07 2018/12/22
 * @ Modified By：
 * @ Version:     0.1
 */
@Service
@Slf4j
public class DownloadService {

    @Autowired
    private WorldConstants constants;

    private Map<String, Long> map;

    public DownloadService() {
        map = Cache.getCache().createLongMap("download");
    }

    public String downLoadFromUrl(String urlStr) throws Exception {
        String fileName = FileUtil.guessFilenameFromUrl(urlStr);
        String savePath = constants.saveFolder == null ? "D://worldDownload//" : constants.saveFolder;
        //文件保存位置
        File saveDir = new File(savePath);
        if (!saveDir.exists()) {
            saveDir.mkdir();
        }
        return downLoadFromUrl(urlStr, fileName, savePath);
    }

    /**
     * 从网络Url中下载文件
     *
     * @param urlStr
     * @param fileName
     * @param savePath
     * @throws IOException
     */
    public String downLoadFromUrl(final String urlStr, final String fileName, String savePath) throws IOException {
        if (!savePath.endsWith(File.separator)) {
            savePath = savePath + File.separator;
        }
        log.info("url = {}", urlStr);
        log.info("fileName = {}", fileName);
        log.info("savePath = {}", savePath);
        String destFile = savePath + fileName;
        HttpDownloader downloader = new HttpDownloader(urlStr, destFile);
        downloader.download(new HttpDownloader.Callback() {
            @Override
            public void onProgress(long progress) {
                map.put(urlStr, progress);
                log.debug("onProgress for {} : {}", urlStr, String.valueOf(map.get(urlStr)));
            }

            @Override
            public void onFinish() {
                log.info("onFinish");
            }

            @Override
            public void onError(IOException ex) {
                log.error("onError");
                ex.printStackTrace();
            }
        });
        log.info("file: {} download success", urlStr);
        log.info("save: {}", destFile);
        return destFile;
    }

    public long getProcess(String fileUrl) {
        Long process = map.get(fileUrl);
        log.debug("get process for " + fileUrl + " : " + process);
        return process == null ? 0 : process;
    }

    public static void main(String[] args) {
        try {
            DownloadService downloadService = new DownloadService();
            String s = downloadService.downLoadFromUrl("https://avatar.csdn.net/9/B/0/3_xb12369.jpg");
            System.out.println(s);
        } catch (Exception e) {
        }
    }

}