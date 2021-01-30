package io.github.kimmking.gateway.filter.response;

import io.netty.handler.codec.http.FullHttpResponse;
import org.reflections.Reflections;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @program: JavaCourseCodes
 * @author: zhangxidong
 * @create: 2021-01-27
 **/

public class HttpResponseFilterChain {

    private final List<HttpResponseFilter> filters = new ArrayList<>();

    public HttpResponseFilterChain() {
        Reflections reflections = new Reflections(HttpResponseFilter.class.getPackage().getName());
        Set<Class<? extends HttpResponseFilter>> classes = reflections.getSubTypesOf(HttpResponseFilter.class);

        for (Class<? extends HttpResponseFilter> clazz : classes) {
            try {
                filters.add(clazz.newInstance());
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        filters.sort((f1, f2) -> f2.order() - f1.order());
    }

    public void filter(FullHttpResponse response) {
        for (HttpResponseFilter filter : filters) {
            filter.filter(response);
        }
    }
}
