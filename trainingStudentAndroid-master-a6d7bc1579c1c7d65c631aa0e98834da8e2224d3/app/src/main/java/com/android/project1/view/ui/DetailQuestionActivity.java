package com.android.project1.view.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.project1.AppConfig;
import com.android.project1.R;
import com.android.project1.api.ApiError;
import com.android.project1.api.ServiceGenerator;
import com.android.project1.api.service.QuestionService;
import com.android.project1.model.pojo.Answer;
import com.android.project1.model.pojo.Question;
import com.android.project1.model.response.ResponseQuestionDetail;
import com.android.project1.view.ui.adapter.AnswerAdapter;
import com.android.project1.view.ui.util.ImageLoaderUtils;
import com.android.project1.view.ui.util.Logger;
import com.android.project1.view.ui.widget.DividerItemDecoration;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by Pc on 5/15/2017.
 */

public class DetailQuestionActivity extends AppBarActivity {

    public static final String EXTRA_IMAGE_ANSWER = "EXTRA_IMAGE_ANSWER";
    public static final String EXTRA_IMAGE_QUESTION = "EXTRA_IMAGE_QUESTION";
    private static final Logger LOGGER = Logger.getLogger(DetailQuestionActivity.class);
    @BindView(R.id.ivUser)
    ImageView ivUser;
    @BindView(R.id.tvUsername)
    TextView tvUsername;
    @BindView(R.id.ivQuestion)
    ImageView ivQuestion;
    @BindView(R.id.tvContent)
    TextView tvContent;
    @BindView(R.id.tv)
    TextView tv;
    @BindView(R.id.rcvAnswer)
    RecyclerView rcvAnswer;
    @BindView(R.id.linearListAnswer)
    LinearLayout linearLIstAnswer;
    @BindView(R.id.btnPostAnswer)
    Button btnPostAnswer;
    Question q;
    private List<Answer> answers;
    private AnswerAdapter answerAdapter;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_detail_question);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        rcvAnswer.setHasFixedSize(true);
        rcvAnswer.setLayoutManager(manager);
        rcvAnswer.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        rcvAnswer.setItemAnimator(new DefaultItemAnimator());

        answerAdapter = new AnswerAdapter(this, answers);
        rcvAnswer.setAdapter(answerAdapter);

        getListAnswer();

        answerAdapter.setListener(new AnswerAdapter.ImageListener() {
            @Override
            public void onImageListener(View view, int position) {
                Toast.makeText(DetailQuestionActivity.this, answers.get(position).getAnswerImage(), Toast.LENGTH_SHORT)
                        .show();
                Intent i = new Intent(DetailQuestionActivity.this, ImageAnswerActivity.class);
                i.putExtra(EXTRA_IMAGE_ANSWER, answers.get(position).getAnswerImage());
                startActivity(i);
            }
        });
    }

    @OnClick(R.id.btnPostAnswer)
    public void postAnswer() {
        Intent i = new Intent(this, PostAnswerActivity.class);
        if (q != null) {
            i.putExtra("question", q);
            startActivity(i);
        }
    }

    @OnClick(R.id.ivQuestion)
    public void viewImage() {
        Intent i = new Intent(DetailQuestionActivity.this, ImageAnswerActivity.class);
        i.putExtra(EXTRA_IMAGE_QUESTION, q.getQuestionImage());
        startActivity(i);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void getListAnswer() {
        QuestionService service = ServiceGenerator.create(QuestionService.class);
        service.getQuestionDetail("1").enqueue(new Callback<ResponseQuestionDetail>() {
            @Override
            public void onResponse(Call<ResponseQuestionDetail> call, Response<ResponseQuestionDetail> response) {
                if (!response.isSuccessful()) {
                    LOGGER.warn(ApiError.parseFromResponse(response));
                    return;
                }
                updateData(response.body());
                LOGGER.info(response.body());
            }

            @Override
            public void onFailure(Call<ResponseQuestionDetail> call, Throwable t) {
                LOGGER.error(t);
            }
        });
    }

    private void updateData(ResponseQuestionDetail data) {
        q = data.question;
        tvUsername.setText(q.getUserName());
        tvContent.setText(q.getContent());
        if (q.userImage != null) {
            ImageLoaderUtils.show(this, AppConfig.URL + q.getUserImage(), ivUser);
        } else {
            ivUser.setVisibility(View.GONE);
        }
        if (q.questionImage != null && !q.questionImage.isEmpty()) {
            ImageLoaderUtils.show(this, AppConfig.URL + q.getQuestionImage(), ivQuestion);
        } else {
            ivQuestion.setVisibility(View.GONE);
        }
        answers = data.question.answers;
        Collections.reverse(answers);
        answerAdapter.setAnswers(answers);
        linearLIstAnswer.setVisibility(answers.size() < 1 ? View.GONE : View.VISIBLE);
        tv.setVisibility(answers.size() < 1 ? View.VISIBLE : View.GONE);
    }
}