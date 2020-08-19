package com.example.testserver;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements AsyncClass.MyAsyncCallBack {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

                AsyncClass asyncClass = new AsyncClass(MainActivity.this);
                asyncClass.registrationMyAsyncCallBack(MainActivity.this);
                asyncClass.execute();


    }

    @Override
    public void doMyAsyncCallBack(String status) {
        Log.e("KUCHA",status);
        if (status.equals("OK")||status.length()<3){

        }else {
            Intent intent = new Intent(MainActivity.this, MainActivity2.class);
            intent.putExtra("status",status);
            MainActivity.this.finish();
            startActivity(intent);
        }
    }
}