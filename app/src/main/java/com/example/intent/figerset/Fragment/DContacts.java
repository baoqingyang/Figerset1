package com.example.intent.figerset.Fragment;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.example.intent.figerset.DateAdapter.DContactsAdapter;
import com.example.intent.figerset.Fragment.base.BaseFragment;
import com.example.intent.figerset.Model.Contacts;
import com.example.intent.figerset.R;

import java.util.ArrayList;
import java.util.List;

public class DContacts extends BaseFragment {

    private RecyclerView rc_dcon;
    private List<Contacts> contactsList = new ArrayList<>();
    private DContactsAdapter dContactsAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;




    @Override
    protected void initPrepare() {

    }

    @Override
    protected void onInvisible() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initViews(View view) {
        rc_dcon = view.findViewById(R.id.rc_dcon);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rc_dcon.setLayoutManager(layoutManager);
        swipeRefreshLayout = view.findViewById(R.id.dcon_swipe_refresh);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshCall();
            }
        });
        dContactsAdapter = new DContactsAdapter(getActivity(),contactsList);
        rc_dcon.setAdapter(dContactsAdapter);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_dcon;
    }

    private void refreshCall() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        readListFromSDcard();
                        swipeRefreshLayout.setRefreshing(false);
                        dContactsAdapter.notifyDataSetChanged();
                        Toast.makeText(getActivity(), "数据刷新成功", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }).start();
    }

    private void  readListFromSDcard(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                contactsList.add(new Contacts("cc","13269513505"));
                contactsList.add(new Contacts("xiuxiu","13265489752"));
                contactsList.add(new Contacts("xxiu","13265489752"));
                contactsList.add(new Contacts("xiiu","13265482757"));
                contactsList.add(new Contacts("xiux","13264489753"));
                contactsList.add(new Contacts("uxiu","13261489758"));
            }
        }).start();
    }
}
