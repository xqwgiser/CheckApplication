package com.example.xqw.checkinapplication.main.log.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.xqw.checkinapplication.R;
import com.example.xqw.checkinapplication.base.Common;
import com.example.xqw.checkinapplication.base.PresenterLoader;
import com.example.xqw.checkinapplication.main.log.presenter.ILogPresenter;
import com.example.xqw.checkinapplication.main.log.presenter.LogPresenter;
import com.example.xqw.checkinapplication.main.view.ContainerActivity;
import com.rey.material.widget.TextView;
import com.zhy.base.adapter.ViewHolder;
import com.zhy.base.adapter.recyclerview.CommonAdapter;

import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by xqw on 2016/4/18.
 */
public class LogFragment extends Fragment implements LoaderManager.LoaderCallbacks<ILogPresenter>, ILogView {
    @Bind(R.id.recycleView_log)
    RecyclerView recycleViewLog;
    private static final int LOADER_ID = 103;
    @Bind(R.id.log_input_btn)
    TextView logInputBtn;
    @Bind(R.id.container)
    RelativeLayout container;
    private ILogPresenter logPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_log, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(LOADER_ID, null, this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        logPresenter.onViewAttached();
    }

    @Override
    public void onStop() {
        super.onStop();
        logPresenter.onViewDetached();
    }


    @Override
    public Loader<ILogPresenter> onCreateLoader(int id, Bundle args) {
        return new PresenterLoader<>(this.getContext(), new LogPresenter(this));
    }

    @Override
    public void onLoadFinished(Loader<ILogPresenter> loader, ILogPresenter data) {
        this.logPresenter = data;
    }

    @Override
    public void onLoaderReset(Loader<ILogPresenter> loader) {
        logPresenter = null;
    }

    @OnClick(R.id.log_input_btn)
    public void onClick() {
        Intent intent = new Intent(getActivity(), ContainerActivity.class);
        intent.putExtra("tag", Common.LOG_EDIT);
        intent.putExtra("isAdd", true);
        startActivity(intent);
    }

    @Override
    public void showLogList(List<Map> list) {
        recycleViewLog.setAdapter(new CommonAdapter<Map>(getActivity(), R.layout.card_view_common, list) {
            @Override
            public void convert(ViewHolder holder, final Map map) {
                holder.setText(R.id.text1, map.get("txsj").toString());
                holder.setText(R.id.text2, "工作内容:" + map.get("gznr").toString());
                final PopupMenu popupMenu = new PopupMenu(getActivity(), holder.getView(R.id.more_btn));
                holder.getView(R.id.more_btn).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupMenu.show();
                    }
                });
                Menu menu = popupMenu.getMenu();
                MenuInflater menuInflater = getActivity().getMenuInflater();
                menuInflater.inflate(R.menu.check_menu, menu);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.check_edit:
                                Intent intent = new Intent(getActivity(), ContainerActivity.class);
                                intent.putExtra("tag", Common.LOG_EDIT);
                                intent.putExtra("txsj", map.get("txsj").toString());
                                intent.putExtra("gznr", map.get("gznr").toString());
                                intent.putExtra("guid", map.get("guid").toString());
                                intent.putExtra("isAdd", false);
                                startActivity(intent);
                                break;
                            case R.id.check_delete:
                                logPresenter.deleteLog(map);
                                break;
                            default:
                                break;
                        }
                        return false;
                    }
                });
            }
        });
        recycleViewLog.setItemAnimator(new DefaultItemAnimator());
        recycleViewLog.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void showDeleteResult(String info) {
        if (info != null) {
            logPresenter.onViewAttached();
            Toast.makeText(getActivity(),"删除成功",Toast.LENGTH_SHORT).show();
        }
    }
}
