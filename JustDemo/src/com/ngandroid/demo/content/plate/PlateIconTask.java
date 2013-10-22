/**
 * PlateIconTask.java[V 1.0.0]
 * classes : com.ngandroid.demo.content.plate.PlateIconTask
 * jiangyuchen Create at 2013-10-18 下午4:23:27
 */
package com.ngandroid.demo.content.plate;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.ngandroid.demo.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.CompressFormat;
import android.os.AsyncTask;
import android.util.Log;

/**
 * com.ngandroid.demo.content.plate.PlateIconTask
 * 
 * @author jiangyuchen
 * 
 *         create at 2013-10-18 下午4:23:27
 */
public class PlateIconTask extends AsyncTask<PlateGroup, String, PlateGroup> {
    private static final String TAG = "JustDemo PlateIconTask";
    Context mContext;

    public PlateIconTask(Context context) {
        mContext = context;
    }

    /**
     * <p>
     * Title: doInBackground
     * </p>
     * <p>
     * Description:
     * </p>
     * 
     * @param params
     * @return
     * @see android.os.AsyncTask#doInBackground(Params[])
     */
    @Override
    protected PlateGroup doInBackground(PlateGroup... params) {
        for (Plate plate : params[0]) {
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

    private Bitmap loadIcon(String iconUrl) throws IOException {
        String name = iconUrl.substring(iconUrl.lastIndexOf("/") + 1);
        InputStream inputStream;
        Bitmap bitmap;
        try {
            inputStream = mContext.openFileInput(name);
            bitmap = BitmapFactory.decodeStream(inputStream);
            return bitmap;
        } catch (FileNotFoundException e) {
        }
        URL url = new URL(iconUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoInput(true);
        conn.connect();
        try {
            inputStream = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(inputStream);
        } catch (FileNotFoundException e2) {
            Log.v(TAG, "FileNotFoundException:"+url);
            bitmap = BitmapFactory.decodeResource(mContext.getResources(),
                    R.drawable.icon_plate_default);
        }
        FileOutputStream fos = mContext.openFileOutput(name,
                Context.MODE_PRIVATE);
        bitmap.compress(CompressFormat.PNG, 100, fos);
        return bitmap;
    }

}
