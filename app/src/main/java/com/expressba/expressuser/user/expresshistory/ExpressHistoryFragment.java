package com.expressba.expressuser.user.expresshistory;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;

import com.expressba.expressuser.main.UIFragment;
import com.expressba.expressuser.model.ExpressInfo;
import com.expressba.expressuser.R;
import com.expressba.expressuser.myelement.MyFragmentManager;

/**
 * Created by songchao on 16/5/8.
 */
public class ExpressHistoryFragment extends UIFragment implements ExpressHistoryView,View.OnClickListener {
    private ListView historyList;
    private ExpressHistoryPresenter presenter;
    private int sendOrReceive;
    private ArrayList<ExpressInfo> expressInfos;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_express_history,container,false);
        view.findViewById(R.id.top_bar_left_img).setOnClickListener(this);
        historyList = (ListView) view.findViewById(R.id.express_history_list);
        init(view);
        presenter = new ExpressHistoryPresenterImpl(getActivity(),this,sendOrReceive);
        startGetData();//开始获取后台数据
        return view;
    }

    @Override
    protected void onBack() {
        MyFragmentManager.popFragment(getClass(),null,null,getFragmentManager());
    }

    private void init(View view){
        Bundle bundle = getArguments();
        sendOrReceive = bundle.getInt("sendorreceive");
        if(sendOrReceive == ExpressHistoryPresenterImpl.HISTORY_SEND){
            ((TextView)view.findViewById(R.id.top_bar_center_text)).setText("发件记录");
        }else {
            ((TextView)view.findViewById(R.id.top_bar_center_text)).setText("收件记录");
        }
        historyList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //跳转到此快递详情页面
                ExpressInfo expressInfo = expressInfos.get(position);
                Bundle bundle = ExpressHistoryDetailFragment.newInstanceBundle(expressInfo,sendOrReceive);
                MyFragmentManager.turnReplaceFragment(ExpressHistoryFragment.class,ExpressHistoryDetailFragment.class,bundle,getFragmentManager(),true);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.top_bar_left_img:
                onBack();
                break;
        }
    }

    @Override
    public void startGetData() {
        presenter.onGetData();
    }

    @Override
    public void onSuccess(ArrayList<ExpressInfo> expressInfos) {
        this.expressInfos = expressInfos;
        ExpressHistoryListAdapter adapter = new ExpressHistoryListAdapter(getActivity(),expressInfos);
        historyList.setAdapter(adapter);
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(getActivity(),message,Toast.LENGTH_SHORT).show();
    }
}
