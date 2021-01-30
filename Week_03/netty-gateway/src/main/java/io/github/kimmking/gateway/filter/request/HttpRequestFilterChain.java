package io.github.kimmking.gateway.filter.request;

import io.github.kimmking.gateway.filter.request.HttpRequestFilter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import org.reflections.Reflections;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @program: JavaCourseCodes
 * @author: zhangxidong
 * @create: 2021-01-26
 **/

public class HttpRequestFilterChain {

    private List<HttpRequestFilter> filters = new ArrayList<>();

    public HttpRequestFilterChain() {
        Reflections reflections = new Reflections(HttpRequestFilter.class.getPackage().getName());
        Set<Class<? extends HttpRequestFilter>> classes = reflections.getSubTypesOf(HttpRequestFilter.class);

        for (Class<? extends HttpRequestFilter> clazz : classes) {
            try {
                filters.add(clazz.newInstance());
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        filters.sort((f1, f2) -> f2.order() - f1.order());
    }

    public void filter(FullHttpRequest fullRequest, ChannelHandlerContext ctx) {
        for (HttpRequestFilter filter : filters) {
            filter.filter(fullRequest, ctx);
        }
    }
}
