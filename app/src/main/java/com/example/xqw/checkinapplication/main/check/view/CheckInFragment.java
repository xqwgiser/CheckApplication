package com.example.xqw.checkinapplication.main.check.view;

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
import android.widget.TextView;
import android.widget.Toast;

import com.example.xqw.checkinapplication.R;
import com.example.xqw.checkinapplication.base.Common;
import com.example.xqw.checkinapplication.base.PresenterLoader;
import com.example.xqw.checkinapplication.bean.ItemEntity;
import com.example.xqw.checkinapplication.main.check.presenter.CheckPresenter;
import com.example.xqw.checkinapplication.main.check.presenter.ICheckPresenter;
import com.example.xqw.checkinapplication.main.view.ContainerActivity;
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
public class CheckInFragment extends Fragment implements LoaderManager.LoaderCallbacks<ICheckPresenter>,ICheckView {
    @Bind(R.id.checkIn_btn)
    TextView checkInBtn;
    @Bind(R.id.checkOut_btn)
    TextView checkOutBtn;
    @Bind(R.id.checkInList)
    RecyclerView checkInList;
    private static final int LOADER_ID = 102;
    private ICheckPresenter presenter;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(LOADER_ID,null,this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.onViewAttached();
        presenter.showCheckList();

    }

    @Override
    public void onStop() {
        super.onStop();
        presenter.onViewDetached();
    }


    @OnClick({R.id.checkIn_btn, R.id.checkOut_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.checkIn_btn:
                Intent intent = new Intent(getActivity(), ContainerActivity.class);
                intent.putExtra("tag", Common.CHECK_IN_EDIT);
                startActivity(intent);
                break;
            case R.id.checkOut_btn:
                Intent intentCheckOut=new Intent(getActivity(),ContainerActivity.class);
                intentCheckOut.putExtra("tag",Common.CHECK_OUT_EDIT);
                startActivity(intentCheckOut);
                break;
        }
    }


    @Override
    public Loader<ICheckPresenter> onCreateLoader(int id, Bundle args) {
        return new PresenterLoader<>(this.getContext(), new CheckPresenter(this));
    }

    @Override
    public void onLoadFinished(Loader<ICheckPresenter> loader, ICheckPresenter data) {
        this.presenter=data;
    }

    @Override
    public void onLoaderReset(Loader<ICheckPresenter> loader) {
        presenter=null;

    }

    @Override
    public void showCheckList(List<Map> list) {
        checkInList.setAdapter(new CommonAdapter<Map>(getActivity(),R.layout.card_view_check_in,list) {
            @Override
            public void convert(final ViewHolder holder, final Map m) {
                holder.setText(R.id.check_time,"签到时间:"+m.get("qdsj"));
                holder.setText(R.id.check_place,"签到地点:"+m.get("qdwz"));
                holder.setText(R.id.check_out_time,"签退时间:"+m.get("qtsj"));
                holder.setText(R.id.check_out_place,"签退地点:"+m.get("qtwz"));
                holder.setText(R.id.check_time_long,"工作时长:"+m.get("gzsc"));
                final PopupMenu popupMenu=new PopupMenu(getActivity(),holder.getView(R.id.more_btn));
                holder.getView(R.id.more_btn).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupMenu.show();
                    }
                });
                Menu menu=popupMenu.getMenu();
                MenuInflater menuInflater = getActivity().getMenuInflater();
                menuInflater.inflate(R.menu.check_menu, menu);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.check_edit:
                                Intent checkEdit=new Intent(getActivity(),ContainerActivity.class);
                                checkEdit.putExtra("tag",Common.CHECK_UPDATE);
                                Map checkInfo=saveCheckInfo(holder);
                                checkEdit.putExtra("qdsj",checkInfo.get("qdsj").toString());
                                checkEdit.putExtra("qdwz",checkInfo.get("qdwz").toString());
                                checkEdit.putExtra("qtsj",checkInfo.get("qtsj").toString());
                                checkEdit.putExtra("qtwz",checkInfo.get("qtwz").toString());
                                checkEdit.putExtra("gzsc",checkInfo.get("gzsc").toString());
                                checkEdit.putExtra("guid",m.get("guid").toString());
                                startActivity(checkEdit);

                                break;
                            case R.id.check_delete:
                                Map<String,String> deleteInfo=saveCheckInfo(holder);
                                deleteInfo.put("qdlx",m.get("qdlx").toString());
                                deleteInfo.put("guid",m.get("guid").toString());
                                deleteInfo.put("userid",m.get("userid").toString());
                                presenter.deleteCheckInfo(deleteInfo);
                                break;
                            default:
                                break;
                        }
                        return false;
                    }
                });
            }
        });
        checkInList.setLayoutManager(new LinearLayoutManager(getContext()));
        checkInList.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    public void changeButton(boolean isCheck) {
        if(isCheck){
            checkOutBtn.setTextColor(getContext().getResources().getColor(R.color.colorDivider));
            checkOutBtn.setEnabled(false);
            checkInBtn.setEnabled(true);
            checkInBtn.setText("签到");
            checkInBtn.setTextColor(getContext().getResources().getColor(R.color.colorAccent));
        }else {
            checkInBtn.setText("已签到");
            checkInBtn.setTextColor(getContext().getResources().getColor(R.color.colorDivider));
            checkInBtn.setEnabled(false);
            checkOutBtn.setEnabled(true);
            checkOutBtn.setTextColor(getContext().getResources().getColor(R.color.colorAccent));
        }
    }

    @Override
    public Map<String,String> saveCheckInfo(ViewHolder viewHolder) {
        Map<String,String> result=new HashMap<>();
        TextView time=viewHolder.getView(R.id.check_time);
        String temp=time.getText().toString();
        String[]temps= temp.split(" ");
        result.put("qdsj",temps[1]);
        TextView place=viewHolder.getView(R.id.check_place);
        String[] places=place.getText().toString().split(":");
        result.put("qdwz",places[1]);
        TextView outTime=viewHolder.getView(R.id.check_out_time);
        String[]checkOutTimes=outTime.getText().toString().split(" ");
        result.put("qtsj",checkOutTimes[1]);
        TextView outPlace=viewHolder.getView(R.id.check_out_place);
        String[] checkOutPlace=outPlace.getText().toString().split(":");
        result.put("qtwz",checkOutPlace[1]);
        TextView timeLong=viewHolder.getView(R.id.check_time_long);
        String[] timeLongs=timeLong.getText().toString().split(":");
        result.put("gzsc",timeLongs[1]);
        return result;
    }

    @Override
    public void showDeletedInfo(ItemEntity<String> itemEntity) {
        if (itemEntity.getRows()!=null){
            presenter.showCheckList();
            Toast.makeText(getActivity(),"删除成功",Toast.LENGTH_SHORT).show();
        }
    }

}
