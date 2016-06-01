package com.example.xqw.checkinapplication.main.check.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xqw.checkinapplication.R;
import com.example.xqw.checkinapplication.base.PresenterLoader;
import com.example.xqw.checkinapplication.bean.ItemEntity;
import com.example.xqw.checkinapplication.main.check.presenter.CheckUpdatePresenter;
import com.example.xqw.checkinapplication.main.check.presenter.ICheckUpdatePresenter;
import com.example.xqw.checkinapplication.utils.Utils;
import com.example.xqw.checkinapplication.utils.SharedPreferencesUtils;
import com.rey.material.app.Dialog;
import com.rey.material.app.DialogFragment;
import com.rey.material.app.TimePickerDialog;
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
 * Created by xqw on 2016/5/24.
 */
public class CheckUpdateFragment extends Fragment implements ICheckUpdateView, LoaderManager.LoaderCallbacks<ICheckUpdatePresenter> {
    @Bind(R.id.check_in_time)
    TextView checkInTime;
    @Bind(R.id.check_in_place)
    TextView checkInPlace;
    @Bind(R.id.check_type)
    Spinner checkType;
    @Bind(R.id.is_delay)
    Spinner isDelay;
    @Bind(R.id.check_out_time)
    TextView checkOutTime;
    @Bind(R.id.work_time_long)
    TextView workTimeLong;
    private static final int LOADER_ID = 103;
    @Bind(R.id.check_out_place)
    TextView checkOutPlace;
    @Bind(R.id.rest_type_1)
    LinearLayout restType1;
    @Bind(R.id.check_commit)
    Button checkCommit;
    private ICheckUpdatePresenter presenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_check_update, container, false);
        ButterKnife.bind(this, view);
        String[] delayItems = new String[]{"否", "是"};
        ArrayAdapter<String> delayAdapter = new ArrayAdapter<>(getActivity(), R.layout.row_spn, delayItems);
        delayAdapter.setDropDownViewResource(R.layout.row_spn_dropdown);
        isDelay.setAdapter(delayAdapter);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(LOADER_ID, null, this);
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.setCheckType();
        presenter.setCheckInfo(getActivity().getIntent());
    }

    @Override
    public void setCheckType(List<Map> list) {
        List<String> checkTypes = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            checkTypes.add(list.get(i).get("gzqdlxmc").toString());
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getActivity(), R.layout.row_spn, checkTypes);
        arrayAdapter.setDropDownViewResource(R.layout.row_spn_dropdown);
        checkType.setAdapter(arrayAdapter);
    }

    @Override
    public void setCheckInfo(Intent intent) {
        checkInTime.setText(intent.getStringExtra("qdsj"));
        checkInPlace.setText(intent.getStringExtra("qdwz"));
        checkOutTime.setText(intent.getStringExtra("qtsj"));
        checkOutPlace.setText(intent.getStringExtra("qtwz"));
        workTimeLong.setText(getWorkTime());
    }

    @Override
    public Map getCheckInfo(Intent intent) {
        Map<String,String> CheckInfo=new HashMap<>();
        CheckInfo.put("qdsj", Utils.getYearMonth()+checkInTime.getText().toString());
        CheckInfo.put("qdwz",checkInPlace.getText().toString());
        if (isDelay.getSelectedItem().equals("是")) {
            CheckInfo.put("qdlx", "加班");
        } else {
            CheckInfo.put("qdlx", "不加班");
        }
        CheckInfo.put("qtsj", Utils.getYearMonth()+checkOutTime.getText().toString());
        CheckInfo.put("qtwz",checkOutPlace.getText().toString());
        CheckInfo.put("gzsc",workTimeLong.getText().toString());
        CheckInfo.put("userid", SharedPreferencesUtils.readString("userId"));
        CheckInfo.put("guid",intent.getStringExtra("guid"));
        return CheckInfo;
    }

    @Override
    public void showUpdateResult(ItemEntity<String> itemEntity) {
        if (itemEntity.getRows()!=null){
            Toast.makeText(getActivity(),"更新成功",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public Loader<ICheckUpdatePresenter> onCreateLoader(int id, Bundle args) {
        return new PresenterLoader<>(getContext(), new CheckUpdatePresenter(this));
    }

    @Override
    public void onLoadFinished(Loader<ICheckUpdatePresenter> loader, ICheckUpdatePresenter data) {
        this.presenter = data;
    }

    @Override
    public void onLoaderReset(Loader<ICheckUpdatePresenter> loader) {
        presenter = null;
    }

    @OnClick(R.id.rest_type_1)
    public void onClick() {
        Dialog.Builder builder=new TimePickerDialog.Builder(){
            @Override
            public void onPositiveActionClicked(DialogFragment fragment) {
                TimePickerDialog dialog = (TimePickerDialog)fragment.getDialog();
                String time=String.valueOf(dialog.getHour())+":"+String.valueOf(dialog.getMinute());
                checkOutTime.setText(time);
                super.onPositiveActionClicked(fragment);
            }

            @Override
            public void onNegativeActionClicked(DialogFragment fragment) {
                super.onNegativeActionClicked(fragment);
            }
        };
        builder.positiveAction("确定").negativeAction("取消");
        DialogFragment fragment = DialogFragment.newInstance(builder);
        fragment.show(getFragmentManager(), null);
    }

    @OnClick(R.id.check_commit)
    public void onCheckCommitClick() {
        presenter.updateCheckInfo(getActivity().getIntent());
        getActivity().finish();
    }
    private String getWorkTime(){
        String[] endString=checkOutTime.getText().toString().split(":");
        int endTime=Integer.parseInt(endString[0]);
        String[] startString=checkInTime.getText().toString().split(":");
        int startTime=Integer.parseInt(startString[0]);
        return String.valueOf(endTime-startTime);
    }
}
