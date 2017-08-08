package com.android.project1.view.ui.adapter;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.project1.R;
import com.android.project1.view.ui.util.ImageLoaderUtils;

/**
 * @Date : 18/05/2017 16:22
 * @Author : ka
 */
public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    private static final int TYPE_AVATAR = 0;
    private static final int TYPE_NORMAL = 1;

    private ClickListener listener;
    private String avatarUrl;
    private String userName;
    private String email;

    private String[] items;
    private TypedArray itemImages;
    private Context context;

    public UserAdapter(Context context) {
        this.context = context;
        this.itemImages = context.getResources().obtainTypedArray(R.array.account_images);
        this.items = context.getResources().getStringArray(R.array.account_settings);
    }

    public void setListener(ClickListener listener) {
        this.listener = listener;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
        this.notifyDataSetChanged();
    }

    public void setUserName(String userName) {
        this.userName = userName;
        this.notifyDataSetChanged();
    }

    public void setEmail(String email) {
        this.email = email;
        this.notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layoutId = viewType == TYPE_AVATAR ? R.layout.account_avatar : R.layout.account_row;
        View view = LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        if (position > 0) {
            holder.tvName.setText(items[position - 1]);
            holder.imageView.setImageResource(itemImages.getResourceId(position - 1, 0));
            holder.getView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(v, holder.getAdapterPosition() - 1);
                }
            });
        } else {
            holder.tvName.setText(userName);
            holder.tvEmail.setText(email);
            if (avatarUrl != null) {
                ImageLoaderUtils.show(context, avatarUrl, holder.imageView);
            }
            holder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onAvatarClick();
                }
            });
            Animation animation = AnimationUtils.loadAnimation(context, R.anim.avatar_rotate);
            holder.imageView.setAnimation(animation);
        }
    }

    @Override
    public int getItemCount() {
        return items.length + 1;
    }

    @Override
    public int getItemViewType(int position) {
        return position == 0 ? TYPE_AVATAR : TYPE_NORMAL;
    }

    public interface ClickListener {
        void onClick(View view, int position);

        void onAvatarClick();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        View view;
        TextView tvEmail;
        ImageView imageView;
        TextView tvName;

        ViewHolder(View itemView) {
            super(itemView);
            this.view = itemView;
            imageView = (ImageView) itemView.findViewById(R.id.imgAvatar);
            tvEmail = (TextView) itemView.findViewById(R.id.tvEmail);
            tvName = (TextView) itemView.findViewById(R.id.tvName);
        }

        public View getView() {
            return view;
        }
    }

}
