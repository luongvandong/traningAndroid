package com.android.project1.view.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.project1.R;
import com.android.project1.api.ApiError;
import com.android.project1.api.ServiceGenerator;
import com.android.project1.api.service.AnswerService;
import com.android.project1.model.pojo.Question;
import com.android.project1.view.ui.util.Factory;
import com.android.project1.view.ui.util.ImageLoaderUtils;
import com.android.project1.view.ui.util.Logger;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostAnswerActivity extends AppBarActivity {

    public static final int REQUEST_CODE_POST_ANSWER = 111;
    private static final Logger LOGGER = Logger.getLogger(PostAnswerActivity.class);
    @BindView(R.id.edtContent)
    EditText edtContent;
    @BindView(R.id.ivCamera)
    ImageView ivCamera;
    String path;
    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_answer);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        i = getIntent();
    }

    @OnClick(R.id.ivCamera)
    public void selectImage() {
        Intent intent = new Intent(this, GalleryActivity.class);
        intent.putExtra(GalleryActivity.EXTRA_MAX_IMAGE_CAN_SELECT, 1);
        startActivityForResult(intent, REQUEST_CODE_POST_ANSWER);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ((requestCode == REQUEST_CODE_POST_ANSWER || resultCode == 1) && data != null) {
            String[] paths = data.getStringArrayExtra("data");
            if (paths.length > 0 && paths[0] != null) {
                path = paths[0];
                LOGGER.info(paths[0]);
                ImageLoaderUtils.show(this, path, ivCamera);
                edtContent.requestFocus();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.post_answer, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.btnPostAnswer) {
            postAnswer();
            startActivity(new Intent(this, DetailQuestionActivity.class));
            finish();
        }
        if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void postAnswer() {
        if (i != null && i.getParcelableExtra("question") != null) {
            String content = edtContent.getText().toString().trim();
            Question q = i.getParcelableExtra("question");
            AnswerService answerService = ServiceGenerator.create(AnswerService.class);

            if (content.isEmpty()) {
                Toast.makeText(this, "Content not empty", Toast.LENGTH_SHORT).show();
                edtContent.requestFocus();
                return;
            }

            if (path == null) {
                Toast.makeText(this, "Please choose image", Toast.LENGTH_SHORT).show();
                return;
            }
            answerService.postAnswer(Factory.requestBodyAnswer(q.getUserId(), q.getId(), content),
                    Factory.prepareFileAsPart("image", path)).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (!response.isSuccessful()) {
                        LOGGER.warn(ApiError.parseFromResponse(response));
                        return;
                    }

                    Toast.makeText(getApplicationContext(), "Post answer success", Toast.LENGTH_SHORT).show();
                    try {
                        LOGGER.info(response.body().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    LOGGER.error(t);
                }
            });
        }
    }
}
