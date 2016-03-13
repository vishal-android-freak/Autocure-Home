package vaas.autocurehome.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;

/**
 * Created by vishal on 11/3/16.
 */
public class Utils {

    Context context;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    public Utils(Context context) {
        this.context = context;
    }

    public void doorLockUnlock(String data) {
        String parameters = "lock_status=" + data;
        byte[] postData = parameters.getBytes(Charset.forName("UTF-8"));
        int postDataLength = postData.length;
        HttpURLConnection conn = null;

        try {
            URL url = new URL("http://192.168.0.102/door_security.php");
            conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000000);
            conn.setConnectTimeout(15000000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("charset", "utf-8");
            conn.setRequestProperty("Content-Length", Integer.toString(postDataLength));
            conn.connect();
            OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());
            writer.write(parameters);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (conn != null)
                conn.disconnect();
        }
    }

    public void readStatus(String object) {

        sharedPreferences = context.getSharedPreferences("Autocure", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        String parameters = "read_status=" + object;
        byte[] postData = parameters.getBytes(Charset.forName("UTF-8"));
        int postDataLength = postData.length;
        HttpURLConnection conn = null;

        try {
            URL url = new URL("http://192.168.0.102/status.php");
            conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000000);
            conn.setConnectTimeout(15000000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("charset", "utf-8");
            conn.setRequestProperty("Content-Length", Integer.toString(postDataLength));
            conn.connect();
            OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());
            writer.write(parameters);
            writer.flush();
            editor.putString("lock_status", convertStreamToString(conn.getInputStream()));
            editor.apply();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (conn != null)
                conn.disconnect();
        }
    }

    private String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null)
                    is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

}
