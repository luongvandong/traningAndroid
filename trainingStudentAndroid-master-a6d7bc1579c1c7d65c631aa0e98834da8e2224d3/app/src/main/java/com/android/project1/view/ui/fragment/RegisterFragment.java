package com.android.project1.view.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFragment extends Fragment implements UserView {

    @BindView(R.id.tvLogin)
    TextView tvLogin;
    @BindView(R.id.edtEmail)
    EditText edtEmail;
    @BindView(R.id.edtUsername)
    EditText edtUsername;
    @BindView(R.id.edtPassword)
    EditText edtPassword;
    @BindView(R.id.edtConfirmPassword)
    EditText edtConfirmPassword;
    @BindView(R.id.btnRegister)
    Button btnRegister;

    UserHelper helper = new UserHelper(getActivity());
    UserPresenter presenter = new UserPresenter(this, helper);

    public RegisterFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_register, container, false);
        ButterKnife.bind(this, v);
        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((LoginActivity) getActivity()).switchToLoginFragment();
            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkValid(edtEmail, edtUsername, edtPassword, edtConfirmPassword);
            }
        });
        return v;
    }

    private void checkValid(EditText edtEmail, EditText edtUsername, EditText edtPassword, EditText edtConfirmPassword) {
        String email = edtEmail.getText().toString().trim();
        String username = edtUsername.getText().toString().trim();
        String password = edtPassword.getText().toString();
        String confirm = edtConfirmPassword.getText().toString();

        if (!isEmailValid(email)) {
            Toast.makeText(getContext(), "Địa chỉ email không hợp lệ", Toast.LENGTH_SHORT).show();
            edtEmail.requestFocus();
            return;
        }

        if (username.isEmpty()) {
            Toast.makeText(getContext(), "Username không được để trống", Toast.LENGTH_SHORT).show();
            edtUsername.requestFocus();
            return;
        }

        if (password.length() < 5) {
            Toast.makeText(getContext(), "Password phải lớn hơn 5 kí tự", Toast.LENGTH_SHORT).show();
            edtPassword.requestFocus();
            return;
        }
        if (!password.equals(confirm)) {
            Toast.makeText(getContext(), "Password không khớp", Toast.LENGTH_SHORT).show();
            edtConfirmPassword.requestFocus();
            return;
        }
        presenter.register(new User(username, email, password));
    }

    public boolean isEmailValid(String email) {
        boolean isValid = false;

        Pattern pattern = Pattern.compile(getString(R.string.pattern), Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        if (matcher.matches() && email.length() > 0) {
            isValid = true;
        }
        return isValid;
    }

    @Override
    public void registerSuccess(User user) {
        Toast.makeText(getContext(), user.email, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void loginFail(String msg) {

    }

    @Override
    public void loginSuccess(String msg) {

    }

    @Override
    public void registerFail(String msg) {
        Toast.makeText(getContext(), "Đăng ký không thành công", Toast.LENGTH_SHORT).show();
    }
}
