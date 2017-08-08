package com.android.project1.view.ui.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.project1.R;
import com.android.project1.model.ListQuestion;

import java.util.List;


/**
 * Created by Pc on 5/14/2017.
 */

public class HomeAdapter extends ArrayAdapter<ListQuestion> {
    Activity context;
    int resource;
    List<ListQuestion> objects;
    TextView tvName, tvTitle, tvContent, tvNothing, tvDate;
    ImageView imageView;

    public HomeAdapter(Activity context, int resource, List<ListQuestion> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.objects = objects;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = this.context.getLayoutInflater();
        convertView = inflater.inflate(this.resource, null);
        tvName = (TextView) convertView.findViewById(R.id.tvName);
        tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
        tvContent = (TextView) convertView.findViewById(R.id.tvContent);
        tvNothing = (TextView) convertView.findViewById(R.id.tvNothing);
        tvDate = (TextView) convertView.findViewById(R.id.tvDate);

        imageView = (ImageView) convertView.findViewById(R.id.imgAvatar);

        ListQuestion listQuestion = this.objects.get(position);

        tvName.setText(listQuestion.getName());
        tvTitle.setText(listQuestion.getTitle());
        tvContent.setText(listQuestion.getContent());
        tvNothing.setText(listQuestion.getCaigido());
        tvDate.setText(listQuestion.getTime());
        String url = listQuestion.getImgAvatar();

        imageView.setImageResource(R.mipmap.ic_launcher);
//        Picasso.with(context)
//                .load(url)      // optional
//                .resize(300,450)                        // optional
//                .rotate(0)                             // optional
//                .into(imageView);


        return convertView;
    }
}
