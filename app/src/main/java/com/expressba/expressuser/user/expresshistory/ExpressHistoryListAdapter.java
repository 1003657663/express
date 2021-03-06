package com.expressba.expressuser.user.expresshistory;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import com.expressba.expressuser.model.ExpressInfo;
import com.expressba.expressuser.R;

/**
 * Created by songchao on 16/5/8.
 */
public class ExpressHistoryListAdapter extends BaseAdapter {

    private ArrayList<ExpressInfo> expressInfos;
    private Activity activity;
    public ExpressHistoryListAdapter(Activity activity,ArrayList<ExpressInfo> expressInfos){
        this.expressInfos = expressInfos;
        this.activity = activity;
    }


    @Override
    public int getCount() {
        return expressInfos.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private static class ViewHolder{
        public TextView expressIDText;
        public TextView sendNameText;
        public TextView sendTimeText;
        public TextView receiveNameText;
        public TextView receiveTimeText;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null){
            viewHolder = new ViewHolder();
            convertView = View.inflate(activity, R.layout.user_express_history_item,null);
            viewHolder.expressIDText = (TextView) convertView.findViewById(R.id.express_history_id);
            viewHolder.sendNameText = (TextView) convertView.findViewById(R.id.express_history_send_people);
            viewHolder.sendTimeText = (TextView) convertView.findViewById(R.id.express_history_sendtime);
            viewHolder.receiveNameText = (TextView) convertView.findViewById(R.id.express_history_receive_people);
            viewHolder.receiveTimeText = (TextView) convertView.findViewById(R.id.express_history_receivetime);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        ExpressInfo expressInfo = expressInfos.get(position);
        viewHolder.expressIDText.setText(expressInfo.getID());
        viewHolder.sendNameText.setText(expressInfo.getSname());
        if(expressInfo.getOutTime() == null){
            viewHolder.sendTimeText.setText("未揽收");
        }else{
            viewHolder.sendTimeText.setText(expressInfo.getOutTime());
        }
        if(expressInfo.getGetTime() == null){
            viewHolder.receiveTimeText.setText("未签收");
        }else {
            viewHolder.receiveTimeText.setText(expressInfo.getGetTime());
        }
        viewHolder.receiveNameText.setText(expressInfo.getRname());
        return convertView;
    }
}
