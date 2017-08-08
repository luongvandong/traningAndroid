package com.android.project1.view.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;

import com.android.project1.AppConfig;
import com.android.project1.R;
import com.android.project1.view.ui.util.ImageLoaderUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ImageAnswerActivity extends AppBarActivity {

    @BindView(R.id.ivAnswer)
    ImageView ivAnswer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_answer);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent i = getIntent();
        if (i != null) {
            if (i.getStringExtra(DetailQuestionActivity.EXTRA_IMAGE_ANSWER) != null) {
                String answerImage = i.getStringExtra(DetailQuestionActivity.EXTRA_IMAGE_ANSWER);
                ImageLoaderUtils.show(this, AppConfig.URL_IMAGE_ANSWER + answerImage, ivAnswer);
            } else if (i.getStringExtra(DetailQuestionActivity.EXTRA_IMAGE_QUESTION) != null) {
                String questionImage = i.getStringExtra(DetailQuestionActivity.EXTRA_IMAGE_QUESTION);
                ImageLoaderUtils.show(this, AppConfig.URL + questionImage, ivAnswer);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
