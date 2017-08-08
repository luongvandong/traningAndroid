package com.android.project1.view.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.project1.R;
import com.android.project1.model.UserHelper;
import com.android.project1.model.pojo.User;
import com.android.project1.presenter.UserPresenter;
import com.android.project1.view.UserView;
import com.android.project1.view.ui.LoginActivity;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment implements UserView {

    @BindView(R.id.btnLogin)
    Button btnLogin;
    @BindView(R.id.tvRegister)
    TextView tvRegister;
    @BindView(R.id.edtUsername)
    EditText edtUsername;
    @BindView(R.id.edtPassword)
    EditText edtPassword;

    UserHelper helper = new UserHelper(getActivity());
    UserPresenter presenter = new UserPresenter(this, helper);

    public LoginFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.bind(this, v);

        String tv = tvRegister.getText().toString();
        Spannable spannable = new SpannableString(tv);
        spannable.setSpan(new UnderlineSpan(), 16, tv.length(), Spanned.SPAN_MARK_MARK);
        tvRegister.setText(spannable);
        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ((LoginActivity) getActivity()).switchToRegisterFragment();
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkValid(edtUsername, edtPassword);
            }
        });
        return v;
    }


    private void checkValid(EditText edtUsername, EditText edtPassword) {
        if (edtUsername.getText().toString().trim().isEmpty()) {
            Toast.makeText(getContext(), "Nhập username", Toast.LENGTH_SHORT).show();
            edtUsername.requestFocus();
            return;
        }
        if (edtPassword.getText().toString().length() < 5) {
            Toast.makeText(getContext(), "Password phải lớn hơn 5 kí tự", Toast.LENGTH_SHORT).show();
            edtPassword.requestFocus();
            return;
        }
        presenter.login(new User(edtUsername.getText().toString().trim(), edtPassword.getText().toString()));
    }

    @Override
    public void registerSuccess(User user) {

    }

    @Override
    public void loginFail(String msg) {
        Toast.makeText(getContext(), "Username hoặc password không đúng", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void loginSuccess(String msg) {
        Toast.makeText(getContext(), "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
        Intent i = getActivity().getIntent();
        if (i != null && i.getAction() != null && i.getAction().equals(PostQuestionFragment.ACTION_POST)) {
            getActivity().finish();
        }
    }

    @Override
    public void registerFail(String msg) {

    }
}
