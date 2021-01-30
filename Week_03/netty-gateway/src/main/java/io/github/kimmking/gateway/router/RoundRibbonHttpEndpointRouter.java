package io.github.kimmking.gateway.router;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @program: JavaCourseCodes
 * @author: zhangxidong
 * @create: 2021-01-27
 **/

public class RoundRibbonHttpEndpointRouter implements HttpEndpointRouter {

    private AtomicInteger count = new AtomicInteger();

    @Override
    public String route(List<String> endpoints) {
        return endpoints.get(count.incrementAndGet() % endpoints.size());
    }
}
