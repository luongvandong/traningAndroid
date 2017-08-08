package com.android.project1.view.ui.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.project1.R;
import com.android.project1.api.ServiceGenerator;
import com.android.project1.api.service.QuestionService;
import com.android.project1.model.ListQuestion;
import com.android.project1.model.pojo.Question;
import com.android.project1.model.response.ResponseQuestionData;
import com.android.project1.view.ui.DetailQuestionActivity;
import com.android.project1.view.ui.adapter.HomeAdapter;
import com.android.project1.view.ui.util.Logger;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    private static final Logger LOGGER = Logger.getLogger(HomeFragment.class);
    Context mContext;
    ListView lvQuestion;
    HomeAdapter adapter;
    ArrayList<ListQuestion> arrayList;
    QuestionService questionService = ServiceGenerator.create(QuestionService.class);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragment_home = inflater.inflate(R.layout.fragment_home, null);
        lvQuestion = (ListView) fragment_home.findViewById(R.id.lvHome);
        arrayList = new ArrayList<>();
//      lấy dữ liệu JSON trả về
        questionService.getListQuestion(1).enqueue(new Callback<ResponseQuestionData>() {
            @Override
            public void onResponse(Call<ResponseQuestionData> call, Response<ResponseQuestionData> response) {

                for (int i = 0; i < response.body().getQuestions().size(); i++) {
                    Question question = response.body().getQuestions().get(i);
                    arrayList.add(new ListQuestion(question.getUserName(), "2017/05/14 10:39", "Cau hoi: " + i
                            , question.getContent(), question.getQuestionImage(), "Nothing"));
                }
                adapter = new HomeAdapter(getActivity(), R.layout.custom_home, arrayList);
                lvQuestion.setAdapter(adapter);
                lvQuestion.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        Intent i = new Intent(getContext(), DetailQuestionActivity.class);
                        startActivity(i);
                    }
                });
            }

            @Override
            public void onFailure(Call<ResponseQuestionData> call, Throwable t) {
                LOGGER.error(t);
            }
        });
        return fragment_home;
    }
}
