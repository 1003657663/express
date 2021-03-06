package com.expressba.expressuser.main;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.app.Activity;
import android.widget.TextView;
import android.widget.Toast;


import com.expressba.expressuser.map.MyBaiduMapFragment;
import com.expressba.expressuser.model.FromAndTo;
import com.expressba.expressuser.myelement.MyFragmentManager;
import com.expressba.expressuser.user.address.AddressReceiveFragment;
import com.expressba.expressuser.user.address.AddressSendFragment;
import com.expressba.expressuser.user.search.SearchMainFragment;
import com.expressba.expressuser.zxing.activity.CaptureActivity;

import com.expressba.expressuser.R;
import com.expressba.expressuser.user.login.LoginFragment;
import com.expressba.expressuser.user.me.MeFragment;
import com.expressba.expressuser.user.search.SearchExpressFragment;

import java.util.ArrayList;


/**
 * Created by songchao on 16/4/4.
 */
public class MainFragment extends UIFragment implements View.OnClickListener{

    private Button meButton;
    private FragmentManager fm;
    private FragmentTransaction transaction;
    private ImageButton cameraButton;
    private ImageButton searchButton;
    private MyApplication myApplication;
    private Button send,search;
    private TextView searchTextView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.index_fragment,container,false);
        myApplication = (MyApplication) getActivity().getApplication();
        cameraButton = (ImageButton) view.findViewById(R.id.index_top_bar_camera);
        searchButton = (ImageButton) view.findViewById(R.id.index_top_bar_message);
        send=(Button)view.findViewById(R.id.send);
        search=(Button)view.findViewById(R.id.search);
        meButton = (Button) view.findViewById(R.id.me_button);
        searchTextView = (EditText) view.findViewById(R.id.index_top_bar_input);

        meButton.setOnClickListener(this);
        cameraButton.setOnClickListener(this);
        searchButton.setOnClickListener(this);
        send.setOnClickListener(this);
        search.setOnClickListener(this);

        searchTextView.setEnabled(true);
        searchTextView.setFocusable(false);
        searchTextView.setOnClickListener(this);
        fm = getFragmentManager();
        transaction = fm.beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        return view;
    }

    @Override
    protected void onBack() {
        getActivity().finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.me_button:
                if(!isLogin()) {//没有登陆跳转登陆界面
                    toLoginFragment(MeFragment.class.getName());
                }else {
                    toMeFragment();//登陆后跳转"我"界面
                }
                break;
            case R.id.index_top_bar_camera:
                startCamera();
                break;
            case R.id.index_top_bar_message:
                toSearchFragment();
                break;
            case R.id.send:
                if(isLogin()) {
                    toSendFragment();
                }else{
                    toLoginFragment(AddressSendFragment.class.getName());
                }
                break;
            case R.id.search:
                toSearchFragment();
                break;
            case R.id.index_top_bar_input:
                toSearchFragment();
                break;
        }
    }

    private boolean isLogin(){
        if(myApplication.getUserInfo().getLoginState()){
            return true;
        }else{
            return false;
        }
    }

    /**
     * 跳转到测试用百度地图页面
     */
    private void toTestMap(){
        String expressID = "";
        ArrayList<String> entityNames = new ArrayList<>();
        entityNames.add("mycar");
        MyFragmentManager.turnBaiduFragment(MainFragment.class,MyBaiduMapFragment.class,null,expressID,getFragmentManager());
    }

    /**
     * 跳转到搜索后的界面，测试用
     */
    private void toSearchResult(){
        MyFragmentManager.turnFragment(MainFragment.class,SearchExpressFragment.class,null,getFragmentManager());
    }
    /**
     * 跳转到“我”界面
     */
    private void toMeFragment(){
        MyFragmentManager.turnFragment(MainFragment.class,MeFragment.class,null,getFragmentManager());
    }


    /**
     * 跳转到搜索界面
     */
    private void toSearchFragment(){
        MyFragmentManager.turnFragment(getClass(), SearchMainFragment.class,null,getFragmentManager());
    }


    /**
     * 跳转到寄快递界面
     */
    private void toSendFragment(){
        Bundle bundle = new Bundle();
        FromAndTo fromAndTo = new FromAndTo(getClass().getName(),AddressReceiveFragment.class.getName());
        bundle.putParcelable("fromandto",fromAndTo);
        MyFragmentManager.turnFragment(MainFragment.class, AddressSendFragment.class,bundle,getFragmentManager());
    }

    /**
     * 跳转到登陆界面
     */
    private void toLoginFragment(String to){
        Bundle bundle = new Bundle();
        FromAndTo fromAndTo = new FromAndTo();
        fromAndTo.setFrom(getClass().getName());
        fromAndTo.setTo(to);
        bundle.putParcelable("fromandto",fromAndTo);
        MyFragmentManager.turnFragment(MainFragment.class,LoginFragment.class,bundle,getFragmentManager());
    }

    /**
     * 跳转到扫码界面
     */
    private void startCamera(){
        startActivityForResult(new Intent(getActivity(),CaptureActivity.class),0);
    }


    /**
     * 获取上个界面返回的值
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode==Activity.RESULT_OK)
        {
            Bundle bundle=data.getExtras();
            String result=bundle.getString("result");
            Toast.makeText(getActivity(),result,Toast.LENGTH_LONG).show();
            if(result!=null){
                cameraToSearchFragment(result);
            }
        }
    }

    private void cameraToSearchFragment(String result){
        Bundle bundle = new Bundle();
        bundle.putString("expressid",result);
        MyFragmentManager.turnFragment(getClass(),SearchMainFragment.class,bundle,getFragmentManager());
    }
}