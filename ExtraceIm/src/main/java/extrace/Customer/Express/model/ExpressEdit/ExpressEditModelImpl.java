package extrace.Customer.Express.model.ExpressEdit;
import android.app.Activity;

import org.json.JSONException;
import org.json.JSONObject;

import extrace.Customer.Express.presenter.express_edit_presenter.ExpressPresenter;
import extrace.net.VolleyHelper;
import extrace.ui.main.R;

/**
 * Created by 黎明 on 2016/4/16.
 * 通过三个参数寄快递
 *
 */
public class ExpressEditModelImpl extends VolleyHelper implements ExpressEditModel {

    ExpressPresenter ExpressPresenter;
    String url="";
    String turl;
    public ExpressEditModelImpl(Activity activity, ExpressPresenter ExpressPresenter) {
        super(activity);
        this.ExpressPresenter = ExpressPresenter;
        turl=activity.getResources().getString(R.string.base_url);
        url = turl;
    }

    @Override
    public void newExpress(int customerId,int senderID, int receiverID) {
      url+="/REST/Domain/prepareSendExpress/customerId/"+customerId+"/sendAddressId/"+senderID+"/recAddressId/"+receiverID;
        try {
            JSONObject object=new JSONObject();
            doJson(url, VolleyHelper.GET,object);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDataReceive(Object jsonOrArray) {
        JSONObject jsonObject = (JSONObject) jsonOrArray;
        try {
            //返回值 参数名待定
            String ID=jsonObject.getString("state");
                ExpressPresenter.onSuccess(ID);
        } catch (JSONException e) {
            e.printStackTrace();
            ExpressPresenter.onFail("error");
        }finally {
            url = turl;
        }

    }
    @Override
    public void onError(String errorMessage) {
        ExpressPresenter.onFail(errorMessage);
        url = turl;
    }

}

