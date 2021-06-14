package com.blancadomene.tfg;

import android.os.AsyncTask;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class APIClient extends AsyncTask<APIClient.Request, Void, APIClient.Response> {
    static public class Request {
        public String method;
        public String query;
        public String body;

        public Request() {
        }
    }

    static public class Response {
        public int retcode;
        public JsonElement json;

        public Response() {
            retcode = -1;
        }
    }

    String server_addr;

    public APIClient(String server_addr) {
        this.server_addr = server_addr;
    }

    @Override
    protected Response doInBackground(Request... requests) {
        Request request = requests[0];
        Response response = new Response();
        try {
            URL url = new URL(String.format("http://%s/%s", this.server_addr, request.query));

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(request.method);

            if (request.method.equals("POST")) {
                connection.setDoOutput(true); // default value: false (GET)
                connection.getOutputStream().write(request.body.getBytes());
            }

            response.retcode = connection.getResponseCode();

            if (response.retcode == 200) {
                InputStream responseBody = connection.getInputStream();
                InputStreamReader responseBodyReader =
                        new InputStreamReader(responseBody, StandardCharsets.UTF_8);

                final JsonParser parser = new JsonParser();
                response.json = parser.parse(responseBodyReader);
            } else {
                System.out.println("Failed to connect.");
            }

            connection.disconnect();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return response;
    }
}
