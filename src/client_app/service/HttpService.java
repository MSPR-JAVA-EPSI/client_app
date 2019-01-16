package client_app.service;

import client_app.dto.Response;
import client_app.utils.FullResponseBuilder;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

public class HttpService {

    private static HttpService httpService;
    private final String SERVER_ENDPOINT = "http://localhost:9991/api";
    private final int CONNECTION_TIMEOUT = 5000;
    private final int READ_TIMEOUT = 5000;

    public static HttpService getInstance() {
        if (httpService == null) {
            httpService = new HttpService();
        }
        return httpService;
    }

    public Response request(String path, Map<String, String> headers, String body) throws Exception {
        URL url = new URL(SERVER_ENDPOINT + path);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con = setRequestHeaders(con, headers);
        con.setConnectTimeout(CONNECTION_TIMEOUT);
        con.setReadTimeout(READ_TIMEOUT);
        con.setDoOutput(true);
        if(body != null) {
            OutputStream os = con.getOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8");
            osw.write(body);
            osw.flush();
            osw.close();
            os.close();
        }
        con.connect();
        return FullResponseBuilder.getFullResponse(con);
    }

    public HttpURLConnection setRequestHeaders(HttpURLConnection con, Map<String, String> headers) {
        headers.forEach((headerType, headerValue) -> {
            con.setRequestProperty(headerType, headerValue);
        });
        return con;
    }
}
