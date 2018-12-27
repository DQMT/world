package xyz.tincat.host.world.util;

import lombok.extern.slf4j.Slf4j;

import java.io.File;

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

    public static boolean sameFileType(String fileName, String etx) {
        if (fileName.endsWith(etx)) {
            return true;
        }
        if (etx.equals(".bin") && fileName.endsWith(".exe")) {
            return true;
        }
        return false;
    }

}
