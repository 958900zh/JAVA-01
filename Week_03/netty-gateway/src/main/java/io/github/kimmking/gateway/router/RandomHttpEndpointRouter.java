package io.github.kimmking.gateway.router;

import io.github.kimmking.gateway.config.ProxyProperties;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class RandomHttpEndpointRouter extends HttpEndpointRouter {

    @Override
    public String route() {
        List<String> urls = this.getProxyProperties().stream()
                .map(ProxyProperties::getHost)
                .collect(Collectors.toList());
        int size = urls.size();
        Random random = new Random(System.currentTimeMillis());
        return urls.get(random.nextInt(size));
    }
}
