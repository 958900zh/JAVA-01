package io.github.kimmking.gateway.filter.response;

import io.github.kimmking.gateway.inbound.HttpInboundHandler;
import io.netty.handler.codec.http.FullHttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @program: JavaCourseCodes
 * @author: zhangxidong
 * @create: 2021-01-27
 **/

public class LastHttpResponseFilter implements HttpResponseFilter {
    private static Logger logger = LoggerFactory.getLogger(HttpInboundHandler.class);

    @Override
    public void filter(FullHttpResponse response) {
        System.out.println("I'm LastHttpResponseFilter, I'm always the last to execute o(╥﹏╥)o");
    }

    @Override
    public int order() {
        return Integer.MIN_VALUE;
    }
}
