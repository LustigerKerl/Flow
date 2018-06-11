package com.example.lwh.project_school.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lwh.ProjectSchool.R;

import java.util.ArrayList;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    private List<RecyclerItem> items;
    Context context;

    public RecyclerAdapter(Context context, ArrayList<RecyclerItem> items) {
        this.items = items;
        this.context = context;
    }

    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerAdapter.ViewHolder holder, int position) {

        RecyclerItem item = items.get(position);
        if (item.getContent().length() >= 20)
            holder.tvNoticeContent.setText(String.format("%s...", item.getContent().substring(0, 19)));
        else holder.tvNoticeContent.setText(item.getContent());

        holder.tvNoticeIdx.setText(Integer.toString(item.getIdx()));
        holder.tvNoticeWriter.setText(item.getWriter().replace("@dgsw.hs.kr",""));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvNoticeIdx, tvNoticeContent, tvNoticeWriter;

        public ViewHolder(View itemView) {
            super(itemView);
            tvNoticeIdx = itemView.findViewById(R.id.tvNoticeIdx);
            tvNoticeContent = itemView.findViewById(R.id.tvNoticeContent);
            tvNoticeWriter = itemView.findViewById(R.id.tvNoticeWriter);
        }
    }
}
