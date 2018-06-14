package com.dingshuwang.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.dingshuwang.APIURL;
import com.dingshuwang.Constants;
import com.dingshuwang.DataView;
import com.dingshuwang.R;
import com.dingshuwang.adapter.PointAdapter;
import com.dingshuwang.base.BaseFragment;
import com.dingshuwang.model.PointItem;
import com.dingshuwang.util.GsonUtils;
import com.dingshuwang.util.RequestUtils;
import com.dingshuwang.util.StringUtils;

import butterknife.Bind;

/**
 * Created by tx on 2017/6/22.
 */
public class PointFragment extends BaseFragment implements DataView {

    @Bind(R.id.listview)
    ListView listView;

    @Bind(R.id.tv_point)
    TextView tv_point;

    //积分
    public static final String point_url = "point_url";

    public static PointFragment newInstance(){
        return new PointFragment();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        point_url();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_point,null);
    }

    //积分
    private void point_url(){
        String url = String.format(APIURL.point, Constants.USER_ID);
        RequestUtils.getDataFromUrl(mActivity,url,this,point_url,false,false);
    }

    @Override
    public void onGetDataFailured(String msg, String requestTag) {

    }

    @Override
    public void onGetDataSuccess(String result, String requestTag) {
        if(result != null){
            if(point_url.equals(requestTag)){
                PointItem item = GsonUtils.jsonToClass(result,PointItem.class);
                if(item != null && item.message != null && item.message.size()>0){
                    double total = 0;
                    for(PointItem.Point point : item.message){
                        total+=point.value;
                    }
                    String d = StringUtils.doubleFormat(total);

                    tv_point.setText("现有"+d+"积分");
                    listView.setAdapter(new PointAdapter(item.message,mActivity));
                }
            }
        }
    }

    @Override
    public String getFragmentTitle() {
        return null;
    }
}
