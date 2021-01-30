package io.github.kimmking.gateway.router;

import io.github.kimmking.gateway.config.ProxyProperties;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * @program: JavaCourseCodes
 * @author: zhangxidong
 * @create: 2021-01-27
 **/

@Activate
public class RoundRibbonHttpEndpointRouter extends HttpEndpointRouter {

    private final AtomicInteger count = new AtomicInteger();

    @Override
    public String route() {
        List<String> urls = this.getProxyProperties().stream()
                .map(ProxyProperties::getHost)
                .collect(Collectors.toList());
        return urls.get(count.incrementAndGet() % urls.size());
    }
}
