package com.example.intent.figerset.Fragment;

import android.content.Context;
import com.google.gson.Gson;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.intent.figerset.DateAdapter.EContactsAdapter;
import com.example.intent.figerset.Fragment.base.BaseFragment;
import com.example.intent.figerset.Model.Contacts;
import com.example.intent.figerset.R;
import com.example.intent.figerset.service.ContactWriteService;

import java.util.ArrayList;

import java.util.List;

import static com.example.intent.figerset.utils.ConstantUtils.CONTACT_WRITE_FLAG;
import static com.example.intent.figerset.utils.ConstantUtils.INDEX_FLAG;


public class EContactsFragment extends BaseFragment implements View.OnClickListener {

    private LinearLayout ll_contacts_below;
    private TextView tv_contacts_count;
    private Button btn_contacts_save;
    private Button btn_contacts_all;
    private RecyclerView rc_econtacts;
    private List<Contacts> contactsList = new ArrayList<>();
    private List<Contacts> callRmList = new ArrayList<>();
    private EContactsAdapter EContactsAdapter;
    private SwipeRefreshLayout eContacts_swipe_refresh;

    //分页查询
    private int Coffset = 0;//数据库中数据的起始位置
    private int Cmax = 20;//每页10条信息

    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            int type = msg.what;
            if (type == CONTACT_WRITE_FLAG) {
                boolean flag = (boolean) msg.obj;
                if (flag) {
                    Toast.makeText(getActivity(), "列表联系人写入SD卡成功", Toast.LENGTH_LONG).show();
                    EContactsAdapter.deleteItem();
                } else {
                    Toast.makeText(getActivity(), "列表联系人写入SD卡失败", Toast.LENGTH_LONG).show();
                }
                EContactsAdapter.setShowBox();
                EContactsAdapter.notifyDataSetChanged();
            }
        }
    };

    @Override
    protected void initPrepare() {

    }

    @Override
    protected void onInvisible() {

    }

    @Override
    protected void initData() {
        readContact(Coffset, Cmax);
    }

    @Override
    protected void initViews(View view) {
        ll_contacts_below = view.findViewById(R.id.ll_contacts_below);
        tv_contacts_count = view.findViewById(R.id.tv_contacts_count);

        btn_contacts_save = view.findViewById(R.id.btn_contacts_save);
        btn_contacts_save.setOnClickListener(this);

        btn_contacts_all = view.findViewById(R.id.btn_contacts_all);
        btn_contacts_all.setOnClickListener(this);

        rc_econtacts = view.findViewById(R.id.rc_econtacts);
        eContacts_swipe_refresh = view.findViewById(R.id.econtacts_swipe_refresh);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rc_econtacts.setLayoutManager(layoutManager);

        EContactsAdapter = new EContactsAdapter(contactsList,getActivity(),eContacts_swipe_refresh,ll_contacts_below,tv_contacts_count);
        rc_econtacts.setAdapter(EContactsAdapter);
        EContactsAdapter.setRecyclerViewOnItemClickListener(new EContactsAdapter.RecyclerViewOnItemClickListener(){

            @Override
            public void onItemClickListener(View view, int position) {
                EContactsAdapter.setSelectItem(position);
            }

            @Override
            public boolean onItemLongClickListener(View view, int position) {
                EContactsAdapter.setShowBox();
                EContactsAdapter.setSelectItem(position);
                EContactsAdapter.notifyDataSetChanged();
                return true;
            }
        });
        eContacts_swipe_refresh.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        eContacts_swipe_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener(){
            public void onRefresh(){
                refreshContact(Coffset,Cmax);
            }
        });
    }
    private void refreshContact(final int offset, final int max) {
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
                        readContact(offset, max);
                        eContacts_swipe_refresh.setRefreshing(false);
                        EContactsAdapter.notifyDataSetChanged();
                        Toast.makeText(getActivity(), "数据刷新成功", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }).start();
    }

    private void readContact(int offset, int max) {
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_econtacts;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_contacts_save:
                writeItemIntoSDcard();
                break;
            case R.id.btn_contacts_all:
                EContactsAdapter.choiceAll();
                break;
        }
    }

    private void writeItemIntoSDcard() {
        callRmList = EContactsAdapter.choiceItem();
        if(!callRmList.isEmpty()){
            Intent intent = new Intent(getActivity(), ContactWriteService.class);
            intent.putExtra(ContactWriteService.CONTACT_LIST, new Gson().toJson(callRmList));
            intent.putExtra(INDEX_FLAG, CONTACT_WRITE_FLAG);
            getActivity().startService(intent);
            ContactWriteService.setUpdateUI(this);
        }
        else{
            Toast.makeText(getActivity(),"请选择通话记录条目",Toast.LENGTH_SHORT).show();
        }
    }
    public interface RecyclerViewOnItemClickListener {
        void onItemClickListener(View view, int position);
        boolean onItemLongClickListener(View view, int position);
    }
    public void updateUI(Message message) {
        handler.sendMessage(message);
    }
}
