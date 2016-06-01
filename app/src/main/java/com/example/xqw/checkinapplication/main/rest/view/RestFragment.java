package com.example.xqw.checkinapplication.main.rest.view;

import android.content.Intent;
import android.graphics.Color;
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
import android.widget.Toast;

import com.example.xqw.checkinapplication.R;
import com.example.xqw.checkinapplication.base.Common;
import com.example.xqw.checkinapplication.base.PresenterLoader;
import com.example.xqw.checkinapplication.main.rest.presenter.IRestPresenter;
import com.example.xqw.checkinapplication.main.rest.presenter.RestPresenter;
import com.example.xqw.checkinapplication.main.view.ContainerActivity;
import com.rey.material.widget.TextView;
import com.zhy.base.adapter.ViewHolder;
import com.zhy.base.adapter.recyclerview.CommonAdapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by xqw on 2016/4/18.
 */
public class RestFragment extends Fragment implements IRestView,LoaderManager.LoaderCallbacks<IRestPresenter>{
    @Bind(R.id.recycleView_rest)
    RecyclerView recycleViewRest;
    @Bind(R.id.rest_edit)
    TextView restEdit;
    private static final int LOADER_ID = 108;
    private IRestPresenter presenter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rest, container, false);
        ButterKnife.bind(this, view);
        recycleViewRest.setItemAnimator(new DefaultItemAnimator());
        recycleViewRest.setLayoutManager(new LinearLayoutManager(getContext()));
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


    @OnClick(R.id.rest_edit)
    public void onClick() {
        Intent intent=new Intent(getActivity(), ContainerActivity.class);
        intent.putExtra("tag", Common.REST_EDIT);
        intent.putExtra("isAdd",true);
        startActivity(intent);
    }

    @Override
    public Loader<IRestPresenter> onCreateLoader(int id, Bundle args) {
        return new PresenterLoader<>(getContext(),new RestPresenter(this));
    }

    @Override
    public void onLoadFinished(Loader<IRestPresenter> loader, IRestPresenter data) {
        presenter=data;
    }

    @Override
    public void onLoaderReset(Loader<IRestPresenter> loader) {
        presenter=null;
    }

    @Override
    public void showRestList(List<Map> list) {
        recycleViewRest.setAdapter(new CommonAdapter<Map>(getContext(),R.layout.card_view_common,list) {
            @Override
            public void convert(ViewHolder holder, final Map map) {
                android.widget.TextView restState=holder.getView(R.id.state);
                final String[] colors = getContext().getResources().getStringArray(R.array.default_preview);
                holder.setText(R.id.text1,"时间:"+map.get("qjkssj").toString()+"~"+map.get("qjjssj").toString());
                holder.setText(R.id.text2,"事由:"+map.get("qjsy").toString());
                holder.setText(R.id.text3,"请假类型:"+map.get("qjlx").toString());
                holder.setText(R.id.state,map.get("status").toString());
                switch (map.get("status").toString()){
                    case "待审批":
                        restState.setTextColor(Color.parseColor(colors[1]));
                        break;
                    case "已审批":
                        restState.setTextColor(Color.parseColor(colors[4]));
                        break;
                    default:
                        break;
                }
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
                                Intent intent=new Intent(getActivity(),ContainerActivity.class);
                                intent.putExtra("tag", Common.REST_EDIT);
                                intent.putExtra("isAdd",false);
                                intent.putExtra("guid",map.get("guid").toString());
                                intent.putExtra("qjkssj", map.get("qjkssj").toString());
                                intent.putExtra("qjjssj", map.get("qjjssj").toString());
                                intent.putExtra("qjts", map.get("qjts").toString());
                                intent.putExtra("qjlx", map.get("qjlx").toString());
                                intent.putExtra("qjsy",map.get("qjsy").toString());
                                intent.putExtra("sqsj", map.get("sqsj").toString());
                                intent.putExtra("status",map.get("status").toString());
                                startActivity(intent);
                                break;
                            case R.id.check_delete:
                                Map<String,String> id=new HashMap<>();
                                id.put("guid",map.get("guid").toString());
                                presenter.deleteRestInfo(id);
                                break;
                            default:
                                break;
                        }
                        return false;
                    }
                });
            }
        });
    }

    @Override
    public void showDeleteResult(String s) {
        if(s!=null){
            presenter.onViewAttached();
            Toast.makeText(getActivity(),"删除成功",Toast.LENGTH_SHORT).show();
        }
    }
}
