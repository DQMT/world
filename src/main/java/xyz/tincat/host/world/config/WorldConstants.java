package xyz.tincat.host.world.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @ Date       ：Created in 15:39 2018/12/25
 * @ Modified By：
 * @ Version:     0.1
 */

@Configuration
public class WorldConstants {

    @Value("${save.folder}")
    public String saveFolder;

}