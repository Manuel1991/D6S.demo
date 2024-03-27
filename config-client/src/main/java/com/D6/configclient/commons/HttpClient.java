package com.D6.configclient.commons;

import org.apache.hc.core5.http.*;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.support.ClassicRequestBuilder;
import org.springframework.stereotype.Component;

import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;

import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Component
public class HttpClient {

    private BasicResponse getBasicResponse(ClassicHttpResponse response) throws IOException, ParseException {
        HttpEntity entity = response.getEntity();
        String content = EntityUtils.toString(entity);
        EntityUtils.consume(entity);
        return new BasicResponse(response.getCode(), content);
    }

    public BasicResponse get(URI url, Map<String, String> headers) throws IOException {

        try (CloseableHttpClient httpClient = HttpClients.custom().build()) {

            final ClassicHttpRequest request = ClassicRequestBuilder.get(url).build();

            for (Map.Entry<String, String> entry : headers.entrySet())
                request.addHeader(entry.getKey(), entry.getValue());

            return httpClient.execute(request, this::getBasicResponse);
        }
    }

    public BasicResponse post(URI url, Map<String, String> headers, String body) {
        try (CloseableHttpClient httpClient = HttpClients.custom().build()) {

            final ClassicHttpRequest request = ClassicRequestBuilder
                    .post(url)
                    .setEntity(body)
                    .build();

            for (Map.Entry<String, String> entry : headers.entrySet())
                request.addHeader(entry.getKey(), entry.getValue());

            return httpClient.execute(request, this::getBasicResponse);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
