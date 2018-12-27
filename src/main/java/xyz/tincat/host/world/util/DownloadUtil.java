package xyz.tincat.host.world.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.tika.mime.MimeTypeException;
import org.apache.tika.mime.MimeTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xyz.tincat.host.world.config.WorldConstants;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

/**
 * @ Date       ：Created in 17:07 2018/12/22
 * @ Modified By：
 * @ Version:     0.1
 */
@Component
@Slf4j
public class DownloadUtil {

    @Autowired
    private WorldConstants constants;

    public String downLoadFromUrl(String urlStr) throws Exception {
        String[] sp = urlStr.split("/");
        String fileName = sp[sp.length - 1];
        String fileType = URLConnection.guessContentTypeFromName(urlStr);
        MimeTypes allTypes = MimeTypes.getDefaultMimeTypes();
        String ext = fileName;
        try {
            ext = allTypes.forName(fileType).getExtension();
            log.info("guess file extension = {}", ext);
        } catch (MimeTypeException e) {
            e.printStackTrace();
        }
        if (!FileUtil.sameFileType(fileName, ext)) {
            fileName = StringUtil.newRandomString(10) + "." + fileType;
        }
        String savePath = constants.saveFolder == null ? "D://worldDownload//" : constants.saveFolder;
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
    public String downLoadFromUrl(String urlStr, String fileName, String savePath) throws IOException {
        log.info("url = {}", urlStr);
        log.info("fileName = {}", fileName);
        log.info("savePath = {}", savePath);
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        //设置超时间为3秒
        conn.setConnectTimeout(3 * 1000);
        //防止屏蔽程序抓取而返回403错误
        conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");

        //得到输入流
        InputStream inputStream = conn.getInputStream();
        //获取自己数组
        byte[] getData = readInputStream(inputStream);

        //文件保存位置
        File saveDir = new File(savePath);
        if (!saveDir.exists()) {
            saveDir.mkdir();
        }
        File file;
        FileOutputStream fos;
        file = new File(saveDir + File.separator + fileName);
        fos = new FileOutputStream(file);
        fos.write(getData);
        if (fos != null) {
            fos.close();
        }
        if (inputStream != null) {
            inputStream.close();
        }
        String save = saveDir + File.separator + fileName;
        log.info("file: {} download success", url);
        log.info("save: {}", save);
        return save;
    }


    /**
     * 从输入流中获取字节数组
     *
     * @param inputStream
     * @return
     * @throws IOException
     */
    public byte[] readInputStream(InputStream inputStream) throws IOException {
        byte[] buffer = new byte[1024];
        int len = 0;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        while ((len = inputStream.read(buffer)) != -1) {
            bos.write(buffer, 0, len);
        }
        bos.close();
        return bos.toByteArray();
    }

    public static void main(String[] args) {
        try {
            DownloadUtil downloadUtil = new DownloadUtil();
            String s = downloadUtil.downLoadFromUrl("https://avatar.csdn.net/9/B/0/3_xb12369.jpg");
            System.out.println(s);
        } catch (Exception e) {
        }
    }

}