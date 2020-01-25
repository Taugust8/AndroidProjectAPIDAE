package com.example.bluesky;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import android.net.Uri;
import android.content.ContentResolver;
import android.database.Cursor;
import android.util.JsonReader;
import android.util.Log;
import android.widget.ListView;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import org.json.JSONArray;


public class Async extends AsyncTask<URL, Integer, Long>  {

    final private static String url = "https://webinfo.iutmontp.univ-montp2.fr/~chambaudM/BlueSky-JS-Project/song.json";


    public static JSONArray getJSONSongList() {

        String code = "";
        BufferedReader in = null;
        JSONArray json =null;
        try
        {
            URL site = new URL(Async.url);
            in = new BufferedReader(new InputStreamReader(site.openStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null)
            {
                code = code + "\n" + (inputLine);
            }
            in.close();
            System.out.println(code);
            json = new JSONArray(code);
        }
        catch (Exception ex)
        {
            System.out.println(ex);
        }
        finally
        {
            try
            {
                in.close();
            }
            catch (Exception ex)
            {

            }
        }

        return json;
    }
}
