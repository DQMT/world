package xyz.tincat.host.world.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.tika.mime.MimeTypeException;
import org.apache.tika.mime.MimeTypes;

import java.io.File;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;

@Slf4j
public class FileUtil {

    public static boolean clearDir(String dir) {
        File file = new File(dir);
        if (!file.exists()) {//判断待删除目录是否存在
            log.error("The dir {} not exists!", dir);
            return false;
        }
        if (!file.isDirectory()) {//不是目录就不删除
            log.error("The dir {} is not directory!", dir);
            return false;
        }
        String[] content = file.list();//取得当前目录下所有文件和文件夹
        for (String name : content) {
            File temp = new File(dir, name);
            if (temp.isDirectory()) {//判断是否是目录
                clearDir(temp.getAbsolutePath());//递归调用，删除目录里的内容
                temp.delete();//删除空目录
            } else {
                if (!temp.delete()) {//直接删除文件
                    log.error("Failed to delete {}", name);
                }
            }
        }
        return true;
    }

    public static boolean acceptFilename(String fileName, String fileType, String etx) {
        if (fileName.endsWith(etx)) {
            return true;
        }
        if ("application/octet-stream".equals(fileType)) {
            return true;
        }
        if (etx.equals(".bin") && fileName.endsWith(".exe")) {
            return true;
        }
        return true;
    }


    public static String guessFilenameFromUrl(String urlStr) {
        log.info("url = {}", urlStr);
        String[] sp = urlStr.split("/");
        String filename = sp[sp.length - 1];
        log.info("guess filename from urlStr = {}", filename);
        String fileType = URLConnection.guessContentTypeFromName(urlStr);
        log.info("guess fileType from urlStr = {}", fileType);
        MimeTypes allTypes = MimeTypes.getDefaultMimeTypes();
        String ext = null;
        try {
            ext = allTypes.forName(fileType).getExtension();
            log.info("guess file extension from urlStr = {}", ext);
        } catch (MimeTypeException e) {
            e.printStackTrace();
        }
        if (!FileUtil.acceptFilename(filename,fileType, ext)) {
            filename = StringUtil.newRandomString(10) + "." + fileType;
        }
        return filename;
    }


    /**
     * 依赖Content-Disposition
     *
     * @param urlStr
     * @return
     */
    public static String getFileName(String urlStr) {
        String fileName = null;
        try {
            URL url = new URL(urlStr);
            URLConnection uc = url.openConnection();
            fileName = uc.getHeaderField("Content-Disposition");
            fileName = new String(fileName.getBytes("ISO-8859-1"), "GBK");
            fileName = URLDecoder.decode(fileName.substring(fileName.indexOf("filename=") + 9), "UTF-8");
            log.info("文件名为：" + fileName + "  大小" + (uc.getContentLength() / 1024) + "KB");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileName;
    }

}
