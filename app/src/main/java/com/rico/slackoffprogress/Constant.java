package com.rico.slackoffprogress;


import org.json.JSONObject;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.Calendar;

public class Constant
{
    public static String APPLICATION_PATH ;


    public static float getProgress()
    {
        try
        {
            String path = Constant.APPLICATION_PATH + "/config.json";
            File file = new File(path);
            byte[] b = FileUtils.readFile(file);
            String content = new String(b, StandardCharsets.UTF_8);
            if(content.isEmpty())
                return 0;
            JSONObject jsonObject = new JSONObject(content);

            int dayWork = jsonObject.getInt("dayWork");
            int startTime = jsonObject.getInt("startTime");
            int endTime = jsonObject.getInt("endTime");
            int workTimeOfDay = endTime - startTime;
            int workTimeOfWeek = workTimeOfDay * dayWork * 60 * 60;

            Calendar calendar = Calendar.getInstance();
            int currentDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
            int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
            int minute = calendar.get(Calendar.MINUTE);
            int second = calendar.get(Calendar.SECOND);

            //周六周日
            if (currentDayOfWeek == 6 || currentDayOfWeek == 0)
            {
                currentDayOfWeek = 5;
                currentHour = endTime;
            }

            if (currentHour < startTime )
            {
                currentHour = startTime;
                minute = 0;
                second = 0;
            }
            else if(currentHour >= endTime)
            {
                currentHour = endTime;
                minute = 0;
                second = 0;
            }

            int workTime = (currentDayOfWeek - 1) * workTimeOfDay * 60 * 60 + (currentHour - startTime) * 60 * 60 + minute * 60 + second;

            float currentProgress = ((float) workTime / workTimeOfWeek) * 100;

            return currentProgress;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return 0;
    }

}
