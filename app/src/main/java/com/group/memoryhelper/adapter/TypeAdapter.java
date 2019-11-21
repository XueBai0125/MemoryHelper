package com.group.memoryhelper.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.group.memoryhelper.R;
import com.group.memoryhelper.db.Type;

import java.util.ArrayList;


public class TypeAdapter extends RecyclerView.Adapter<TypeAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Type> merchantDatalist;

    public TypeAdapter(Context context, ArrayList<Type> merchantDatalist) {
        this.context = context;
        this.merchantDatalist = merchantDatalist;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_type, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Type item = merchantDatalist.get(position);
        holder.tvName.setText(item.getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.setOnItemClickListener(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return merchantDatalist.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;

        public ViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
        }
    }

    private ItemClickListener listener;

    public void setOnItemClickListener(ItemClickListener listener) {
        this.listener = listener;
    }

    public interface ItemClickListener {
        void setOnItemClickListener(int position);
    }
}
