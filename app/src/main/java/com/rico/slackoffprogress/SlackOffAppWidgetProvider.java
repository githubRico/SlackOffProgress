package com.rico.slackoffprogress;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.TextView;

import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class SlackOffAppWidgetProvider extends AppWidgetProvider
{

    //每次更新都调用一次该方法，使用频繁
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds)
    {
        super.onUpdate(context, appWidgetManager, appWidgetIds);

        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
        float currentProgress = Constant.getProgress();
        currentProgress = (float) Math.round(currentProgress * 100) / 100;
        remoteViews.setTextViewText(R.id.tvProgress, String.format(Locale.CHINA, "周进度：%s%%", currentProgress));
        ComponentName componentName = new ComponentName(context, SlackOffAppWidgetProvider.class);
        appWidgetManager.updateAppWidget(componentName, remoteViews);//setText之后，记得更新一下
    }

    //每接收一次广播消息就调用一次，使用频繁
    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);

    }


    //每删除一个就调用一次
    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
    }

    //当该Widget第一次添加到桌面是调用该方法，可添加多次但只第一次调用
    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
    }

    //当最后一个该Widget删除是调用该方法，注意是最后一个
    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
    }
}
