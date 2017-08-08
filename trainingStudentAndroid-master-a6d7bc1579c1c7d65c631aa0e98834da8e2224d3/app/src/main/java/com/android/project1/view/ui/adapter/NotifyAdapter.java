package com.android.project1.view.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.project1.R;
import com.android.project1.model.pojo.Notify;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @Date : 09/05/2017
 * @Author : ka
 */
public class NotifyAdapter extends RecyclerView.Adapter<NotifyAdapter.ViewHolder> {

    private Context context;
    private List<Notify> list;
    private LongClickListener listener;

    public NotifyAdapter(Context context, List<Notify> list) {
        this.context = context;
        this.list = list;
    }

    public void setList(List<Notify> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public void setListener(LongClickListener listener) {
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_noti, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Notify noti = list.get(position);
        holder.tvContent.setText(noti.getContent());
        holder.tvName.setText(noti.getName());
        holder.view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                listener.onLongClick(v, holder.getAdapterPosition());
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    public interface LongClickListener {
        void onLongClick(View v, int position);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tvName)
        TextView tvName;
        @BindView(R.id.tvContent)
        TextView tvContent;
        @BindView(R.id.view)
        LinearLayout view;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
