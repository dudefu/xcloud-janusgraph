package com.xinyi.info.janusgraph.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class CacheClearSchedule {

    @Autowired
    private ClusterCache clusterCache;

    @Scheduled(cron = "0 */30 * * * *")
    public void clearCache() {
        clusterCache.clear(30 * 60);
    }
}
