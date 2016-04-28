package extrace.user.telephone;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Map;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import extrace.ToolBox.CheckInput;
import extrace.ToolBox.CountDown;
import extrace.ui.main.R;
import extrace.user.login.LoginFragment;

/**
 * Created by chao on 2016/4/28.
 */
public class ChangeTelFragment extends Fragment implements View.OnClickListener,ChangeTelView{
    private EditText telEdit;
    private EditText verifyEdit;
    private Button verifyGetButton;
    private Button verifySubmitButton;

    private String tel;
    private String verify;

    ChangeTelPresenterImpl changeTelPresenterImpl;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_change_telephone,container,false);

        changeTelPresenterImpl = new ChangeTelPresenterImpl(getActivity(),this);

        ((TextView)view.findViewById(R.id.top_bar_center_text)).setText("手机号修改");
        telEdit = (EditText) view.findViewById(R.id.user_change_tel);
        verifyEdit = (EditText) view.findViewById(R.id.user_change_tel_verify);
        verifyGetButton = (Button) view.findViewById(R.id.user_change_tel_get_verify);
        verifySubmitButton = (Button) view.findViewById(R.id.user_change_tel_submit);

        view.findViewById(R.id.top_bar_left_img).setOnClickListener(this);
        verifyGetButton.setOnClickListener(this);
        verifySubmitButton.setOnClickListener(this);

        initSMS();//初始化验证码发送

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.top_bar_left_img:
                getFragmentManager().popBackStack();
                break;
            case R.id.user_change_tel_submit:
                checeVerify();
                break;
            case R.id.user_change_tel_get_verify:
                getVerify();
                break;
            default:
                break;
        }
    }

    private void checeVerify(){
        verify = verifyEdit.getText().toString();
        tel = telEdit.getText().toString();
        if(verify!=null){
            SMSSDK.submitVerificationCode(LoginFragment.COUNTRY_CODE,tel,verify);
        }else{
            verifyEdit.setError("请填写验证码");
        }
    }

    /**
     * 获取验证码
     */
    CountDown countDown;
    @Override
    public void getVerify() {
        tel = telEdit.getText().toString();
        if(CheckInput.checkTel(tel)){
            SMSSDK.getVerificationCode(LoginFragment.COUNTRY_CODE, tel);
            countDown = new CountDown(getActivity(),verifySubmitButton,60,"重新获取验证码");
            countDown.execute(60);
        }else {
            telEdit.setError("手机号错误");
        }
    }

    @Override
    public void onDestroy() {
        if(countDown!=null && countDown.getStatus() != AsyncTask.Status.FINISHED){
            countDown.cancel(true);
        }
    }

    /**
     * 初始化验证码发送器
     */
    private EventHandler eh;
    Handler smsHandler;
    private void initSMS(){
        smsHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                String mess = (String) msg.obj;
                if(msg.what == 1){
                    //提交
                    ArrayList<Map<String,String>> data = (ArrayList<Map<String, String>>) msg.obj;
                    if(true){//检测返回数据是否准确
                        submit();
                    }else {
                        showToast("验证码验证失败，请重试");
                    }
                }
                showToast(mess);
            }
        };
        eh=new EventHandler(){
            @Override
            public void afterEvent(int event, int result, Object data) {
                if (result == SMSSDK.RESULT_COMPLETE) {
                    //回调完成
                    if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                        //提交验证码成功
                        smsHandler.sendMessage(smsHandler.obtainMessage(1,data));
                    }else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE){
                        //获取验证码成功
                        smsHandler.sendMessage(smsHandler.obtainMessage(0,"发送验证码成功，请查收"));
                    }else if (event ==SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES){
                        //返回支持发送验证码的国家列表
                    }
                }else{
                    ((Throwable)data).printStackTrace();
                }
            }
        };
        SMSSDK.registerEventHandler(eh); //注册短信回调

    }

    private void showToast(String message){
        Toast.makeText(getActivity(),message,Toast.LENGTH_SHORT).show();
    }

    /**
     * 提交用户手机号数据
     */
    @Override
    public void submit() {
        changeTelPresenterImpl.onSubmit(tel);
    }

    /**
     * 提交成功回调
     */
    @Override
    public void onSubmitSuccess() {
        showToast("电话号码修改成功");
        getFragmentManager().popBackStack();
    }

    /**
     * 错误处理
     * @param errormessage
     */
    @Override
    public void onError(String errormessage) {
        showToast(errormessage);
    }
}
