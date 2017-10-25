package ru.pricemin.lessonservice;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import java.io.IOException;

public class WiFiOffActivity extends AppCompatActivity {

    MyWiFi myWiFi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wi_fi_off);
    }

    public void onClick(View view) {
        switch (view.getId()){
            case R.id.buttonEnableWiFi:
                myWiFi = new MyWiFi(this);
                myWiFi.enableWiFi();
                this.finish();
                break;
            case R.id.buttonRemindLater:

                this.finish();
                break;
        }

    }

}
