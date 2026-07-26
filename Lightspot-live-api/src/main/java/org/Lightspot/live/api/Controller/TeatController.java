package org.Lightspot.live.api.Controller;

import org.Lightspot.live.user.interfaces.IUserRpc;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TeatController {
    @DubboReference

    private IUserRpc userRpc;
    @RequestMapping("/dubbo")
    public String dubbo() {
       return userRpc.test();
    }
}
