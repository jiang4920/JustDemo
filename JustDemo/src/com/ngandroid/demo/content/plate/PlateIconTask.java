/**
 * PlateIconTask.java[V 1.0.0]
 * classes : com.ngandroid.demo.content.plate.PlateIconTask
 * jiangyuchen Create at 2013-10-18 下午4:23:27
 */
package com.ngandroid.demo.content.plate;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

/**
 * com.ngandroid.demo.content.plate.PlateIconTask
 * @author jiangyuchen
 *
 * create at 2013-10-18 下午4:23:27
 */
public class PlateIconTask extends AsyncTask<PlateGroup, String, PlateGroup> {
    private static final String TAG = "JustDemo PlateIconTask";

    /**
     * <p>Title: doInBackground</p>
     * <p>Description: </p>
     * @param params
     * @return
     * @see android.os.AsyncTask#doInBackground(Params[])
     */
    @Override
    protected PlateGroup doInBackground(PlateGroup... params) {
        for(Plate plate:params[0]){
            try {
                plate.icon = loadIcon(plate.getIconUrl());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return params[0];
    }

    @Override
    protected void onPostExecute(PlateGroup result) {
        result.notifyRefresh();
    }
    
    private Bitmap loadIcon(String iconUrl) throws IOException{
        URL url = new URL(iconUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoInput(true);
        conn.connect();
        InputStream inputStream = conn.getInputStream();
        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
        return bitmap;
    }
    
}
