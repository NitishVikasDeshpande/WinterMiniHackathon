package com.example.learn.simple;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class App extends Application {



    @Override
    public void onCreate() {
        super.onCreate();

        startService(new Intent(this, YourService.class));
    }
}
