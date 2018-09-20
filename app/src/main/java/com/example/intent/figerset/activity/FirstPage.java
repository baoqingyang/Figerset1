package com.example.intent.figerset.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.example.intent.figerset.DateBase.DateBaseOperator;
import com.example.intent.figerset.R;

public class FirstPage extends AppCompatActivity {

    private DateBaseOperator dateBaseOperator;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        dateBaseOperator = new DateBaseOperator(FirstPage.this);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent;
                if(dateBaseOperator.queryOne("user") == ""){
                    intent = new Intent(FirstPage.this,SetlockActivity.class);
                }
                else{

                    intent = new Intent(FirstPage.this,UnlockActivity.class);
                }
                startActivity(intent);
                FirstPage.this.finish();
            }
        },3000);
    }



}
