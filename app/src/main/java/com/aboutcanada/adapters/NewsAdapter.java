package com.aboutcanada.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.aboutcanada.models.Rows;
import com.aboutcanada.view.activity.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

/**
 * Created by Dinesh on 25/06/18.
 */

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsHolder> {
    private String TAG=NewsAdapter.this.getClass().getName();
    private List<Rows> rowsList;
    private Context context;
    OnItemClickListener onItemClickListener;
    public NewsAdapter(List<Rows> rowsList, Context context, OnItemClickListener onItemClickListener) {
        this.rowsList = rowsList;
        this.context = context;
        this.onItemClickListener=onItemClickListener;
    }

    @Override
    public NewsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.news_rows,parent,false);
        NewsHolder newsHolder= new NewsHolder(v);
        return newsHolder;
    }

    @Override
    public void onBindViewHolder(NewsAdapter.NewsHolder holder, int position) {
        Log.d(TAG,"Title--"+ rowsList.get(position).getTitle());
        holder.tvTitle.setText(rowsList.get(position).getTitle());
        holder.tvDescription.setText(rowsList.get(position).getDescription());
        holder.click(rowsList.get(position),onItemClickListener);
        Glide.with(context).load(rowsList.get(position).getImageHref()).diskCacheStrategy(DiskCacheStrategy.SOURCE).placeholder(R.drawable.no_image).into(holder.ivNews);
    }

    @Override
    public int getItemCount() {
        return rowsList.size();
    }

    public interface OnItemClickListener {
        void onClick(Rows rows);
    }

    public class NewsHolder extends RecyclerView.ViewHolder {
        private TextView tvTitle, tvDescription;
        private ImageView ivNews;
        public NewsHolder(View v) {
            super(v);
            tvTitle = v.findViewById(R.id.tv_title);
            tvDescription = v.findViewById(R.id.tv_description);
            ivNews = v.findViewById(R.id.iv_news);
        }
        public void click(final Rows rows, final OnItemClickListener onItemClickListener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onClick(rows);
                }
            });
        }
    }
}

