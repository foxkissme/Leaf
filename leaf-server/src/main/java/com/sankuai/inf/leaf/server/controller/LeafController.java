package com.sankuai.inf.leaf.server.controller;

import com.cxytiandi.kitty.id.service.DistributedIdLeafSegmentRemoteService;
import com.cxytiandi.kitty.id.service.DistributedIdLeafSnowflakeRemoteService;
import com.sankuai.inf.leaf.common.Result;
import com.sankuai.inf.leaf.common.Status;
import com.sankuai.inf.leaf.server.exception.LeafServerException;
import com.sankuai.inf.leaf.server.exception.NoKeyException;
import com.sankuai.inf.leaf.server.service.SegmentService;
import com.sankuai.inf.leaf.server.service.SnowflakeService;
import org.apache.dubbo.config.annotation.DubboService;
import org.apache.dubbo.config.annotation.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@DubboService(version = "1.0.0", group = "default")
@RestController
public class LeafController implements DistributedIdLeafSnowflakeRemoteService, DistributedIdLeafSegmentRemoteService {

    @Autowired
    private SegmentService segmentService;
    @Autowired
    private SnowflakeService snowflakeService;

    @Override
    public String getSegmentId(@PathVariable("key") String key) {
        return get(segmentService.getId(key));
    }

    @Override
    public String getSnowflakeId(@PathVariable("key") String key) {
        return get(snowflakeService.getId(key));
    }

    private String get(Result id) {
        if (id.getStatus().equals(Status.EXCEPTION)) {
            throw new LeafServerException(id.toString());
        }
        return String.valueOf(id.getId());
    }
}
