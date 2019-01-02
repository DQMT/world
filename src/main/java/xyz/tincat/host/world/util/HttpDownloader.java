package xyz.tincat.host.world.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class HttpDownloader {
    private final String url, destFilename;

    public HttpDownloader(String url, String destFilename) {
        this.url = url;
        this.destFilename = destFilename;
    }

    public void download(Callback callback) {
        try (FileOutputStream fos = new FileOutputStream(destFilename)) {
            URLConnection connection = new URL(url).openConnection();
            //设置超时间为3秒
            connection.setConnectTimeout(3 * 1000);
            //防止屏蔽程序抓取而返回403错误
            connection.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
            long fileSize = connection.getContentLengthLong();
            InputStream inputStream = connection.getInputStream();
            byte[] buffer = new byte[10 * 1024 * 1024];
            int numberOfBytesRead;
            long totalNumberOfBytesRead = 0;
            while ((numberOfBytesRead = inputStream.read(buffer)) != - 1) {
                fos.write(buffer, 0, numberOfBytesRead);
                totalNumberOfBytesRead += numberOfBytesRead;
                callback.onProgress(totalNumberOfBytesRead * 100 / fileSize);
            }
            callback.onFinish();
        } catch (IOException ex) {
            callback.onError(ex);
        }
    }

    public interface Callback {

        void onProgress(long progress);

        void onFinish();

        void onError(IOException ex);
    }
}
