/**
 * SettingsActivity.java[V 1.0.0]
 * classes : com.ngandroid.demo.SettingsActivity
 * jiangyuchen Create at 2013-11-5 下午2:16:18
 */
package com.ngandroid.demo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

/**
 * com.ngandroid.demo.SettingsActivity
 * @author jiangyuchen
 *
 * create at 2013-11-5 下午2:16:18
 */
public class SettingsActivity extends Activity implements OnClickListener {
    private static final String TAG = "JustDemo SettingsActivity";

    TextView mCacheTv;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_settings);
        mCacheTv = (TextView)findViewById(R.id.settings_clear_cache);
        mCacheTv.setText(String.format(this.getString(R.string.settings_clear_cache),"3.4"));
        findViewById(R.id.settings_clear_cache).setOnClickListener(this);
        findViewById(R.id.settings_visit_178).setOnClickListener(this);
        findViewById(R.id.settings_about_us).setOnClickListener(this);
        findViewById(R.id.settings_back).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
        case R.id.settings_clear_cache:
            break;
        case R.id.settings_visit_178:
            break;
        case R.id.settings_about_us:
            break;
        case R.id.settings_back:
            finish();
            break;
        }
    }
    
}
