package com.android.project1.view.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.project1.R;


@SuppressWarnings("ConstantConditions")
public abstract class AppBarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.up__appbar_base_activity);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public void setContentView(int layoutResID) {
        ViewGroup contentParent = (ViewGroup) findViewById(R.id.content);
        contentParent.removeAllViews();
        LayoutInflater.from(this).inflate(layoutResID, contentParent);
    }

    @Override
    public void setContentView(View view) {
        ViewGroup contentParent = (ViewGroup) findViewById(R.id.content);
        contentParent.removeAllViews();
        contentParent.addView(view);
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        ViewGroup contentParent = (ViewGroup) findViewById(R.id.content);
        contentParent.removeAllViews();
        contentParent.addView(view, params);
    }

    @Override
    public void addContentView(View view, ViewGroup.LayoutParams params) {
        ViewGroup contentParent = (ViewGroup) findViewById(R.id.content);
        contentParent.addView(view, params);
    }
}
