package com.example.intent.figerset.DateAdapter;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.support.v7.widget.RecyclerView;

import com.example.intent.figerset.Model.Contacts;
import com.example.intent.figerset.R;

import java.util.List;

public class DContactsAdapter extends RecyclerView.Adapter<DContactsAdapter.ViewHolder> {

    private Context context;
    private List<Contacts> data;

    public DContactsAdapter(Context context,List<Contacts> data){
        this.context = context;
        this.data = data;

    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_recyclerview,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        String name = data.get(position).getName();
        String number = data.get(position).getNumber();
        if (name != null)
            holder.tv_name.setText(data.get(position).getName());
        else
            holder.tv_name.setText("佚名");
        holder.tv_number.setText(data.get(position).getNumber());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("这里是点击每一行item的响应事件",""+position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView tv_name;
        private TextView tv_number;



        public ViewHolder(View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.tv_dcon_name);
            tv_number = itemView.findViewById(R.id.tv_dcon_number);

        }
    }
}