package com.android.project1.view.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.android.project1.AppConfig;
import com.android.project1.R;
import com.android.project1.api.ApiError;
import com.android.project1.api.ServiceGenerator;
import com.android.project1.api.service.UserService;
import com.android.project1.model.pojo.User;
import com.android.project1.view.ui.adapter.UserAdapter;
import com.android.project1.view.ui.fragment.HomeFragment;
import com.android.project1.view.ui.fragment.NotifyFragment;
import com.android.project1.view.ui.fragment.PostQuestionFragment;
import com.android.project1.view.ui.prefs.UserPrefs;
import com.android.project1.view.ui.util.Factory;
import com.android.project1.view.ui.util.FragmentController;
import com.android.project1.view.ui.util.Logger;
import com.android.project1.view.ui.widget.DividerItemDecoration;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity implements View.OnClickListener,
        BottomNavigationView.OnNavigationItemSelectedListener, UserAdapter.ClickListener {

    private static final Logger LOGGER = Logger.getLogger(MainActivity.class);

    @BindView(R.id.rcv)
    RecyclerView rcvNavigation;
    @BindView(R.id.drawlayout)
    DrawerLayout drawerLayout;
    @BindView(R.id.bottomNavigation)
    BottomNavigationView bottomNavigationView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private UserAdapter userAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        makeBottomNavigation();
        addMenuNavigation();

        FragmentController.addFragment(MainActivity.this, new HomeFragment());
    }

    private void makeBottomNavigation() {
        bottomNavigationView.inflateMenu(R.menu.bottom_menu);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
    }

    private void addMenuNavigation() {
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string
                .navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        userAdapter = new UserAdapter(this);
        rcvNavigation.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rcvNavigation.setLayoutManager(layoutManager);
        rcvNavigation.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        rcvNavigation.setItemAnimator(new DefaultItemAnimator());

        rcvNavigation.setAdapter(userAdapter);
        userAdapter.setListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_menu:
                drawerLayout.openDrawer(Gravity.START);
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_view, menu);
        MenuItem item = menu.findItem(R.id.searchView);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setQueryHint("Type and Search");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_home:
                FragmentController.replaceFragment(MainActivity.this, new HomeFragment());
                break;
            case R.id.item_message:
                FragmentController.replaceFragment(MainActivity.this, new PostQuestionFragment());
                break;
            case R.id.item_notification:
                FragmentController.replaceFragment(MainActivity.this, new NotifyFragment());
                break;
        }
        return true;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
//                showDialog();
                break;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void showDialog() {
        AlertDialog alertDialog = new AlertDialog.Builder(this).setMessage("Are you sure want to exit?")
                .setCancelable(true).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).create();
        alertDialog.show();
    }

    @Override
    public void onClick(View view, int position) {
        if (position == 1) {
            startActivity(new Intent(this, LoginActivity.class));
        }
    }

    @Override
    public void onAvatarClick() {
        setAvatar();
    }

    private void setAvatar() {
        Intent intent = new Intent(this, GalleryActivity.class);
        intent.putExtra(GalleryActivity.EXTRA_MAX_IMAGE_CAN_SELECT, 1);
        startActivityForResult(intent, 0);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ((requestCode == 0 || resultCode == 1) && data != null) {
            String[] paths = data.getStringArrayExtra("data");
            if (paths.length > 0 && paths[0] != null) {
                updateProfile(paths[0]);
            }
        }
    }

    private void updateProfile(String path) {
        final UserService userService = ServiceGenerator.create(UserService.class);

        userService.updateImageProfile(RequestBody.create(null, "5"), Factory.prepareFileAsPart("image", path))
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (!response.isSuccessful()) {
                            LOGGER.error(ApiError.parseFromResponse(response));
                            return;
                        }

                        try {
                            JSONObject object = new JSONObject(response.body().string());
                            JSONObject data = object.getJSONObject("data");
                            User user = new User();
                            user.image = AppConfig.URL + data.getString("image");
                            user.userId = data.getString("user_id");
                            user.email = data.getString("email");
                            user.userName = data.getString("user_name");
                            userAdapter.setAvatarUrl(user.image);
                            UserPrefs.setUser(user);
                            LOGGER.info(user);
                        } catch (JSONException | IOException e) {
                            e.printStackTrace();
                            LOGGER.error(e);
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        LOGGER.error(t);
                    }
                });
    }
}
