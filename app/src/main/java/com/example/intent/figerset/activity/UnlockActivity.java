package com.example.intent.figerset.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.intent.figerset.DateBase.DateBaseOperator;
import com.example.intent.figerset.Lock.GestureLock;
import com.example.intent.figerset.R;

import java.util.ArrayList;
import java.util.List;

public class UnlockActivity extends AppCompatActivity {

    private GestureLock gestureLock;
    private DateBaseOperator dateBaseOperator;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        gestureLock = findViewById(R.id.lockView);
        dateBaseOperator = new DateBaseOperator(UnlockActivity.this);
        initEvents();
    }

    private void initEvents() {
        gestureLock.setOnDrawFinishedListener(new GestureLock.OnDrawFinishedListener() {
            @Override
            public boolean onDrawFinished(List<Integer> passList) {
                String input="";
                for(int i = 0;i<passList.size();i++){
                    input += passList.get(i).toString();
                }
                if (input.equals(dateBaseOperator.queryOne("user"))){
                    Toast.makeText(UnlockActivity.this, "密码正确", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(UnlockActivity.this,MainActivity.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(UnlockActivity.this, "密码错误", Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });
    }

}
