package com.example.intent.figerset.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.intent.figerset.DateBase.DateBaseOperator;
import com.example.intent.figerset.Lock.GestureLock;
import com.example.intent.figerset.R;

import java.util.ArrayList;
import java.util.List;

public class SetlockActivity extends AppCompatActivity {

    private GestureLock gestureLock;
    private boolean first;
    private List<Integer> pList;
    private DateBaseOperator dateBaseOperator;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gestureLock = findViewById(R.id.lockView);
        initEvents();
        dateBaseOperator = new DateBaseOperator(SetlockActivity.this);
        first = true;
    }

    private void initEvents() {
        gestureLock.setOnDrawFinishedListener(new GestureLock.OnDrawFinishedListener() {
            @Override
            public boolean onDrawFinished(List<Integer> passList) {

                if (first){
                    int i = 0;
                    pList = new ArrayList<Integer>();
                    while (i<passList.size()){
                        pList.add(passList.get(i++));
                    }
                    first = false;

                }
                else{
                    if (pList.size() != passList.size()){
                        pList.clear();
                        first = true;
                    }
                    else{
                        String s="";
                        int i = 0;
                        while(i<pList.size() && pList.get(i) == passList.get(i)){
                            s += pList.get(i).toString();
                            i++;
                        }
                        if(i == pList.size())
                        {
                            dateBaseOperator.add(s);
                            Toast.makeText(SetlockActivity.this, "OK OK", Toast.LENGTH_SHORT).show();
                        }
                        else
                            Toast.makeText(SetlockActivity.this,"NO NO",Toast.LENGTH_SHORT).show();
                    }
                }
                return false;
            }
        });



    }

}
