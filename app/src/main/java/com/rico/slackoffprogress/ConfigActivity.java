package com.rico.slackoffprogress;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.nio.charset.StandardCharsets;

public class ConfigActivity extends AppCompatActivity
{
    private EditText tbDayWork;
    private EditText tbStartTime;
    private EditText tbEndTime;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);
        initUI();
        dataToUI();
    }

    private void initUI()
    {
        tbDayWork = findViewById(R.id.tbDayWork);
        tbStartTime = findViewById(R.id.tbStartTime);
        tbEndTime = findViewById(R.id.tbEndTime);
    }

    private void dataToUI()
    {
        String path = Constant.APPLICATION_PATH + "/config.json";
        File file = new File(path);

        try
        {
            byte[] b = FileUtils.readFile(file);
            String content = new String(b, StandardCharsets.UTF_8);
            JSONObject jsonObject = new JSONObject(content);
            tbDayWork.setText(jsonObject.getString("dayWork"));
            tbStartTime.setText(jsonObject.getString("startTime"));
            tbEndTime.setText(jsonObject.getString("endTime"));
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * 保存按钮事件
     *
     * @param view
     */
    public void save(View view) throws JSONException
    {
        String path = Constant.APPLICATION_PATH + "/config.json";
        File file = new File(path);


        JSONObject jsonObject = new JSONObject();
        jsonObject.put("dayWork", tbDayWork.getText().toString());
        jsonObject.put("startTime", tbStartTime.getText().toString());
        jsonObject.put("endTime", tbEndTime.getText().toString());
        String content = jsonObject.toString();

        FileUtils.writeFile(file, content.getBytes(StandardCharsets.UTF_8));

        this.finish();
    }

}
