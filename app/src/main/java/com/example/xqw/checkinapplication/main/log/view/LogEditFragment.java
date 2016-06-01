package com.example.xqw.checkinapplication.main.log.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xqw.checkinapplication.R;
import com.example.xqw.checkinapplication.base.PresenterLoader;
import com.example.xqw.checkinapplication.bean.ItemEntity;
import com.example.xqw.checkinapplication.main.log.presenter.ILogEditPresenter;
import com.example.xqw.checkinapplication.main.log.presenter.LogEditPresenter;
import com.example.xqw.checkinapplication.utils.SharedPreferencesUtils;
import com.example.xqw.checkinapplication.utils.Utils;
import com.rey.material.widget.Button;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by xqw on 2016/4/22.
 */
public class LogEditFragment extends Fragment implements ILogEditView, LoaderManager.LoaderCallbacks<ILogEditPresenter> {
    @Bind(R.id.edit_time_content)
    TextView editTimeContent;
    @Bind(R.id.edit_work_content)
    TextView editWorkContent;
    @Bind(R.id.commit_btn)
    Button commitBtn;
    private static final int LOADER_ID = 106;
    @Bind(R.id.work_content)
    TextInputEditText workContent;
    private ILogEditPresenter presenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_log_edit, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(LOADER_ID, null, this);
    }

    @Override
    public void onResume() {
        super.onResume();
        editTimeContent.setText(Utils.getHourMin());
        presenter.setLogInfo(getActivity().getIntent());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick(R.id.commit_btn)
    public void onClick() {
        presenter.postLogInfo(getActivity().getIntent());
        getActivity().finish();
    }

    @Override
    public Loader<ILogEditPresenter> onCreateLoader(int id, Bundle args) {
        return new PresenterLoader<>(getContext(), new LogEditPresenter(this));
    }

    @Override
    public void onLoadFinished(Loader<ILogEditPresenter> loader, ILogEditPresenter data) {
        this.presenter = data;
    }

    @Override
    public void onLoaderReset(Loader<ILogEditPresenter> loader) {
        this.presenter = null;
    }

    @Override
    public Map getLogInfo() {
        Map<String, String> result = new HashMap<>();
        result.put("txsj", Utils.getSystemTime());
        result.put("userid", SharedPreferencesUtils.readString("userId"));
        result.put("gznr",workContent.getText().toString());
        result.put("guid",getActivity().getIntent().getStringExtra("guid"));
        return result;
    }

    @Override
    public void showResult(ItemEntity<String> itemEntity) {
        if(itemEntity.getRows()!=null){
            Toast.makeText(getActivity(),"添加成功",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void setLogInfo(Intent intent) {
        editTimeContent.setText(intent.getStringExtra("txsj"));
        workContent.setText(intent.getStringExtra("gznr"));
    }
}
