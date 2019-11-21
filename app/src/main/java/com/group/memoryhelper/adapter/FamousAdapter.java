package com.group.memoryhelper.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.group.memoryhelper.R;
import com.group.memoryhelper.db.Famous;

import java.util.List;


public class FamousAdapter extends RecyclerView.Adapter<FamousAdapter.ViewHolder> {
    private Context context;
    private List<Famous> articles;
    public FamousAdapter(Context context, List<Famous> articles) {
        this.context = context;
        this.articles = articles;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_api, parent,false);
        return  new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Famous article = articles.get(position);
        holder.mTvAuthor.setText("Author:"+article.getAuthor());
        holder.mTvQuote.setText(article.getQuote());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener!=null){
                    listener.setOnItemClickListener(position);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView mTvAuthor;
        TextView mTvQuote;
        public ViewHolder(View itemView) {
            super(itemView);
            mTvAuthor = itemView.findViewById(R.id.tv_author);
            mTvQuote = itemView.findViewById(R.id.tv_quote);
        }
    }
    private ItemClickListener listener;
    public void setOnItemClickListener(ItemClickListener listener ){
        this.listener = listener;
    }
    public interface ItemClickListener{
        void setOnItemClickListener(int position);
    }
}
