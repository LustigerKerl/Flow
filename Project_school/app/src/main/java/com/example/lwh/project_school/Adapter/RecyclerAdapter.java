package com.example.lwh.project_school.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lwh.project_school.Activity.Notice.NoticeDetail.NoticeDetailActivity;
import com.example.lwh.project_school.R;

import java.util.ArrayList;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    private List<RecyclerItem> items = new ArrayList<>();
    private Context context;
    private String type;

    public RecyclerAdapter(Context context, String type) {
        this.context = context;
        this.type = type;
    }

    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = null;
        switch (type) {
            case "notice":
                view = LayoutInflater.from(context).inflate(R.layout.layout, parent, false);
                break;
            case "result":
                view = LayoutInflater.from(context).inflate(R.layout.layout2, parent, false);
                break;
        }
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerAdapter.ViewHolder holder, int position) {
        switch (type) {
            case "notice":
                RecyclerItem recyclerItem = items.get(position);
                if (recyclerItem.getContent().length() >= 20)
                    holder.tvNoticeContent.setText(String.format("%s  ...", recyclerItem.getContent().substring(0, 19)));
                else holder.tvNoticeContent.setText(recyclerItem.getContent());
                holder.tvNoticeIdx.setText(Integer.toString(recyclerItem.getIdx()));
                holder.tvNoticeWriter.setText(recyclerItem.getWriter().replace("@dgsw.hs.kr", ""));
                break;
            case "result":
                RecyclerItem recyclerItem1 = items.get(position);
                holder.tvSubType.setText(recyclerItem1.getType());
                holder.tvSubStartTime.setText(recyclerItem1.getStart_date());
                holder.tvSubEndTime.setText(recyclerItem1.getEnd_date());
                holder.tvSubReason.setText(recyclerItem1.getReason());
        }

    }

    public void addRecyclerItems(ArrayList<RecyclerItem> items) {
        this.items.addAll(items);
    }

    public void clear() {
        items.clear();
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvNoticeIdx, tvNoticeContent, tvNoticeWriter,
                tvSubType, tvSubStartTime, tvSubEndTime, tvSubReason;

        public ViewHolder(View itemView) {
            super(itemView);
            switch (type) {
                case "notice":
                    itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(context, NoticeDetailActivity.class);
                            intent.putExtra("idx", Integer.toString(items.get(getAdapterPosition()).getIdx()));
                            context.startActivity(intent);
                        }
                    });
                    tvNoticeIdx = itemView.findViewById(R.id.tvNoticeIdx);
                    tvNoticeContent = itemView.findViewById(R.id.tvNoticeContent);
                    tvNoticeWriter = itemView.findViewById(R.id.tvNoticeWriter);
                    break;
                case "result":
                    tvSubType = itemView.findViewById(R.id.tvSubType);
                    tvSubStartTime = itemView.findViewById(R.id.tvSubStartTime);
                    tvSubEndTime = itemView.findViewById(R.id.tvSubEndTime);
                    tvSubReason = itemView.findViewById(R.id.tvSubReason);
                    break;
            }

        }
    }
}
