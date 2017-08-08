package com.android.project1.view.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.project1.R;
import com.android.project1.view.ui.util.ImageLoaderUtils;
import com.android.project1.view.ui.widget.SquareImageView;

import java.util.List;

public class GalleryAdapter extends BaseAdapter {

    private Context context;
    private OnImageSelectListener imageSelectListener;
    private OnCameraSelectListener onCameraSelectListener;
    private List<IndexedImage> images;
    private int totalSelected = 0;
    private int maxImageCanSelect;

    public GalleryAdapter(Context context, OnCameraSelectListener onCameraSelectListener,
                          OnImageSelectListener imageSelectListener, List<IndexedImage> images,
                          int maxImageCanSelect, int totalSelected) {
        this.context = context;
        this.onCameraSelectListener = onCameraSelectListener;
        this.imageSelectListener = imageSelectListener;
        this.images = images;
        this.maxImageCanSelect = maxImageCanSelect;
        this.totalSelected = totalSelected;
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public Object getItem(int position) {
        return images.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public int getItemViewType(int position) {
        return position == 0 ? 0 : 1;
    }

    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final ViewHolder holder;
        if (getItemViewType(position) == 0) {
            if (convertView == null) {
                ImageView iv = new SquareImageView(context);
                iv.setImageResource(R.drawable.ic8_camera);
                iv.setBackgroundResource(android.R.color.white);
                iv.setScaleType(ImageView.ScaleType.CENTER);
                iv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (totalSelected >= maxImageCanSelect) {
                            Toast.makeText(context, "Only selected max is " + maxImageCanSelect + " photos",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            onCameraSelectListener.onCameraSelect();
                        }
                    }
                });
                convertView = iv;
            }
            return convertView;
        }
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_image_in_galery, null);
            holder.imageView = (ImageView) convertView.findViewById(R.id.thumbImage);
            holder.textView = (TextView) convertView.findViewById(R.id.tvSelect);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final IndexedImage image = (IndexedImage) getItem(position);
        ImageLoaderUtils.show(context, images.get(position).url, holder.imageView);
        if (image.selectedIndex < 0) {
            holder.textView.setVisibility(View.INVISIBLE);
        } else {
            holder.textView.setVisibility(View.VISIBLE);
            holder.textView.setText(String.valueOf(image.selectedIndex));
        }

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IndexedImage currentImg = images.get(position);
                if (currentImg.selectedIndex > 0) {
                    for (IndexedImage i : images) {
                        if (i.selectedIndex > currentImg.selectedIndex) {
                            i.selectedIndex--;
                        }
                    }
                    currentImg.selectedIndex = -1;
                    totalSelected--;
                } else {
                    if (totalSelected < maxImageCanSelect) {
                        currentImg.selectedIndex = ++totalSelected;
                    } else {
                        Toast.makeText(context, "Only selected max is " + maxImageCanSelect + " photos",
                                Toast.LENGTH_SHORT).show();
                    }
                }
                imageSelectListener.onImageSelect(position);
            }
        });
        holder.textView.setId(position);
        holder.imageView.setId(position);
        holder.id = position;
        return convertView;
    }

    public interface OnImageSelectListener {
        void onImageSelect(int position);
    }

    public interface OnCameraSelectListener {
        void onCameraSelect();
    }

    private static class ViewHolder {
        ImageView imageView;
        TextView textView;
        int id;
    }

    public static class IndexedImage {
        public String url;
        public int selectedIndex = -1;
    }
}