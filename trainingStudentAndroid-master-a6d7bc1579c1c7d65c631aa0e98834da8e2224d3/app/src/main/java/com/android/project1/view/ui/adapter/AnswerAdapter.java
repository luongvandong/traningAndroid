package com.android.project1.view.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.project1.AppConfig;
import com.android.project1.R;
import com.android.project1.model.pojo.Answer;
import com.android.project1.view.ui.util.ImageLoaderUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @Date : 23/05/2017 15:23
 * @Author : ka
 */
public class AnswerAdapter extends RecyclerView.Adapter<AnswerAdapter.AnswerHolder> {

    private Context context;
    private List<Answer> answers;
    private ImageListener listener;

    public AnswerAdapter(Context context, List<Answer> answers) {
        this.context = context;
        this.answers = answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
        notifyDataSetChanged();
    }

    public void setListener(ImageListener listener) {
        this.listener = listener;
    }

    @Override
    public AnswerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_answer, parent, false);
        return new AnswerHolder(view);
    }

    @Override
    public void onBindViewHolder(final AnswerHolder holder, int position) {
        Answer answer = answers.get(position);
        holder.tvContent.setText(answer.getContent());
        holder.tvUserId.setText(answer.getUserId());
        if (answer.answerImage != null) {
            holder.ivAnswer.setVisibility(View.VISIBLE);
            ImageLoaderUtils.show(context, AppConfig.URL_IMAGE_ANSWER + answer.answerImage, holder.ivAnswer);
            holder.ivAnswer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onImageListener(v, holder.getAdapterPosition());
                }
            });
        } else {
            holder.ivAnswer.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return answers != null ? answers.size() : 0;
    }


    public interface ImageListener {
        void onImageListener(View view, int position);
    }

    static class AnswerHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tvUserId)
        TextView tvUserId;
        @BindView(R.id.tvContent)
        TextView tvContent;
        @BindView(R.id.ivAnswer)
        ImageView ivAnswer;

        public AnswerHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
