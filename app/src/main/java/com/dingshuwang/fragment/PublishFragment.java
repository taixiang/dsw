package com.dingshuwang.fragment;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.LocationSource;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.MyLocationStyle;
import com.dingshuwang.APIURL;
import com.dingshuwang.Constants;
import com.dingshuwang.DataView;
import com.dingshuwang.LoginActivity;
import com.dingshuwang.R;
import com.dingshuwang.adapter.StatusAdapter;
import com.dingshuwang.base.BaseFragment;
import com.dingshuwang.base.MMApplication;
import com.dingshuwang.model.ReleaseItem;
import com.dingshuwang.util.BitmapUtils;
import com.dingshuwang.util.GsonUtils;
import com.dingshuwang.util.OkHttpUtils;
import com.dingshuwang.util.PhotoUtils;
import com.dingshuwang.util.StringUtils;
import com.dingshuwang.util.UIUtil;
import com.xys.libzxing.zxing.activity.CaptureActivity;

import java.io.File;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by tx on 2017/6/6.
 */

public class PublishFragment extends BaseFragment implements DataView,AMapLocationListener,LocationSource{

    @Bind(R.id.et_isbn)
    EditText et_isbn;
    @Bind(R.id.et_name)
    EditText et_name;
    @Bind(R.id.et_author)
    EditText et_author;
    @Bind(R.id.et_publish)
    EditText et_publish;
    @Bind(R.id.et_original)
    EditText et_original;
    @Bind(R.id.et_price)
    EditText et_price;
    @Bind(R.id.et_count)
    EditText et_count;
    @Bind(R.id.et_status)
    EditText et_status;
    @Bind(R.id.et_remark)
    EditText et_remark;

    @Bind(R.id.et_linkname)
    EditText et_linkname;
    @Bind(R.id.et_phone)
    EditText et_phone;
    @Bind(R.id.et_school)
    EditText et_school;

    @Bind(R.id.iv_zxing)
    ImageView iv_zxing;
    @Bind(R.id.iv_photo)
    ImageView iv_photo;
    @Bind(R.id.container)
    LinearLayout container;
    @Bind(R.id.tv_add_photo)
    TextView tv_add_photo;

    @Bind(R.id.mapView)
    MapView mapView;

    private String type = "0";
    private String[] strs = {"全新品"," 二手品—非常好" ," 二手品—好" ,  " 二手品—可接受" , " 二手品—笔记较多" };

    private static final String REQUEST_MODIFY_PHOTO_TAG = "photo";

    private static final String publish_photo = "publish";

    private String imagePath;
    private AMap aMap;
    private String location = "";

    private OnLocationChangedListener mListener;
    private AMapLocationClient mlocationClient;
    private AMapLocationClientOption mLocationOption;


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mActivity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN | WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        mapView.onCreate(savedInstanceState);
        register();
        imagePath = "";
        mListener = null;
        mlocationClient = null;
        mLocationOption = null;
        location = "";

        Log.i("》》》》   "," map ===  ");
        aMap = mapView.getMap();
        MyLocationStyle myLocationStyle;
        myLocationStyle = new MyLocationStyle();//初始化定位蓝点样式类myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
        myLocationStyle.strokeColor(Color.BLACK);// 设置圆形的边框颜色
        myLocationStyle.radiusFillColor(Color.argb(180, 245, 245, 245));// 设置圆形的填充颜色
        // myLocationStyle.anchor(int,int)//设置小蓝点的锚点
        myLocationStyle.strokeWidth(1.0f);// 设置圆形的边框粗细
        aMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
        aMap.setLocationSource(this);// 设置定位监听
        aMap.getUiSettings().setMyLocationButtonEnabled(true);//设置默认定位按钮是否显示，非必需设置。
        aMap.setMyLocationEnabled(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_publish,null);
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(receiver!= null){
            mActivity.unregisterReceiver(receiver);
        }
        mapView.onDestroy();
        if(null != mlocationClient){
            mlocationClient.onDestroy();
        }
    }

    private void doUploadModifyPhoto(String imagePath,String url)
    {
        mActivity.showProgressDialog();
        OkHttpUtils.TheadUtils.ModifyPhoto(mActivity, url, imagePath, false, this, REQUEST_MODIFY_PHOTO_TAG);
    }

    private void doGetPublish(String user_id,String isbn,String name,String author,String publish,String origial,String price,String count,String status,String remark,String linkName, String linkPhone,String university,String location){
        String url = String.format(APIURL.publish,user_id,isbn,name,author,publish,origial,price,count,status,remark,linkName, linkPhone,university,location);
        doUploadModifyPhoto(imagePath,url);
    }

    @OnClick(R.id.photo_container)
    void showPhoto(){
        PhotoUtils.showSelectDialog(mActivity);
    }

    @OnClick(R.id.iv_zxing)
    void zxing(){
        Intent openCameraIntent = new Intent(mActivity, CaptureActivity.class);
        openCameraIntent.putExtra("tag_code",2);
        startActivityForResult(openCameraIntent, Constants.CODE_PUBLISH);
    }

    PopupWindow popupWindow ;

    @OnClick(R.id.et_status)
    void choseStatus(){
        showPop();
    }

    private void showPop(){
        if(null != popupWindow){
            popupWindow.dismiss();
        }
        View view = LayoutInflater.from(mActivity).inflate(R.layout.popup_listview,null);
        popupWindow = new PopupWindow(view,ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
//        popupWindow.setContentView(view);
        ListView listView = (ListView) view.findViewById(R.id.listview);
        listView.setAdapter(new StatusAdapter(strs,mActivity));
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setOutsideTouchable(true);
        popupWindow.showAtLocation(container, Gravity.CENTER,0,0);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                et_status.setText(strs[position]);
                if(null != popupWindow){
                    popupWindow.dismiss();
                }
            }
        });
    }

    @OnClick(R.id.btn_ok)
    void btn_ok() {
        if (UIUtil.isfastdoubleClick()) {
            return;
        }
        if(!MMApplication.mIsLogin){
            mActivity.showToast("请先登录");
            LoginActivity.actionLogin(mActivity,Constants.LOGIN);
            return;
        }
        String isbn = et_isbn.getText().toString().trim();
        String name = et_name.getText().toString().trim();
        String author = et_author.getText().toString().trim();
        String publish = et_publish.getText().toString().trim();
        String original = et_original.getText().toString().trim();
        String price = et_price.getText().toString().trim();
        String count = et_count.getText().toString().trim();
        String status = et_status.getText().toString().trim();
        String remark = et_remark.getText().toString().trim();

        String linkName= et_linkname.getText().toString().trim();
        String phone = et_phone.getText().toString().trim();
        String university = et_school.getText().toString().trim();
        if (StringUtils.isEmpty(isbn) ||  isbn.length() != 13) {
            mActivity.showToast("请输入正确的isbn号码");
            return;
        }
        if (StringUtils.isEmpty(name)) {
            mActivity.showToast("请输入书名");
            return;
        }
//        if (StringUtils.isEmpty(author)) {
//            mActivity.showToast("请输入作者");
//            return;
//        }
//        if (StringUtils.isEmpty(publish)) {
//            mActivity.showToast("请输入出版社");
//            return;
//        }
//        if (StringUtils.isEmpty(original)) {
//            mActivity.showToast("请输入原价");
//            return;
//        }
//        if (StringUtils.isEmpty(price)) {
//            mActivity.showToast("请输入出售价");
//            return;
//        }
//        if (StringUtils.isEmpty(count)) {
//            mActivity.showToast("请输入数量");
//            return;
//        }
//        if (StringUtils.isEmpty(status)) {
//            mActivity.showToast("请选择新旧程度");
//            return;
//        }
//        if (StringUtils.isEmpty(remark)) {
//            mActivity.showToast("请输入备注");
//            return;
//        }
        if(StringUtils.isEmpty(imagePath) || imagePath.length() == 0){
            mActivity.showToast("请添加封面");
            return;
        }
        File file = new File(imagePath);


        if (file.exists()) {
            String filrName = imagePath.substring(imagePath.lastIndexOf("/") + 1);

            doGetPublish(Constants.USER_ID,isbn, name, author, publish, original, price, count, status, remark,linkName,phone,university,location);
        }
    }

    private void register(){
        IntentFilter filter = new IntentFilter(Constants.PHOTO_URL);
        mActivity.registerReceiver(receiver,filter);
    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals(Constants.PHOTO_URL)){
                imagePath = intent.getStringExtra("photo_url");
                Bitmap bitmap = BitmapUtils.scaleBitmap(imagePath);
                if (null != bitmap)
                {
                    iv_photo.setImageBitmap(bitmap);
                    iv_photo.setVisibility(View.VISIBLE);
                    tv_add_photo.setVisibility(View.GONE);

                    try {
//                        output.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

            }
        }
    };

    @Override
    public String getFragmentTitle() {
        return null;
    }

    @Override
    public void onGetDataFailured(String msg, String requestTag) {
        mActivity.dismissProgressDialog();
        mActivity.showToast("发布失败，稍后再试");
    }

    @Override
    public void onGetDataSuccess(String result, String requestTag) {

        if(requestTag.equals(REQUEST_MODIFY_PHOTO_TAG)){
//            iv_photo.setVisibility(View.VISIBLE);
            mActivity.dismissProgressDialog();
            Log.i("》》》》》》   ","result  "+result);
            if(result != null){
                ReleaseItem item = GsonUtils.jsonToClass(result,ReleaseItem.class);
                if(item != null){
                    if(item.result.equals("true")){
                        mActivity.showToast("发布成功");

                        et_isbn.setText("");
                        et_name.setText("");
                        et_author.setText("");
                        et_publish.setText("");
                        et_original.setText("");
                        et_price.setText("");
                        et_count.setText("");
                        et_status.setText("");
                        et_remark.setText("");
                        et_linkname.setText("");
                        et_phone.setText("");
                        et_school.setText("");


                    }
                }
            }
//            Intent intent= new Intent(Constants.GOTOFIRST);
//            mActivity.sendBroadcast(intent);
            Intent intent = new Intent(Constants.GOTOFIRST);
            mActivity.sendBroadcast(intent);
        }else if(requestTag.equals(publish_photo)){

        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i("》》》》》  "," requestCode"+requestCode + "resultCode   "+resultCode);

            if(Constants.CAMMER_PUBLISH == resultCode){
                Bundle bundle = data.getExtras();
                String scanResult = bundle.getString("result");
                et_isbn.setText(scanResult);
            }

    }

    /**
     * 定位成功后回调函数
     */
    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        Log.i("》》》》  ","  locationchanged ");
        if (mListener != null && amapLocation != null) {
            if (amapLocation != null
                    && amapLocation.getErrorCode() == 0) {
                mListener.onLocationChanged(amapLocation);// 显示系统小蓝点
                aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(amapLocation.getLatitude(), amapLocation.getLongitude()), 19));
                location = amapLocation.getAddress();
                Log.i("  》》》》》  ",  "address === "+  location);
            } else {
                String errText = "定位失败," + amapLocation.getErrorCode()+ ": " + amapLocation.getErrorInfo();
                Log.e("AmapErr",errText);
            }
        }
    }

    /**
     * 激活定位
     */
    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        Log.i("》》》》  ","  acyivated ");
        mListener = onLocationChangedListener;
        if (mlocationClient == null) {
            mlocationClient = new AMapLocationClient(mActivity);
            mLocationOption = new AMapLocationClientOption();
            //设置定位监听
            mlocationClient.setLocationListener(this);
            mLocationOption.setOnceLocation(true);
            //设置为高精度定位模式
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            mLocationOption.setLocationCacheEnable(true);
            //设置定位参数
            mlocationClient.setLocationOption(mLocationOption);
            // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
            // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
            // 在定位结束后，在合适的生命周期调用onDestroy()方法
            // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
            mlocationClient.startLocation();
        }
    }

    @Override
    public void deactivate() {
        mListener = null;
        if (mlocationClient != null) {
            mlocationClient.stopLocation();
            mlocationClient.onDestroy();
        }
        mlocationClient = null;

    }
}
