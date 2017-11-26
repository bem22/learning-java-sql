package utils;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class JSONUtils {

    public static String getJSON(String url, OkHttpClient client){
        String s = "";
        Request request = new Request.Builder().url(url).build();
        Response response = null;
        try {
            response = client.newCall(request).execute();
            s = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return s;
    }
}
