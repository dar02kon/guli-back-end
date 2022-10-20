package com.dar.vod.config;

import org.apache.http.HeaderElement;
import org.apache.http.HeaderElementIterator;
import org.apache.http.client.RedirectStrategy;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultRedirectStrategy;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeaderElementIterator;
import org.apache.http.protocol.HTTP;
import org.apache.http.ssl.SSLContexts;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

/**
 * httpClient配置
 * <P>
 *    JDK版本过低需要setDefaultSocketConfig(socketConfig)
 *    设置SocketTimeout才能解决Socket超时的问题。
 * </P>
 */
@Configuration
public class HttpClientConfig {

    /**
     * 池连接管理
     * @param maxTotal 总连接数最大值
     * @param maxPerRoute 每个路由默认总连接数
     * @return PoolingHttpClientConnectionManager
     */
    @Bean(name = "poolingHttpClientConnectionManager")
    public PoolingHttpClientConnectionManager poolingHttpClientConnectionManager(
            @Value("${spider.pooling-http-client-connection-manager.max-total}") int maxTotal,
            @Value("${spider.pooling-http-client-connection-manager.default-max-per-route}") int maxPerRoute) {

        //兼容http以及https请求
        Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", PlainConnectionSocketFactory.INSTANCE)
                .register("https", initSSlFactory())
                .build();
        PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);

        SocketConfig socketConfig = SocketConfig.custom()
                .setSoKeepAlive(false)//检测一下服务器是否仍处于活动状态默认为false，不检测
                .setSoLinger(1)//如果超过了1秒，如果还有未发送的数据包，这些数据包将全部被丢弃
                .setSoReuseAddress(true)//可以使多个Socket对象绑定在同一个端口上
                .setSoTimeout(5000)//设置读取数据超时(ms)
                .setTcpNoDelay(true)//客户端每发送一次数据，无论数据包的大小都会将这些数据发送出去
                .build();
        connManager.setDefaultSocketConfig(socketConfig);
        connManager.setMaxTotal(maxTotal);
        connManager.setDefaultMaxPerRoute(maxPerRoute);
        connManager.setValidateAfterInactivity(2000);
        return connManager;
    }

    private ConnectionSocketFactory initSSlFactory() {
        SSLConnectionSocketFactory scsf = null;
        try {
            scsf = new SSLConnectionSocketFactory(
                    SSLContexts.custom().loadTrustMaterial(null, new TrustSelfSignedStrategy()).build(),
                    NoopHostnameVerifier.INSTANCE);
        } catch (NoSuchAlgorithmException | KeyManagementException | KeyStoreException e) {
            e.printStackTrace();
        }
        return scsf;
    }


    /**
     * 默认请求配置
     * @param connReqTimeOut 连接池获取到连接的超时时间 (毫秒)
     * @param connTimeOut 客户端和服务器数据交互超时时间(毫秒)
     * @param socketTimeOut 客户端与服务端建立连接的超时时间(毫秒)
     * @return RequestConfig
     */
    @Bean(name = "requestConfig")
    public RequestConfig requestConfig(
            @Value("${spider.request-config.connection-request-timeout}") int connReqTimeOut,
            @Value("${spider.request-config.socket-timeout}") int socketTimeOut,
            @Value("${spider.request-config.connection-timeout}") int connTimeOut) {
        return RequestConfig.custom()
                .setConnectionRequestTimeout(connReqTimeOut)
                .setSocketTimeout(socketTimeOut)
                .setConnectTimeout(connTimeOut)
                .build();
    }

    /**
     * HTTP长连接保持活跃策略
     * @return ConnectionKeepAliveStrategy
     */
    @Bean(name = "connectionKeepAliveStrategy")
    public ConnectionKeepAliveStrategy connectionKeepAliveStrategy() {
        return (httpResponse, httpContext) -> {
            //获取 HTTP Header 'Keep-Alive'
            HeaderElementIterator iter = new BasicHeaderElementIterator(
                    httpResponse.headerIterator(HTTP.CONN_KEEP_ALIVE)
            );
            while (iter.hasNext()) {
                HeaderElement headerElement = iter.nextElement();
                String param = headerElement.getName();
                String value = headerElement.getValue();
                if (value != null && "timeout".equalsIgnoreCase(param)) {
                    try {
                        return Long.parseLong(value) * 1000;
                    } catch (NumberFormatException ignore) {}
                }
            }
            return 10 * 1000;
        };
    }

    /**
     * 重定向策略
     * @return RedirectStrategy
     */
    @Bean(name="redirectStrategy")
    public RedirectStrategy redirectStrategy(){
        return new DefaultRedirectStrategy();
    }

    /**
     * HttpClient
     * @param connManager 池连接管理
     * @param reqCon 默认请求配置
     * @return CloseableHttpClient
     */
    @Bean(name = "httpClient")
    public CloseableHttpClient closeableHttpClient(
            @Qualifier("poolingHttpClientConnectionManager") PoolingHttpClientConnectionManager connManager,
            @Qualifier("connectionKeepAliveStrategy") ConnectionKeepAliveStrategy connKeepAliveStrategy,
            @Qualifier("redirectStrategy")RedirectStrategy redirectStrategy,
            @Qualifier("requestConfig") RequestConfig reqCon) {
        return  HttpClients.custom()
                .setConnectionManager(connManager)
                .setKeepAliveStrategy(connKeepAliveStrategy)
                .setRedirectStrategy(redirectStrategy)
                .setDefaultRequestConfig(reqCon)
                //取消HttpClient 默认Cookie管理
                .disableCookieManagement()
                .build();

    }
}
