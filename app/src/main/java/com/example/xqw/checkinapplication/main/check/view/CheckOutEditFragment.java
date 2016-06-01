package com.example.xqw.checkinapplication.main.check.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xqw.checkinapplication.R;
import com.example.xqw.checkinapplication.base.PresenterLoader;
import com.example.xqw.checkinapplication.bean.ItemEntity;
import com.example.xqw.checkinapplication.main.check.presenter.CheckOutPresenter;
import com.example.xqw.checkinapplication.main.check.presenter.ICheckOutPresenter;
import com.example.xqw.checkinapplication.utils.Utils;
import com.example.xqw.checkinapplication.utils.SharedPreferencesUtils;
import com.rey.material.widget.Button;
import com.rey.material.widget.Spinner;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by xqw on 2016/4/27.
 */
public class CheckOutEditFragment extends Fragment implements ICheckOutView, LoaderManager.LoaderCallbacks<ICheckOutPresenter> {
    @Bind(R.id.is_delay)
    Spinner isDelay;
    @Bind(R.id.check_commit)
    Button checkCommit;
    @Bind(R.id.check_out_time)
    TextView checkOutTime;
    @Bind(R.id.work_time_long)
    TextView workTimeLong;
    private ICheckOutPresenter checkOutPresenter;
    private static final int LOADER_ID = 105;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_check_out, container, false);
        ButterKnife.bind(this, view);
        String[] delayItems = new String[]{"否", "是"};
        ArrayAdapter<String> delayAdapter = new ArrayAdapter<String>(getActivity(), R.layout.row_spn, delayItems);
        delayAdapter.setDropDownViewResource(R.layout.row_spn_dropdown);
        isDelay.setAdapter(delayAdapter);
        isDelay.setEnabled(false);
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
        checkOutPresenter.onViewAttached();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick(R.id.check_commit)
    public void onClick() {
        checkOutPresenter.postCheckInfo();
        getActivity().finish();
    }


    @Override
    public Loader<ICheckOutPresenter> onCreateLoader(int id, Bundle args) {
        return new PresenterLoader<>(getContext(), new CheckOutPresenter(this));
    }

    @Override
    public void onLoadFinished(Loader<ICheckOutPresenter> loader, ICheckOutPresenter data) {
        this.checkOutPresenter = data;
    }

    @Override
    public void onLoaderReset(Loader<ICheckOutPresenter> loader) {
        checkOutPresenter = null;
    }

    @Override
    public void setCheckInfo() {
        checkOutTime.setText(Utils.getHourMin());
        if (SharedPreferencesUtils.readString("qdlx").equals("加班")) {
            isDelay.setSelection(1);
        } else {
            isDelay.setSelection(0);
        }
        workTimeLong.setText(SharedPreferencesUtils.readString("gzsc"));
    }

    @Override
    public void showResult(ItemEntity<String> itemEntity) {
        Log.e("返回结果", itemEntity.getRows());
        if (itemEntity.getRows()!=null){
            Toast.makeText(getContext(),"提交成功",Toast.LENGTH_SHORT).show();
        }
    }
}
