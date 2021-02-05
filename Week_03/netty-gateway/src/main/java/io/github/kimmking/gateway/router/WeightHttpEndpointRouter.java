package io.github.kimmking.gateway.router;

import io.github.kimmking.gateway.config.ProxyProperties;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @program: JavaCourseCodes
 * @author: zhangxidong
 * @create: 2021-01-27
 **/

public class WeightHttpEndpointRouter extends HttpEndpointRouter {

    private List<String> urlList = new ArrayList<>();
    private int range;

    public WeightHttpEndpointRouter() {
        int range = 0;
        List<ProxyProperties> proxyList = getProxyProperties();
        for (ProxyProperties proxy : proxyList) {
            for (int i = 0; i < proxy.getWeight(); i++) {
                urlList.add(proxy.getHost());
            }
            range += proxy.getWeight();
        }
        this.range = range;
    }

    @Override
    public String route() {
        Random random = new Random(System.currentTimeMillis());
        return urlList.get(random.nextInt(range));
    }
}
