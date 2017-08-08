package com.android.project1.view.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.project1.R;
import com.android.project1.view.ui.LoginActivity;

/**
 * Created by DongPC on 5/10/2017.
 */

public class AdapterMenuNavigation extends BaseAdapter {
    private LayoutInflater layoutInflater;
    private Context context;

    public AdapterMenuNavigation(Context context) {
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        switch (position) {
            case 0:
                Animation animation = AnimationUtils.loadAnimation(context, R.anim.avatar_rotate);
                convertView = layoutInflater.inflate(R.layout.item_view_navigationheader, parent, false);
                LinearLayout linearLayout = (LinearLayout) convertView.findViewById(R.id.linear_header);
                linearLayout.setBackgroundColor(ContextCompat.getColor(context, R.color.dark_green));
                ImageView imageViewAvatar = (ImageView) convertView.findViewById(R.id.img_image);
                TextView textViewName = (TextView) convertView.findViewById(R.id.tv_name);
                TextView textViewEmail = (TextView) convertView.findViewById(R.id.tv_email);
                imageViewAvatar.setImageResource(R.drawable.ic_navigation_avatar);
                textViewName.setText("Kun Harry");
                textViewEmail.setText("kunharry95@gmail.com");
                imageViewAvatar.startAnimation(animation);

                break;
            case 1:
                convertView = layoutInflater.inflate(R.layout.item_navigation_text, parent, false);
                TextView textView = (TextView) convertView.findViewById(R.id.tv_content);
                textView.setText("Content");
                break;

            case 2:
                convertView = layoutInflater.inflate(R.layout.item_view_navigation, parent, false);
                ImageView imageView = (ImageView) convertView.findViewById(R.id.img_image);
                Button buttonHistory = (Button) convertView.findViewById(R.id.bt_text_navigation);
                imageView.setImageResource(R.drawable.ic_navigation_history);
                buttonHistory.setText("History");
                buttonHistory.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    }
                });
                break;
            case 3:
                convertView = layoutInflater.inflate(R.layout.item_view_navigation, parent, false);
                ImageView imageView1 = (ImageView) convertView.findViewById(R.id.img_image);
                Button buttonLogin = (Button) convertView.findViewById(R.id.bt_text_navigation);
                imageView1.setImageResource(R.drawable.ic_login);
                buttonLogin.setText("Login");
                buttonLogin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, LoginActivity.class);
                        context.startActivity(intent);
                    }
                });

                break;
        }
        return convertView;
    }
}
