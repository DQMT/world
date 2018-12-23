package xyz.tincat.host.world.clean;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import xyz.tincat.host.world.store.Cache;
import xyz.tincat.host.world.util.FileUtil;


@Component
@Slf4j
public class Cleaner {
    @Value("${save.folder}")
    private String defaultSaveFolder;

    @Scheduled(cron = "${cleaner.cron}")
    public void cleanGiveMe() {
        log.info("cleaner is working for : {}!", "GiveMe");
        Cache.getCache().clearMap("giveme");
        FileUtil.clearDir(defaultSaveFolder);
    }
}
