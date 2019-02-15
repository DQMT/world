package xyz.tincat.host.world.config;

import java.io.InputStream;

/**
 * @ Date       ：Created in 10:58 2019/2/15
 * @ Modified By：
 * @ Version:     0.1
 */
public class WorldResources {

    public static InputStream getResourcesFile(String filename) {
        return WorldResources.class.getResourceAsStream("/files/" + filename);
    }
}