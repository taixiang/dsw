package com.dingshuwang.fragment;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

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
import com.dingshuwang.util.RequestUtils;
import com.dingshuwang.util.StringUtils;
import com.dingshuwang.util.UIUtil;
import com.xys.libzxing.zxing.activity.CaptureActivity;

import java.io.File;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/7/24.
 */
public class PurchaseFragment extends BaseFragment implements DataView {

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

    @Bind(R.id.iv_zxing)
    ImageView iv_zxing;
    @Bind(R.id.iv_photo)
    ImageView iv_photo;
    @Bind(R.id.container)
    LinearLayout container;
    @Bind(R.id.tv_add_photo)
    TextView tv_add_photo;
    @Bind(R.id.btn_ok)
    Button btn_ok;
    private String[] strs = {"全新品"," 二手品—非常好" ," 二手品—好" ,  " 二手品—可接受" , " 二手品—笔记较多" };

    private static final String REQUEST_MODIFY_PHOTO_TAG = "photo";

    private static final String publish_photo = "publish";

    private String imagePath;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_purchase,null);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        register();
        mActivity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN | WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        imagePath = "";
        Log.i("》》》》》  ","22222");
        et_isbn.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                btn_ok.setEnabled(true);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mActivity.unregisterReceiver(receiver);
    }

    private void doUploadModifyPhoto(String imagePath, String url)
    {
//        String modifyPhothoUrl = APIURL.photo_url;
        mActivity.showProgressDialog();
        OkHttpUtils.TheadUtils.ModifyPhoto(mActivity, url, imagePath, false, this, REQUEST_MODIFY_PHOTO_TAG);
    }

    private void doGetPublish(String user_id, String isbn, String name, String author, String publish, String origial, String price, String count, String status, String remark){
        String url = String.format(APIURL.purchase,user_id,isbn,name,author,publish,origial,price,count,status,remark,"1");
//        RequestUtils.getDataFromUrl(mActivity,url,this,publish_photo,false,false);
        doUploadModifyPhoto(imagePath,url);
    }

    @OnClick(R.id.photo_container)
    void showPhoto(){
        PhotoUtils.showSelectDialog2(mActivity);
    }

    @OnClick(R.id.iv_zxing)
    void zxing(){
        Intent openCameraIntent = new Intent(mActivity, CaptureActivity.class);
        openCameraIntent.putExtra("tag_code",3);
        startActivityForResult(openCameraIntent, 666);
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
        popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
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

            doGetPublish(Constants.USER_ID,isbn, name, author, publish, original, price, count, status, remark);
        }
    }

    @OnClick(R.id.check)
    public void check(){
        if(et_isbn.getText().toString().trim() != null && et_isbn.getText().toString().trim().length()>0){
            checkId(et_isbn.getText().toString().trim());
            scanResult = et_isbn.getText().toString().trim();
        }

    }
    @Override
    public void onGetDataFailured(String msg, String requestTag) {

    }

    //    String phtotByte ;
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
                    }else {
                        if(item.message.equals("can_purchase")){
                            mActivity.showToast("可采购");
                        }else {
                            mActivity.showToast("不可采购");
                        }
                    }
                }
            }
//            Intent intent= new Intent(Constants.GOTOFIRST);
//            mActivity.sendBroadcast(intent);
            Intent intent = new Intent(Constants.GOTOFIRST);
            mActivity.sendBroadcast(intent);
        }else if(requestTag.equals(publish_photo)){

        }else if(requestTag.equals("checkId")){
            if(result != null){
                ReleaseItem item = GsonUtils.jsonToClass(result,ReleaseItem.class);
                if(item != null){
                    if(item.result.equals("true")){
                        if(item.message.equals("ok")){
                            isPurchase(scanResult);
                        }
                    }
                }
            }
        }else if(requestTag.equals("isPurchase")){
            if(result != null){
                ReleaseItem item = GsonUtils.jsonToClass(result,ReleaseItem.class);
                if(item != null){
                    if(item.result.equals("true")){
                        if(item.message.equals("ok")){
                            mActivity.showToast("可采购");
                            btn_ok.setEnabled(false);
                        }else {
                            mActivity.showToast("不可采购");
                        }
                    }
                }
            }
        }
    }
    private void register(){
        IntentFilter filter = new IntentFilter(Constants.PHOTO_URL_2);
        mActivity.registerReceiver(receiver,filter);
    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals(Constants.PHOTO_URL_2)){
                imagePath = intent.getStringExtra("photo_url");
                Bitmap bitmap = BitmapUtils.scaleBitmap(imagePath);
                if (null != bitmap)
                {
                    iv_photo.setImageBitmap(bitmap);
                    iv_photo.setVisibility(View.VISIBLE);
                    tv_add_photo.setVisibility(View.GONE);

//                    ByteArrayOutputStream output = new ByteArrayOutputStream();//初始化一个流对象
//                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, output);//把bitmap100%高质量压缩 到 output对象里
//
//                    byte[] result = output.toByteArray();//转换成功了
                    try {
//                        output.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

//                    try {
//                        String str = new String(result,"UTF-8");
//                    } catch (UnsupportedEncodingException e) {
//                        e.printStackTrace();
//                    }


                }

            }
        }
    };

private String scanResult;
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i("》》》》》  "," requestCode"+requestCode + "resultCode   "+resultCode);
        if (resultCode == Constants.CAMMER_PURCHASE)
        {
                Bundle bundle = data.getExtras();
                 scanResult= bundle.getString("result");
                et_isbn.setText(scanResult);
                checkId(scanResult);
        }
    }

    private void checkId(String isbn){
        String url = String.format(APIURL.check_id, Constants.USER_ID,isbn);
        RequestUtils.getDataFromUrl(mActivity,url,this,"checkId",false,false);
    }

    public void isPurchase(String isbn){
        String url = String.format(APIURL.isPurchase, Constants.USER_ID,isbn);
        RequestUtils.getDataFromUrl(mActivity,url,this,"isPurchase",false,false);
    }

    @Override
    public String getFragmentTitle() {
        return null;
    }
}
