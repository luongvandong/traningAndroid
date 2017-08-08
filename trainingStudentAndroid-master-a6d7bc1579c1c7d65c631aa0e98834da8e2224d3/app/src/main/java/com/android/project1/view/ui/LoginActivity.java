package com.android.project1.view.ui;

import android.os.Bundle;

import com.android.project1.R;
import com.android.project1.view.ui.fragment.LoginFragment;
import com.android.project1.view.ui.fragment.RegisterFragment;

public class LoginActivity extends AppBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        switchToLoginFragment();
    }

    public void switchToLoginFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, new LoginFragment()).commit();
    }

    public void switchToRegisterFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, new RegisterFragment()).commit();
    }
}