package org.acme;

import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.net.SocketAddress;
import io.vertx.ext.web.Router;
import io.vertx.httpproxy.HttpProxy;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;

@ApplicationScoped
public class VertProxy {
    @Inject
    Vertx vertx;

    void init(@Observes Router router) {
        var proxyClient = vertx.createHttpClient();
        var proxy = HttpProxy.reverseProxy(proxyClient);
        proxy.originSelector(httpServerRequest -> {
            return Future.succeededFuture(
                    SocketAddress.inetSocketAddress(80, httpServerRequest.uri()));
        });


        router.route("/*").handler(rc -> proxy.handle(rc.request()));
    }
}
