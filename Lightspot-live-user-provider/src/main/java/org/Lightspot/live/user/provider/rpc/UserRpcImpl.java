package org.Lightspot.live.user.provider.rpc;

import org.Lightspot.live.user.interfaces.IUserRpc;
import org.apache.dubbo.config.annotation.DubboService;

@DubboService
public class UserRpcImpl implements IUserRpc {
    @Override
    public String test() {
        System.out.println("dubbo-test");
        return "dubbo-test";
    }
}
