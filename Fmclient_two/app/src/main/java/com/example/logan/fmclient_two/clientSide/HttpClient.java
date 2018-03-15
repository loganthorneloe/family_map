package com.example.logan.fmclient_two.clientSide;

import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by logan on 11/14/2017.
 */

//need ways to create different URLS
public class HttpClient {

    private String portserver;
    private String hostserver;

    public HttpClient(String host, String port){
        portserver = port;
        hostserver = host;
    }

    public String getUrl(String urlString , String requestBody, String authToken){ //pass result to know what type of object it is
        try{
            URL url = new URL("http://" + hostserver + ":" + portserver + urlString);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoOutput(true);


            if(authToken != null){ //put auth_token in requestheader
                connection.setRequestProperty("Authorization", authToken); //adding auth token
            }
            connection.connect();
            //write in requestBody, whatever I put in my writer will go into my requestBody
            if(requestBody != null) {
                Writer writer = new OutputStreamWriter(connection.getOutputStream());
                writer.write(requestBody);
                writer.close();
            }

            if(connection.getResponseCode() == HttpURLConnection.HTTP_OK){
                InputStream responseBody = connection.getInputStream();

                ByteArrayOutputStream out = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int length = 0;
                while((length = responseBody.read(buffer)) != -1){
                    out.write(buffer, 0, length);
                }

                String responseBodyData = out.toString();
                return responseBodyData;
            }

        }catch(Exception e){
            System.out.println("Exception!");
            e.printStackTrace();
        }

        return null;
    }
}
