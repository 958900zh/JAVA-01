package io.github.kimmking.gateway.filter.response;

import io.netty.handler.codec.http.FullHttpResponse;

public class HeaderHttpResponseFilter implements HttpResponseFilter {
    @Override
    public void filter(FullHttpResponse response) {
        response.headers().set("kk", "java-1-nio");
        System.out.println("HeaderHttpResponseFilter execute");
    }
}
