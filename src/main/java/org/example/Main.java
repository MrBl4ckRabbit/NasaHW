package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.FileOutputStream;
import java.io.IOException;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws IOException {
        String URL = "https://api.nasa.gov/planetary/apod?api_key=IwBTrFvtgYLw4jQz9yYTCxGfxo3LeVVTtL30UtF7&date=2024-06-19";
        CloseableHttpClient httpClient = HttpClientBuilder.create()
                .setDefaultRequestConfig(RequestConfig.custom()
                        .setConnectTimeout(5000)
                        .setSocketTimeout(30000)
                        .setRedirectsEnabled(false)
                        .build())
                .build();

        HttpGet request = new HttpGet(URL);
        CloseableHttpResponse response = httpClient.execute(request);

        ObjectMapper mapper = new ObjectMapper();
        NasaResponse answer = mapper.readValue(response.getEntity().getContent(), NasaResponse.class);

        String imgURL = answer.url;
        String[] splittedURL = imgURL.split("/");
        String fileName = splittedURL[splittedURL.length - 1];

        HttpGet imageGet = new HttpGet(imgURL);
        CloseableHttpResponse image = httpClient.execute(imageGet);
        FileOutputStream fileOutputStream = new FileOutputStream(fileName);
        image.getEntity().writeTo(fileOutputStream);
    }
}
