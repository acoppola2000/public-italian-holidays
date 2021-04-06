package it.acoppola2000.publicItalianHolidays.cache;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSocketFactory;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

class CustomHttpClient {

    private String urlString;
    private SSLSocketFactory customSSLSocketFactory;

    CustomHttpClient(String urlString, SSLSocketFactory customSSLSocketFactory) {
        this.urlString = urlString;
        this.customSSLSocketFactory = customSSLSocketFactory;
    }

    String getPublicItalianHolidaysResponse() throws IOException {
        HttpsURLConnection conn = null;
        BufferedReader in = null;
        try {
            URL url = new URL(urlString);
            conn = this.getNewConnection(url);
            int responseCode = conn.getResponseCode();

            InputStreamReader streamReader;
            if (responseCode == HttpURLConnection.HTTP_OK) {
                streamReader = new InputStreamReader(conn.getInputStream());
            } else {
                streamReader = new InputStreamReader(conn.getErrorStream());
            }
            in = new BufferedReader(streamReader);
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            return response.toString();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (Exception e) {
                }
            }
            if( conn != null) {
                try {
                    conn.disconnect();
                } catch (Exception e) {

                }
            }
        }
    }


    private HttpsURLConnection getNewConnection(URL url) throws IOException {
        HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setConnectTimeout(5000);
        conn.setReadTimeout(5000);
        conn.setSSLSocketFactory(customSSLSocketFactory);
        return conn;
    }

}


