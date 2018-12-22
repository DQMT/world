package xyz.tincat.host.world.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @ Date       ：Created in 17:07 2018/12/22
 * @ Modified By：
 * @ Version:     0.1
 */
@Component
public class DownloadUtil {


    @Value("${save.path}")
    private String defaultSavePath;

    public String downLoadFromUrl(String urlStr) throws IOException {
        String[] sp = urlStr.split("/");
        String filename = sp[sp.length - 1];
        String savePath = defaultSavePath == null ? "D:/" : defaultSavePath;
        return downLoadFromUrl(urlStr, filename, savePath);
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
        File file = new File(saveDir + File.separator + fileName);
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(getData);
        if (fos != null) {
            fos.close();
        }
        if (inputStream != null) {
            inputStream.close();
        }
        System.out.println("info:" + url + " download success");
        return saveDir + File.separator + fileName;
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