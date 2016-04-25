package extrace.user.address;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

import extrace.model.UserAddress;
import extrace.ui.main.R;
import extrace.user.address.addressEdit.AddressEditFragment;
import extrace.user.me.MeFragment;

/**
 * Created by chao on 2016/4/17.
 */
public class AddressFragment extends Fragment implements AddressView,View.OnClickListener {
    AddressPresenter presenter;
    LayoutInflater inflater;
    ViewGroup viewGroup;
    ListView listView;
    Integer receiveOrSend;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_address_fragment,container,false);
        this.inflater = inflater;
        this.viewGroup = container;
        Bundle bundle = getArguments();
        receiveOrSend = bundle.getInt("receiveOrSend");//获取上个页面传的发送或者接受地址的标志

        view.findViewById(R.id.top_bar_left_img).setOnClickListener(this);
        listView = (ListView) view.findViewById(R.id.user_address_list);
        presenter = new AddressPresenterImpl(getActivity(),this);

        if(receiveOrSend == MeFragment.SEND) {
            ((TextView) view.findViewById(R.id.top_bar_center_text)).setText("管理发货地址");
            presenter.getSendAddress();
        }else {
            ((TextView) view.findViewById(R.id.top_bar_center_text)).setText("管理收货地址");
            presenter.getReceiveAddress();
        }
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.top_bar_left_img:
                getFragmentManager().popBackStack();
                break;
            default:
                break;
        }
    }

    @Override
    public void addAddress(String name, final String tel, String address, boolean isDefault) {

    }

    @Override
    public void onError(String errorMessage) {
        Toast.makeText(getActivity(),errorMessage,Toast.LENGTH_LONG).show();
    }

    @Override
    public void toEditFragment(UserAddress userAddress) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        Bundle bundle = new Bundle();
        bundle.putParcelable("userAddress",userAddress);

        AddressEditFragment addressEditFragment = new AddressEditFragment();
        addressEditFragment.setArguments(bundle);
        ft.addToBackStack("address");
        ft.replace(R.id.fragment_container_layout,addressEditFragment).commit();
    }

    @Override
    public void refreshAddress(HashMap<Integer,UserAddress> addressMap) {
        //地址获取成功后，加载到list通过adapter
        AddressListApapter listApapter = new AddressListApapter(this,addressMap);
        listView.setAdapter(listApapter);
    }
}
