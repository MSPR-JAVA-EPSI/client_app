package client_app.utils;

import client_app.dto.Response;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class FullResponseBuilder {
    public static Response getFullResponse(HttpURLConnection con) throws IOException {
        int status = con.getResponseCode();

        Map<String, String> headers = new HashMap<>();
        con.getHeaderFields()
                .entrySet()
                .stream()
                .filter(entry -> entry.getKey() != null)
                .forEach(entry -> {

                    String key = entry.getKey();
                    StringBuilder headerValuesBuilder = new StringBuilder();
                    List<String> headerValues = entry.getValue();
                    Iterator<String> it = headerValues.iterator();
                    if (it.hasNext()) {
                        headerValuesBuilder.append(it.next());

                        while (it.hasNext()) {
                            headerValuesBuilder.append(", ")
                                    .append(it.next());
                        }
                    }
                    headers.put(key, headerValuesBuilder.toString());
                });

        Reader streamReader = null;
        System.out.println("Status: "+status);
        if (status > 299) {
            streamReader = new InputStreamReader(con.getErrorStream());
        } else {
            streamReader = new InputStreamReader(con.getInputStream());
        }

        BufferedReader in = new BufferedReader(streamReader);
        String inputLine;
        StringBuilder body = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            body.append(inputLine);
        }

        in.close();

        return new Response(status, headers, body.toString());
    }
}
