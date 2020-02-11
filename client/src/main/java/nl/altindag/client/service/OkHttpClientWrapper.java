package nl.altindag.client.service;

import static nl.altindag.client.ClientType.OK_HTTP;
import static nl.altindag.client.Constants.HEADER_KEY_CLIENT_TYPE;

import java.io.IOException;
import java.util.Objects;

import org.springframework.stereotype.Service;

import nl.altindag.client.ClientType;
import nl.altindag.client.model.ClientResponse;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

@Service
public class OkHttpClientWrapper implements RequestService {

    private final OkHttpClient okHttpClient;

    public OkHttpClientWrapper(OkHttpClient okHttpClient) {
        this.okHttpClient = okHttpClient;
    }

    @Override
    public ClientResponse executeRequest(String url) throws IOException {
        Request request = new Request.Builder()
                                     .url(url)
                                     .header(HEADER_KEY_CLIENT_TYPE, getClientType().getValue())
                                     .build();

        Response response = okHttpClient.newCall(request).execute();


        return new ClientResponse(Objects.requireNonNull(response.body()).string(), response.code());
    }

    @Override
    public ClientType getClientType() {
        return OK_HTTP;
    }
}
