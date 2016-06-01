package com.example.xqw.checkinapplication.main.notice.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.xqw.checkinapplication.R;
import com.example.xqw.checkinapplication.base.Common;
import com.example.xqw.checkinapplication.base.PresenterLoader;
import com.example.xqw.checkinapplication.main.notice.presenter.INoticePresenter;
import com.example.xqw.checkinapplication.main.notice.presenter.NoticePresenter;
import com.zhy.base.adapter.ViewHolder;
import com.zhy.base.adapter.recyclerview.CommonAdapter;

import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by xqw on 2016/4/18.
 */
public class NoticeFragment extends Fragment implements INoticeView,LoaderManager.LoaderCallbacks<INoticePresenter>{


    @Bind(R.id.recycleView_notice)
    RecyclerView recycleViewNotice;
    private INoticePresenter presenter;
    private static final int LOADER_ID = 111;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notice, container, false);
        ButterKnife.bind(this, view);
        recycleViewNotice.setItemAnimator(new DefaultItemAnimator());
        recycleViewNotice.setLayoutManager(new LinearLayoutManager(getContext()));
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(LOADER_ID,null,this);
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.onViewAttached();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


    @Override
    public Loader<INoticePresenter> onCreateLoader(int id, Bundle args) {
        return new PresenterLoader<>(getContext(),new NoticePresenter(this));
    }

    @Override
    public void onLoadFinished(Loader<INoticePresenter> loader, INoticePresenter data) {
        presenter=data;
    }

    @Override
    public void onLoaderReset(Loader<INoticePresenter> loader) {
        presenter=null;
    }

    @Override
    public void showNoticeList(List<Map> list) {

        recycleViewNotice.setAdapter(new CommonAdapter<Map>(getContext(),R.layout.list_view_work_state,list) {
            @Override
            public void convert(ViewHolder holder, final Map map) {
                holder.setText(R.id.notice_title,map.get("xxlx").toString());
                holder.setText(R.id.workState_time,map.get("fbsj").toString());
                holder.setText(R.id.workState_title,map.get("xxbt").toString());
                holder.setText(R.id.workState_isRead,"未读");
                holder.setTextColor(R.id.workState_isRead,R.color.colorNotRead);
                holder.getView(R.id.card_ripple).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent();
                        intent.putExtra("tag", Common.NOTICE_INFO);
                        intent.putExtra("xxbt",map.get("xxbt").toString());
                        intent.putExtra("xxnr",map.get("xxnr").toString());
                        startActivity(intent);
                    }
                });
            }

        });
    }
}
