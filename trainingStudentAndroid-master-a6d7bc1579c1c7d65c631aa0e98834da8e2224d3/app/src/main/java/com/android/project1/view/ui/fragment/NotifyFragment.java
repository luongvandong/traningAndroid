package com.android.project1.view.ui.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.project1.R;
import com.android.project1.model.pojo.Notify;
import com.android.project1.view.ui.adapter.DbAdapter;
import com.android.project1.view.ui.adapter.NotifyAdapter;
import com.android.project1.view.ui.prefs.Preferences;
import com.android.project1.view.ui.service.NotifyService;
import com.android.project1.view.ui.widget.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NotifyFragment extends Fragment {

    public static final String ARG_PARAM1 = "param1";

    @BindView(R.id.rcvNoti)
    RecyclerView rcvNoti;

    NotifyAdapter adapter;
    List<Notify> list;

    private DbAdapter helper;
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.i("receiver", "test");
            list = helper.getAllNotification();
            adapter.setList(list);
        }
    };

    public NotifyFragment() {
    }

    public static NotifyFragment newInstance(String param) {
        NotifyFragment fragment = new NotifyFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_notification, container, false);
        ButterKnife.bind(this, v);

        RecyclerView.LayoutManager manager = new LinearLayoutManager(getContext());
        rcvNoti.setHasFixedSize(true);
        rcvNoti.setLayoutManager(manager);
        rcvNoti.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        rcvNoti.setItemAnimator(new DefaultItemAnimator());
        helper = new DbAdapter(getContext());
        adapter = new NotifyAdapter(getContext(), list);
        rcvNoti.setAdapter(adapter);
        loadData();

        adapter.setListener(new NotifyAdapter.LongClickListener() {
            @Override
            public void onLongClick(View v, int position) {
                helper.deleteNotification(list.get(position).getId());
                list.remove(position);
                adapter.notifyDataSetChanged();
            }
        });
        startService();
        return v;
    }

    private void loadData() {
        list = new ArrayList<>();
        list = helper.getAllNotification();
        adapter.setList(list);
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().registerReceiver(receiver, new IntentFilter(NotifyService.ACTION_SHOW));
    }

    @Override
    public void onPause() {
        super.onPause();
        getActivity().unregisterReceiver(receiver);
    }

    private void startService() {
        if (!Preferences.isAlarmOn()) {
            NotifyService.setServiceAlarm(getContext(), true);
        }
    }
}
