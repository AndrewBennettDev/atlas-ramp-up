package com.example.atlasrampupandrewbennett.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.RestTemplate;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

@Configuration
public class RestTemplateConfig {
    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        final List<ClientHttpRequestInterceptor> interceptors =
                Arrays.asList(
                        new ClientHttpRequestInterceptor() {
                            @Override
                            public ClientHttpResponse intercept(
                                    final HttpRequest request,
                                    final byte[] body,
                                    final ClientHttpRequestExecution execution)
                                    throws IOException {
                                String dateString = ZonedDateTime.now().format(DateTimeFormatter.ISO_INSTANT);
                                request.getHeaders().add("Authorization", generateHmac(request, dateString));
                                request.getHeaders().add("x-bc-date", dateString);
                                return execution.execute(request, body);
                            }

                            private String generateHmac(final HttpRequest request, final String dateString) {

                                String path = request.getURI().getPath();
                                String message = dateString + "," + path;
                                String signature =
                                        getSignature("hIgivImi5Hb7ouzBd18iGL17jTd3xGv6FpcGULgHQ", message);
                                return "BC1-HMAC-SHA256 keyid=BetterCloud_hmac_2019-08-22-1& signature="
                                       + signature
                                       + " signedheaders=date,uri";
                            }

                            private String getSignature(final String key, final String message) {
                                SecretKeySpec secretKeySpec =
                                        new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
                                String signature = "";
                                try {
                                    Mac mac = Mac.getInstance("HmacSHA256");
                                    mac.init(secretKeySpec);
                                    signature =
                                            Base64.getEncoder()
                                                  .encodeToString(mac.doFinal(message.getBytes(StandardCharsets.UTF_8)));
                                } catch (Exception e) {

                                } finally {

                                }

                                return signature;
                            }
                        });
        restTemplate.setInterceptors(interceptors);
        return restTemplate;
    }
}
