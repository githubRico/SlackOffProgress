package com.rico.slackoffprogress;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.Calendar;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends AppCompatActivity
{

    private ProgressBar progressBar;
    private TextView tvProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.setTitle("进度");


        initUI();
        dataToUI();
    }

    private void initUI()
    {
        progressBar = findViewById(R.id.progressBar);
        tvProgress = findViewById(R.id.tvProgress);
    }

    private void dataToUI()
    {
        Timer timer = new Timer();
        timer.schedule(new TimerTask()
        {
            @Override
            public void run()
            {
                runOnUiThread(new TimerTask()
                {
                    @Override
                    public void run()
                    {
                        float currentProgress = Constant.getProgress();
                        progressBar.setProgress((int) currentProgress);
                        currentProgress = (float) Math.round(currentProgress * 1000) / 1000;
                        tvProgress.setText(String.format(Locale.CHINA, "周进度：%s%%", currentProgress));
                    }
                });
            }
        }, 1000, 1000);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        super.onCreateOptionsMenu(menu);
        menu.add(0,0,0,"配置");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        if (item.getItemId() == 0)
        {
            Intent intent = new Intent();
            intent.setClass(this, ConfigActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * copy文本
     * @param view
     */
    public void copyText(View view) {

    }
}