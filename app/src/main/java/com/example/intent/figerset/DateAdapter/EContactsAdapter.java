package com.example.intent.figerset.DateAdapter;

import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.intent.figerset.Fragment.EContactsFragment;
import com.example.intent.figerset.Model.Contacts;
import com.example.intent.figerset.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EContactsAdapter extends   RecyclerView.Adapter<EContactsAdapter.ViewHolder> implements View.OnClickListener, View.OnLongClickListener {

    private RecyclerViewOnItemClickListener onItemClickListener;//接口实例
    public Map<Integer, Boolean> map = new HashMap<>();// 存储勾选框状态的map集合
    private List<Contacts> mList;
    private int mCount = 0;//选中条目的数量
    private TextView mTvCount;//显示选中条目数量

    private void initMap() {//初始化map集合,默认为不选中
        for (int i = 0; i < mList.size(); i++) {
            map.put(i, false);
        }
    }
    public void setRecyclerViewOnItemClickListener(RecyclerViewOnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public EContactsAdapter(List<Contacts> contactsList, FragmentActivity activity, SwipeRefreshLayout eContacts_swipe_refresh, LinearLayout ll_contacts_below, TextView tv_contacts_count) {

    }

    @NonNull
    @Override
    public EContactsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull EContactsAdapter.ViewHolder viewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public boolean onLongClick(View view) {
        return false;
    }

    public void deleteItem() {
    }

    public void setShowBox() {
    }

    public void setSelectItem(int position) {
        if (map.get(position)) {
            map.put(position, false);
        } else {
            map.put(position, true);
        }
        //[2]底部below的count设置为对应数量
        mCount = 0;
        for (int i = 0; i < map.size(); i++) {
            if (map.get(i) == true) {
                mCount++;
            }
        }
        mTvCount.setText("" + mCount);
        notifyItemChanged(position);
        mCount = 0;
    }

    public void choiceAll() {
    }

    public List<Contacts> choiceItem() {
        return new ArrayList<>();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_name;
        private TextView tv_date;
        private TextView tv_duration;
        private TextView tv_location;
        private ImageView iv_type;
        private CheckBox check_box;
        private LinearLayout ll_call_checkbox;



        public ViewHolder(View itemView) {
            super(itemView);

        }
    }
    public interface RecyclerViewOnItemClickListener {
        void onItemClickListener(View view, int position);
        boolean onItemLongClickListener(View view, int position);
    }

}
