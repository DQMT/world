package xyz.tincat.host.world.config;

import java.util.Objects;

/**
 * @ Date       ：Created in 10:58 2019/2/15
 * @ Modified By：
 * @ Version:     0.1
 */
public class WorldResources {

    public static String getResourcesFile(String filename) {
        return Objects.requireNonNull(WorldResources.class.getClassLoader().getResource("files/" + filename)).getFile();
    }
}