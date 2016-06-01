package com.example.xqw.checkinapplication.main.log.view;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.xqw.checkinapplication.R;
import com.zhy.base.adapter.ViewHolder;
import com.zhy.base.adapter.recyclerview.CommonAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by xqw on 2016/5/5.
 */
public class ApprovalLogFragment extends Fragment {
    @Bind(R.id.list_log)
    RecyclerView listLog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_log, container, false);
        ButterKnife.bind(this, view);
        listLog.setAdapter(new CommonAdapter<Map>(getActivity(),R.layout.card_view_log_check,getSomeData()) {
            @Override
            public void convert(ViewHolder holder, Map map) {
                TextView logState=holder.getView(R.id.log_state);
                final String[] colors = getContext().getResources().getStringArray(R.array.default_preview);
                holder.setText(R.id.log_content,map.get("logContent").toString());
                holder.setText(R.id.log_state,map.get("logState").toString());
                holder.setText(R.id.log_time,map.get("logTime").toString());
                holder.setText(R.id.log_user,map.get("logUser").toString());
                switch (map.get("logState").toString()){
                    case "待审批":
                        logState.setTextColor(Color.parseColor(colors[1]));
                        break;
                    case "不合格":
                        logState.setTextColor(Color.parseColor(colors[0]));
                        break;
                    case "已审批":
                        logState.setTextColor(Color.parseColor(colors[4]));
                        break;
                    default:
                        break;
                }
            }
        });
        listLog.setLayoutManager(new LinearLayoutManager(getContext()));
        listLog.setItemAnimator(new DefaultItemAnimator());
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
    private List<Map> getSomeData(){
        List<Map> resultList=new ArrayList<>();
        Map<String, String> temp = new HashMap<>();
        temp.put("logTime", "3月31日 工作日志");
        temp.put("logContent", "工作内容：入户走访");
        temp.put("logUser","填写人:民警A");
        temp.put("logState", "待审批");
        resultList.add(temp);
        HashMap<String, String> temp1 = new HashMap<>();
        temp1.put("logTime", "3月30日 工作日志");
        temp1.put("logContent", " 工作内容：车辆排查");
        temp1.put("logUser","填写人:民警B");
        temp1.put("logState", "不合格");
        resultList.add(temp1);
        HashMap<String, String> temp2 = new HashMap<>();
        temp2.put("logTime", "3月29日 工作日志");
        temp2.put("logContent", " 工作内容：工作例会");
        temp2.put("logUser","填写人:民警C");
        temp2.put("logState", "已审批");
        resultList.add(temp2);
        HashMap<String, String> temp3 = new HashMap<>();
        temp3.put("logTime", "3月31日 工作日志");
        temp3.put("logContent", "工作内容：入户走访");
        temp3.put("logUser","填写人:民警C");
        temp3.put("logState", "待审批");
        resultList.add(temp3);
        HashMap<String, String> temp4 = new HashMap<>();
        temp4.put("logTime", "3月31日 工作日志");
        temp4.put("logContent", "工作内容：入户走访");
        temp4.put("logUser","填写人:民警D");
        temp4.put("logState", "待审批");
        resultList.add(temp4);
        return resultList;
    }
}
