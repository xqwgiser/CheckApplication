package com.example.xqw.checkinapplication.main.rest.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xqw.checkinapplication.R;
import com.example.xqw.checkinapplication.base.PresenterLoader;
import com.example.xqw.checkinapplication.main.rest.presenter.IRestEditPresenter;
import com.example.xqw.checkinapplication.main.rest.presenter.RestEditPresenter;
import com.example.xqw.checkinapplication.utils.SharedPreferencesUtils;
import com.example.xqw.checkinapplication.utils.Utils;
import com.rey.material.app.DatePickerDialog;
import com.rey.material.app.Dialog;
import com.rey.material.app.DialogFragment;
import com.rey.material.widget.Button;
import com.rey.material.widget.Spinner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by xqw on 2016/4/25.
 */
public class RestEditFragment extends Fragment implements IRestEditView, LoaderManager.LoaderCallbacks<IRestEditPresenter> {
    @Bind(R.id.rest_type)
    LinearLayout restType;
    @Bind(R.id.rest_time)
    LinearLayout restTime;
    @Bind(R.id.rest_edit_commit)
    Button restEditCommit;
    @Bind(R.id.spinner_type)
    Spinner spinnerType;
    private static final int LOADER_ID = 109;
    @Bind(R.id.rest_days)
    TextView restDays;
    @Bind(R.id.rest_start_time)
    LinearLayout restStartTime;
    @Bind(R.id.rest_end_time)
    LinearLayout restEndTime;
    @Bind(R.id.start_time)
    TextView startTime;
    @Bind(R.id.end_time)
    TextView endTime;
    @Bind(R.id.rest_reason)
    EditText restReason;
    private IRestEditPresenter presenter;
    DialogFragment fragment;
    long start=Utils.getTimeMillis();
    long end=Utils.getTimeMillis();

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rest_edit, container, false);
        ButterKnife.bind(this, view);
        startTime.setText(Utils.getYearMonth());
        endTime.setText(Utils.getYearMonth());
        restDays.setText("0天");
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
        presenter.setRestInfo(getActivity().getIntent());
        presenter.onViewAttached();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.rest_start_time, R.id.rest_end_time, R.id.rest_edit_commit})
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.rest_start_time:
                Dialog.Builder builder = new DatePickerDialog.Builder() {
                    @Override
                    public void onPositiveActionClicked(DialogFragment fragment) {
                        DatePickerDialog dialog = (DatePickerDialog) fragment.getDialog();
                        String time = String.valueOf(dialog.getYear()) + "-" + String.valueOf(dialog.getMonth()
                                + 1) + "-" + String.valueOf(dialog.getDay());
                        startTime.setText(time);
                        start = dialog.getDate();
                        String days = getDate() + "天";
                        restDays.setText(days);
                        super.onPositiveActionClicked(fragment);
                    }

                    @Override
                    public void onNegativeActionClicked(DialogFragment fragment) {
                        super.onNegativeActionClicked(fragment);
                    }
                };
                builder.positiveAction("确定").negativeAction("取消");
                fragment = DialogFragment.newInstance(builder);
                fragment.show(getFragmentManager(), null);
                break;
            case R.id.rest_end_time:
                builder = new DatePickerDialog.Builder() {
                    @Override
                    public void onPositiveActionClicked(DialogFragment fragment) {
                        DatePickerDialog dialog = (DatePickerDialog) fragment.getDialog();
                        String time = String.valueOf(dialog.getYear()) + "-" + String.valueOf(dialog.getMonth()
                                + 1) + "-" + String.valueOf(dialog.getDay());
                        endTime.setText(time);
                        end = dialog.getDate();
                        String days = getDate() + "天";
                        restDays.setText(days);
                        super.onPositiveActionClicked(fragment);
                    }

                    @Override
                    public void onNegativeActionClicked(DialogFragment fragment) {
                        super.onNegativeActionClicked(fragment);
                    }
                };
                builder.positiveAction("确定").negativeAction("取消");
                fragment = DialogFragment.newInstance(builder);
                fragment.show(getFragmentManager(), null);
                break;
            case R.id.rest_edit_commit:
                presenter.postRestInfo(getActivity().getIntent());
                getActivity().finish();
                break;
        }
    }

    @Override
    public Loader<IRestEditPresenter> onCreateLoader(int id, Bundle args) {
        return new PresenterLoader<>(getContext(), new RestEditPresenter(this));
    }

    @Override
    public void onLoadFinished(Loader<IRestEditPresenter> loader, IRestEditPresenter data) {
        presenter = data;
    }

    @Override
    public void onLoaderReset(Loader<IRestEditPresenter> loader) {
        presenter = null;
    }

    @Override
    public void setRestType(List<Map> list) {
        List<String> items = new ArrayList<>();
        for (Map map : list) {
            items.add(map.get("qjlxmc").toString());
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getActivity(), R.layout.row_spn, items);
        arrayAdapter.setDropDownViewResource(R.layout.row_spn_dropdown);
        spinnerType.setAdapter(arrayAdapter);
    }

    @Override
    public Map getRestInfo() {
        Map<String, String> result = new HashMap<>();
        result.put("userid", SharedPreferencesUtils.readString("userId"));
        result.put("qjkssj", startTime.getText().toString());
        result.put("qjjssj", endTime.getText().toString());
        result.put("qjts", restDays.getText().toString());
        result.put("qjlx", spinnerType.getSelectedItem().toString());
        result.put("qjsy",restReason.getText().toString());
        result.put("sqsj",Utils.getYearMonth());
        result.put("status","待审批");
        result.put("guid",getActivity().getIntent().getStringExtra("guid"));
        return result;
    }

    @Override
    public void setRestInfo(Intent intent) {
        startTime.setText(intent.getStringExtra("qjkssj"));
        endTime.setText(intent.getStringExtra("qjjssj"));
        restDays.setText(intent.getStringExtra("qjts"));
        restReason.setText(intent.getStringExtra("qjsy"));
    }

    @Override
    public void showResult(String s) {
        if(s!=null){
            Toast.makeText(getActivity(),"成功",Toast.LENGTH_SHORT).show();
        }
    }

    @NonNull
    private String getDate() {
        return String.valueOf((end - start) / 24 / 3600000);
    }
}
