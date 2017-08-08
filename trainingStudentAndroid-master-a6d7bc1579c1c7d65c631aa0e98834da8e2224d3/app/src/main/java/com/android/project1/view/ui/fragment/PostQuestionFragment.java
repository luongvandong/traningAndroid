package com.android.project1.view.ui.fragment;


import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.project1.R;
import com.android.project1.api.ApiError;
import com.android.project1.api.ServiceGenerator;
import com.android.project1.api.service.QuestionService;
import com.android.project1.model.pojo.User;
import com.android.project1.view.ui.LoginActivity;
import com.android.project1.view.ui.prefs.UserPrefs;
import com.android.project1.view.ui.util.Factory;
import com.android.project1.view.ui.util.Logger;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;


public class PostQuestionFragment extends Fragment {

    public static final String ACTION_POST = "ACTION_POST";

    private static final Logger LOGGER = Logger.getLogger(PostQuestionFragment.class);
    private static final int REQUEST_CODE = 1;
    private static final int RESULT_LOAD_IMAGE = 11;

    @BindView(R.id.edtContent)
    EditText edtContent;
    @BindView(R.id.edtTitle)
    EditText edtTitle;
    @BindView(R.id.btnPost)
    Button buttonPost;
    @BindView(R.id.imgBtnCamera)
    ImageButton imageButtonCamera;
    @BindView(R.id.ivCamera)
    ImageView imageViewCamera;

    private String picturePath;

    public PostQuestionFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_post_question, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.ivCamera)
    public void getImageFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 2);
    }

    @OnClick(R.id.btnPost)
    public void postQuestion() {
        User user = UserPrefs.getUser();
        String title = edtTitle.getText().toString().trim();
        String content = edtContent.getText().toString().trim();

        if (title.length() < 1) {
            Toast.makeText(getContext(), "Title not empty", Toast.LENGTH_SHORT).show();
            edtTitle.requestFocus();
            return;
        }
        if (content.length() < 1) {
            Toast.makeText(getContext(), "Content not empty", Toast.LENGTH_SHORT).show();
            edtContent.requestFocus();
            return;
        }
        if ((user.userName == null)) {
            Intent i = new Intent(getContext(), LoginActivity.class);
            i.setAction(ACTION_POST);
            getActivity().startActivity(i);
            return;
        }

        QuestionService questionService = ServiceGenerator.create(QuestionService.class);

        questionService.postQuestion(Factory.requestBodyQuestion(user.userId, content), Factory.prepareFileAsPart
                ("image", picturePath)).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (!response.isSuccessful()) {
                    LOGGER.warn(ApiError.parseFromResponse(response));
                    return;
                }

                Toast.makeText(getActivity(), "Post Success", Toast.LENGTH_SHORT).show();
                   /* try {
                        JSONObject object = new JSONObject(response.body().string());
                        JSONObject data = object.getJSONObject("data");
                        String userName = data.getString("user_name");
                        Toast.makeText(getActivity(), "" + userName, Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {

                    }*/
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

    @OnClick(R.id.imgBtnCamera)
    public void doCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Uri selectedImage;
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            Bundle bundle = data.getExtras();
            Bitmap bitmap = (Bitmap) bundle.get("data");
            selectedImage = data.getData();
            picturePath = selectedImage.getPath();
            imageViewCamera.setImageBitmap(bitmap);
        }
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = getActivity().getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            picturePath = cursor.getString(columnIndex);
            cursor.close();
            imageViewCamera.setImageBitmap(BitmapFactory.decodeFile(picturePath));
            Snackbar.make(getView(), "You choose image success", Snackbar.LENGTH_INDEFINITE).
                    setAction("Dismiss", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    }).show();
        }
    }
}
