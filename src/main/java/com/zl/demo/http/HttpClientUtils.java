package com.zl.demo.http;

import org.apache.http.*;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLException;
import javax.net.ssl.SSLHandshakeException;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author zhuangl
 * @version 1.0
 * @date 2020/8/6 16:01
 */
public class HttpClientUtils {
    private final static Logger LOGGER = LoggerFactory.getLogger(HttpClientUtils.class);
    private final static String DEFAULT_CHARSET = "utf-8";
    private final static int DEFAULT_CONN_TIMEOUT = 50000;
    private final static int DEFAULT_SO_TIMEOUT = 80000;
    /** 每个路由的最大连接数 */
    private final static int DEFAULT_MAX_CONN_PER_HOST = 300;
    /** 最大连接数 */
    private final static int DEFAULT_MAX_TOTAL_CONN = 600;
    /** {@link HttpClient}实例-单例 */
    private static CloseableHttpClient httpClient = null;
    /** 单例实例锁 */
    private final static Object syncLock = new Object();

    /**
     * POST方式获取请求内容-<b>默认UTF-8编码</b>
     * @param url 请求地址
     * @param params 请求参数
     * @param charset 编码
     * @return 请求结果内容
     */
    public static String post(final String url, final Map<String, Object> params, final String charset) {
        final String reqCharset = charset != null ? charset : DEFAULT_CHARSET;
        HttpPost httpPost = new HttpPost(url);
        setConfig(httpPost);
        CloseableHttpResponse response = null;
        try {
            setPostParams(httpPost, params, charset);
            response = getHttpClient(url).execute(httpPost, HttpClientContext.create());
            HttpEntity entity = response.getEntity();
            String result = EntityUtils.toString(entity, reqCharset);
            return result;
        } catch (IOException e) {
            LOGGER.error("POST方式获取请求内容-请求IO异常.", e);
        } catch (Exception e) {
            LOGGER.error("POST方式获取请求内容-请求异常.", e);
        } finally {
            if (response != null) {
                try {
                    EntityUtils.consume(response.getEntity());
                    response.close();
                } catch (IOException e) {
                    LOGGER.error("POST方式获取请求内容-关闭请求IO异常.", e);
                }
            }
        }
        return null;
    }

    public static String post(String toURL,String data) throws Exception {
        StringBuffer bs = new StringBuffer();
        URL url = new URL(toURL);
        HttpURLConnection urlcon = (HttpURLConnection) url.openConnection();
        urlcon.setRequestMethod("POST");
        urlcon.setUseCaches(false);
        urlcon.setConnectTimeout(DEFAULT_CONN_TIMEOUT);
        urlcon.setReadTimeout(DEFAULT_SO_TIMEOUT);
        urlcon.setDoInput(true);
        urlcon.setDoOutput(true);
        urlcon.setRequestProperty("Content-Type",
                "application/json");
        OutputStream out = urlcon.getOutputStream();
        out.write(data.getBytes("UTF-8"));
        out.flush();
        out.close();
        urlcon.connect();
        InputStream is = urlcon.getInputStream();
        BufferedReader buffer = new BufferedReader(
                new InputStreamReader(is));

        String l = null;
        while ((l = buffer.readLine()) != null) {
            bs.append(l);
        }
        return bs.toString();
    }


    /**
     * POST方式获取请求内容
     * @param url 请求地址
     * @param params 请求参数
     * @return 请求结果内容
     */
    public static String postWithoutCharset(final String url, final Map<String, Object> params) {
        HttpPost httpPost = new HttpPost(url);
        setConfig(httpPost);
        CloseableHttpResponse response = null;
        try {
            setPostParamsWithoutCharset(httpPost, params);
            response = getHttpClient(url).execute(httpPost, HttpClientContext.create());
            HttpEntity entity = response.getEntity();
            String result = EntityUtils.toString(entity);
            return result;
        } catch (IOException e) {
            LOGGER.error("POST(WithoutCharset)方式获取请求内容-请求IO异常.", e);
        } catch (Exception e) {
            LOGGER.error("POST(WithoutCharset)方式获取请求内容-请求异常.", e);
        } finally {
            if (response != null) {
                try {
                    EntityUtils.consume(response.getEntity());
                    response.close();
                } catch (IOException e) {
                    LOGGER.error("POST方式获取请求内容-关闭请求IO异常.", e);
                }
            }
        }
        return null;
    }

    /**
     * GET方式获取请求内容
     * @param url 请求地址
     * @param params 请求参数
     * @param charset 编码
     * @return 请求结果内容
     */
    public static String get(String url, final Map<String, Object> params, final String charset) {
        CloseableHttpResponse response = null;
        try {
            final String fullUrl = url + "?" + setGetParams(params, charset);
            HttpGet httpGet = new HttpGet(fullUrl);
            setConfig(httpGet);
            response = getHttpClient(fullUrl).execute(httpGet, HttpClientContext.create());
            HttpEntity entity = response.getEntity();
            String result = EntityUtils.toString(entity, charset);
            return result;
        } catch (IOException e) {
            LOGGER.error("GET方式获取请求内容-请求IO异常.", e);
        } catch (Exception e) {
            LOGGER.error("GET方式获取请求内容-请求异常.", e);
        } finally {
            if (response != null) {
                try {
                    EntityUtils.consume(response.getEntity());
                    response.close();
                } catch (IOException e) {
                    LOGGER.error("GET方式获取请求内容-关闭请求IO异常.", e);
                }
            }
        }
        return null;
    }

    /**
     * 获取{@link HttpClient}对象
     * @param url 请求URL
     * @return {@link CloseableHttpClient}
     */
    private static CloseableHttpClient getHttpClient(final String url) {
        String hostname = url.split("/")[2];
        int port = 80;
        if (hostname.contains(":")) {
            String[] tmpArr = hostname.split(":");
            hostname = tmpArr[0];
            port = Integer.parseInt(tmpArr[1]);
        }
        if (httpClient == null) {
            synchronized (syncLock) {
                if (httpClient == null) {
                    httpClient = createHttpClient(hostname, port);
                }
            }
        }
        return httpClient;
    }

    /**
     * 创建{@link HttpClient}对象
     * @param hostname 请求地址名称
     * @param port 请求地址的端口
     * @return {@link CloseableHttpClient}
     */
    private static CloseableHttpClient createHttpClient(final String hostname, final int port) {
        final ConnectionSocketFactory plainSocketFactory = PlainConnectionSocketFactory.getSocketFactory();
        final LayeredConnectionSocketFactory sslSocketFactory = SSLConnectionSocketFactory.getSocketFactory();
        final Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory> create()
                .register("http", plainSocketFactory)
                .register("https", sslSocketFactory).build();
        final PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager(registry);
        connManager.setMaxTotal(DEFAULT_MAX_TOTAL_CONN);
        connManager.setDefaultMaxPerRoute(DEFAULT_MAX_CONN_PER_HOST);
        final HttpHost httpHost = new HttpHost(hostname, port);
        connManager.setMaxPerRoute(new HttpRoute(httpHost), DEFAULT_MAX_CONN_PER_HOST);
        // 请求重试处理
        final HttpRequestRetryHandler httpRequestRetryHandler = new HttpRequestRetryHandler() {
            @Override
            public boolean retryRequest(IOException exception, int executionCount, HttpContext context) {
                if (executionCount >= 3) {// 重试3次放弃
                    return false;
                }
                if (exception instanceof NoHttpResponseException) {// 服务器丢掉连接，重试
                    return true;
                }
                if (exception instanceof SSLHandshakeException) {// SSL握手异常，不重试
                    return false;
                }
                if (exception instanceof InterruptedIOException) {// 超时，不重试
                    return false;
                }
                if (exception instanceof UnknownHostException) {// 目标服务器不可达，不重试
                    return false;
                }
                if (exception instanceof ConnectTimeoutException) {// 连接被拒绝，不重试
                    return false;
                }
                if (exception instanceof SSLException) {// SSL异常，不重试
                    return false;
                }
                final HttpClientContext clientContext = HttpClientContext.adapt(context);
                final HttpRequest request = clientContext.getRequest();
                if (!(request instanceof HttpEntityEnclosingRequest)) {// 请求是幂等的，重试
                    return true;
                }
                return false;
            }
        };
        final CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(connManager).setRetryHandler(httpRequestRetryHandler).build();
        return httpClient;
    }

    /**
     * 设置POST请求参数-需要设置编码
     * @param httpPost {@link HttpPost}
     * @param params 请求参数键值对
     * @param charset 编码
     * @throws UnsupportedEncodingException 异常-{@link UnsupportedEncodingException}
     */
    private static void setPostParams(HttpPost httpPost, Map<String, Object> params, final String charset) throws UnsupportedEncodingException {
        final String reqCharset = charset != null ? charset : DEFAULT_CHARSET;
        List<NameValuePair> nameValuePairList = new ArrayList<NameValuePair>();
        Set<String> keySet = params.keySet();
        for (String key : keySet) {
            nameValuePairList.add(new BasicNameValuePair(key, params.get(key) != null ? params.get(key).toString() : ""));
        }
        httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairList, reqCharset));
    }

    /**
     * 设置POST请求参数
     * @param httpPost {@link HttpPost}
     * @param params 请求参数键值对
     * @throws UnsupportedEncodingException {@link UnsupportedEncodingException}
     */
    private static void setPostParamsWithoutCharset(HttpPost httpPost, Map<String, Object> params) throws UnsupportedEncodingException {
        Set<String> keySet = params.keySet();
        StringBuffer result = new StringBuffer();
        for (String key : keySet) {
            if (result.length() > 0) {
                result.append("&");
            }
            result.append(key);
            if (params.get(key) != null) {
                result.append("=");
                result.append(params.get(key));
            }
        }
        httpPost.setEntity(new StringEntity(result.toString(), ContentType.create("application/x-www-form-urlencoded", "")));
    }

    /**
     * 设置GET请求参数-需要设置编码
     * @param params 请求参数键值对
     * @param charset 编码
     * @return 拼接的参数字符串
     * @throws IOException {@link IOException}
     */
    private static String setGetParams(Map<String, Object> params, final String charset) throws IOException {
        final String reqCharset = charset != null ? charset : DEFAULT_CHARSET;
        List<NameValuePair> nameValuePairList = new ArrayList<NameValuePair>();
        Set<String> keySet = params.keySet();
        for (String key : keySet) {
            nameValuePairList.add(new BasicNameValuePair(key, params.get(key) != null ? params.get(key).toString() : ""));
        }
        return EntityUtils.toString(new UrlEncodedFormEntity(nameValuePairList, reqCharset));
    }

    /**
     * 设置连接的参数
     * @param httpRequestBase {@link HttpRequestBase}
     */
    private static void setConfig(HttpRequestBase httpRequestBase) {
        // 设置Header
        httpRequestBase.setHeader("User-Agent", "Mozilla/5.0");
        httpRequestBase.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
        httpRequestBase.setHeader("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3");// "en-US,en;q=0.5");
        httpRequestBase.setHeader("Accept-Charset", "ISO-8859-1,utf-8,gbk,gb2312;q=0.7,*;q=0.7");
        // 配置请求的超时时间
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectionRequestTimeout(DEFAULT_CONN_TIMEOUT) // http clilent中从connetcion pool中获得一个connection的超时时间
                .setConnectTimeout(DEFAULT_CONN_TIMEOUT) // 连接建立的超时时间
                .setSocketTimeout(DEFAULT_SO_TIMEOUT) // 响应超时时间，超过此时间不再读取响应
                .build();
        httpRequestBase.setConfig(requestConfig);
    }
}
