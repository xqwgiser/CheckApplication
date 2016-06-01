package com.example.xqw.checkinapplication.main.check.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.xqw.checkinapplication.R;
import com.example.xqw.checkinapplication.base.PresenterLoader;
import com.example.xqw.checkinapplication.main.check.presenter.CheckEditPresenter;
import com.example.xqw.checkinapplication.main.check.presenter.ICheckEditPresenter;
import com.example.xqw.checkinapplication.utils.Utils;
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
 * Created by xqw on 2016/4/22.
 */
public class CheckInEditFragment extends Fragment implements ICheckEditView, LoaderManager.LoaderCallbacks<ICheckEditPresenter>{
    @Bind(R.id.check_commit)
    Button checkCommit;
    @Bind(R.id.check_type)
    Spinner checkType;
    @Bind(R.id.is_delay)
    Spinner isDelay;
    private static final int LOADER_ID = 104;
    @Bind(R.id.check_in_time)
    TextView checkInTime;
    @Bind(R.id.check_in_place)
    TextView checkInPlace;
    private ICheckEditPresenter presenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_check_in, container, false);
        ButterKnife.bind(this, view);
        checkInTime.setText(Utils.getHourMin());
        String[] delayItems = new String[]{"否", "是"};
        ArrayAdapter<String> delayAdapter = new ArrayAdapter<>(getActivity(), R.layout.row_spn, delayItems);
        delayAdapter.setDropDownViewResource(R.layout.row_spn_dropdown);
        isDelay.setAdapter(delayAdapter);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.onViewAttached();
        presenter.setCheckType();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(LOADER_ID, null, this);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.onDestroyed();
        ButterKnife.unbind(this);
    }


    @OnClick(R.id.check_commit)
    public void onClick() {
        presenter.postCheckInfo();
        getActivity().finish();
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
    public Map getCheckInfo() {
        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("qdsj", Utils.getSystemTime());
        resultMap.put("qdwz", "113.25,23.33");
        if (isDelay.getSelectedItem().equals("是")) {
            resultMap.put("qdlx", "加班");
        } else {
            resultMap.put("qdlx", "不加班");
        }
        return resultMap;
    }

    @Override
    public Loader<ICheckEditPresenter> onCreateLoader(int id, Bundle args) {
        return new PresenterLoader<>(getContext(), new CheckEditPresenter(this));
    }

    @Override
    public void onLoadFinished(Loader<ICheckEditPresenter> loader, ICheckEditPresenter data) {
        this.presenter = data;
    }

    @Override
    public void onLoaderReset(Loader<ICheckEditPresenter> loader) {
        presenter = null;
    }
}
