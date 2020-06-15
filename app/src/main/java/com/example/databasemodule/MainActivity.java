package com.example.databasemodule;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import com.example.databasemodule.Views.AddDataActivity;
import com.example.databasemodule.Views.DownloadDataActivity;
import com.example.databasemodule.Views.AddNotificationActivity;
import com.example.databasemodule.Views.emulator.EmulatorActivity;

public class MainActivity extends AppCompatActivity {
    public static final String CHANNEL_1_ID = "channel1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createNotificationChannels();
    }

    public void downloadData(View view) {
        Intent intent = new Intent(this, DownloadDataActivity.class);
        startActivity(intent);
    }

    public void addData(View view) {
        Intent intent = new Intent(this, AddDataActivity.class);
        startActivity(intent);
    }

    public void openEmulatorActivity(final View view) {
        final Intent intent = new Intent(this, EmulatorActivity.class);
        startActivity(intent);
    }

    public void openNotificationActivity(View view) {
        final Intent intent = new Intent(this, AddNotificationActivity.class);
        startActivity(intent);
    }

    private void createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel1 = new NotificationChannel(
                    CHANNEL_1_ID,
                    "Channel 1",
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel1.setDescription("This is Channel 1");

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel1);
        }
    }
}