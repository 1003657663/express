package extrace.main;

import android.app.Application;

import cn.smssdk.SMSSDK;
import extrace.model.EmployeesEntity;
import extrace.model.UserInfo;

/**
 * Created by chao on 2016 /4/17.
 * 使用application保存全局变量
 */
public class MyApplication extends Application {
    //全局变量
    private UserInfo userInfo;
    private EmployeesEntity employeesInfo;

    @Override
    public void onCreate() {
        super.onCreate();
        userInfo = new UserInfo(getApplicationContext());
        employeesInfo = new EmployeesEntity(2, "name", "password","00000000000",1,"jobtext",0, 3,"1","2");
        //--------------------------------test
        //userInfo.setLoginState(true);
        //------------------------------------
        SMSSDK.initSDK(this, "12282c18097fb", "55a709db05d0213647f5bd05e29c24f6");//初始化短信发送sdk
    }

    public EmployeesEntity getEmployeesInfo() {
        if (employeesInfo != null) {
            return employeesInfo;
        } else {
            employeesInfo =  new EmployeesEntity(3, "name", "password","00000000000",1,"jobtext",0, 3,"1","2");
            return employeesInfo;
        }
    }

    public UserInfo getUserInfo() {
        if (userInfo != null) {
            return userInfo;
        } else {
            userInfo = new UserInfo(getApplicationContext());
            return userInfo;
        }
    }
    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public void setEmployeesInfo(EmployeesEntity employeesInfo) {
        this.employeesInfo = employeesInfo;
    }
}
