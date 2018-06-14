package com.dingshuwang.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.dingshuwang.APIURL;
import com.dingshuwang.Constants;
import com.dingshuwang.DataView;
import com.dingshuwang.R;
import com.dingshuwang.alipayutil.Result;
import com.dingshuwang.alipayutil.SignUtils;
import com.dingshuwang.base.BaseFragment;
import com.dingshuwang.model.PayItem;
import com.dingshuwang.util.GsonUtils;
import com.dingshuwang.util.RequestUtils;
import com.dingshuwang.util.UIUtil;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

import butterknife.Bind;
import butterknife.OnClick;

import static com.dingshuwang.Constants.SDK_PAY_FLAG;

/**
 * Created by tx on 2017/7/5.
 */

public class PayFragment extends BaseFragment implements DataView{

    private static final String order_detail = "order_detail";
    private static final String order_success = "order_success";

    private String notify_url = "http://www.iisbn.com/direct_pay/notify_url.aspx";
    private String return_url = "http://www.iisbn.com/direct_pay/return_url.aspx";



    public static PayFragment newInstance(String order_id){
        PayFragment fragment = new PayFragment();
        Bundle bundle = new Bundle();
        bundle.putString("order_id",order_id);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Bind(R.id.ctv_alipay)
    CheckedTextView ctv_alipay;

    private String order_id;
    private String money;
    private String subject;
    private String body;

    @Bind(R.id.tv_order_no)
    TextView tv_order_no;

    @Bind(R.id.tv_real_amount)
    TextView tv_real_amount;
    @Bind(R.id.tv_express_fee)
    TextView tv_express_fee;
    @Bind(R.id.tv_point)
    TextView tv_point;
    @Bind(R.id.tv_groups_favourable)
    TextView tv_groups_favourable;

    @Bind(R.id.tv_pay)
    TextView tv_pay;

    @Bind(R.id.tv_address)
    TextView tv_address;
    @Bind(R.id.tv_name)
    TextView tv_name;
    @Bind(R.id.tv_phone)
    TextView tv_phone;
    @Bind(R.id.tv_order)
    TextView tv_order;
    @Bind(R.id.tv_way)
    TextView tv_way;

    @Bind(R.id.order_status)
    TextView order_status;

    @Bind(R.id.btn_ok)
    Button btn_ok;
    @Bind(R.id.pay_contain)
    LinearLayout pay_contain;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_pay,null);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        order_id = (String) getArguments().get("order_id");
        subject =  "丁书网商贸[订单流水号：" + order_id + "]";
        body =  "丁书网网上书店[订单流水号：" + order_id + "]";

        ctv_alipay.setChecked(true);
        ctv_alipay.setCheckMarkDrawable(R.mipmap.ic_pay_checked);
        doGetDetail(order_id);
    }

    @OnClick(R.id.btn_ok)
    void confirm(){
        if(UIUtil.isfastdoubleClick()){
            return;
        }
        if(order_id != null){
            pay(subject,body,money,order_id,notify_url,return_url);
        }

    }

    private void doGetDetail(String order_id){
        String url = String.format(APIURL.ORDER_DETAIL, Constants.USER_ID,order_id);
        RequestUtils.getDataFromUrl(mActivity,url,this,order_detail,false,false);
    }

    private void pay_success(){
        String url = String.format(APIURL.Pay_Success ,Constants.USER_ID,order_id,"9000");
        RequestUtils.getDataFromUrl(mActivity,url,this,order_success,false,false);

    }


    @Override
    public void onGetDataFailured(String msg, String requestTag) {

    }

    @Override
    public void onGetDataSuccess(String result, String requestTag) {
        if(null != result){
            if(order_detail.equals(requestTag)){
                PayItem payItem = GsonUtils.jsonToClass(result,PayItem.class);
                if(payItem != null && payItem.order != null){

                    if("待付款".equals(payItem.order.order_status)){
                        btn_ok.setVisibility(View.VISIBLE);
                        pay_contain.setVisibility(View.VISIBLE);
                    }

                    money = payItem.order.order_amount;
                    Log.i("》》》》》","------money"+money);
                    tv_order_no.setText(payItem.order.order_no);
                    tv_real_amount.setText(payItem.order.real_amount);
                    tv_express_fee.setText(payItem.order.express_fee);

                    order_status.setText(payItem.order.order_status);

                    tv_address.setText(payItem.order.area+payItem.order.address);
                    tv_name.setText(payItem.order.accept_name);
                    tv_phone.setText(payItem.order.telphone);
                    tv_order.setText(payItem.order.order_no);
                    tv_way.setText(payItem.order.express_title);

                    tv_point.setText("-"+payItem.order.point);
                    tv_groups_favourable.setText("-"+payItem.order.groups_favourable);
                    tv_pay.setText(payItem.order.order_amount);
                }
            }
        }
    }

    @Override
    public String getFragmentTitle() {
        return null;
    }

    /*** 支付宝 *****************************************************************/
    /**
     * call alipay sdk pay. 调用SDK支付
     */
    public void pay(
            String subject, String body, String price, String outNo, String notify_url, String return_url
    ) {
        String orderInfo = getOrderInfo(subject, body, price, outNo, notify_url, return_url);
        String sign = sign(orderInfo);
        try {
            // 仅需对sign 做URL编码
            sign = URLEncoder.encode(sign, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        final String payInfo = orderInfo + "&sign=\"" + sign + "\"&" + getSignType();

        Runnable payRunnable = new Runnable() {
            @Override
            public void run() {
                // 构造PayTask 对象
                PayTask alipay = new PayTask(mActivity);
                // 调用支付接口
                String result = alipay.pay(payInfo);
                Message msg = new Message();
                msg.what = Constants.SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    /**
     * create the order info. 创建订单信息
     */
    public String getOrderInfo(
            String subject, String body, String price, String outNo, String notify_url, String return_url
    ) {
        // 合作者身份ID
        String orderInfo = "partner=" + "\"" + Constants.PARTNER + "\"";

        // 卖家支付宝账号
        orderInfo += "&seller_id=" + "\"" + Constants.SELLER + "\"";

        // 商户网站唯一订单号
        orderInfo += "&out_trade_no=" + "\"" + outNo + "\"";

        // 商品名称
        orderInfo += "&subject=" + "\"" + subject + "\"";

        // 商品详情
        orderInfo += "&body=" + "\"" + body + "\"";

        // 商品金额
        orderInfo += "&total_fee=" + "\"" + price + "\"";// price

        // 服务器异步通知页面路径
        orderInfo += "&notify_url=" + "\"" + notify_url + "\"";

        // 接口名称， 固定值
        orderInfo += "&service=\"mobile.securitypay.pay\"";

        // 支付类型， 固定值
        orderInfo += "&payment_type=\"1\"";

        // 参数编码， 固定值
        orderInfo += "&_input_charset=\"utf-8\"";

        // 设置未付款交易的超时时间
        // 默认30分钟，一旦超时，该笔交易就会自动被关闭。
        // 取值范围：1m～15d。
        // m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
        // 该参数数值不接受小数点，如1.5h，可转换为90m。
        orderInfo += "&it_b_pay=\"30m\"";

        // 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
        orderInfo += "&return_url=\"" + return_url + "\"";

        // 调用银行卡支付，需配置此参数，参与签名， 固定值
        // orderInfo += "&paymethod=\"expressGateway\"";

        return orderInfo;
    }

    /**
     * get the out_trade_no for an order. 获取外部订单号
     */
    public String getOutTradeNo() {
        SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss", Locale.getDefault());
        Date date = new Date();
        String key = format.format(date);

        Random r = new Random();
        key = key + r.nextInt();
        key = key.substring(0, 15);
        return key;
    }

    /**
     * sign the order info. 对订单信息进行签名
     *
     * @param content 待签名订单信息
     */
    public String sign(String content) {
        return SignUtils.sign(content,Constants.RSA_PRIVATE);
//        return "wxxp1eaoa60lfppfbtzqbu9o2hjhnmlw";
    }

    /**
     * get the sign type we use. 获取签名方式
     */
    public String getSignType() {
        return "sign_type=\"RSA\"";
    }

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Constants.SDK_PAY_FLAG: {
                    Result resultObj = new Result((String) msg.obj);
                    String resultStatus = resultObj.resultStatus;
                    // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                    if (TextUtils.equals(resultStatus, "9000")) {
                        UIUtil.showToasts(mActivity, "支付成功");
//                        loadNext(OrderFeedbackAct.class, new String[]{"money", cousumTotalMoney + ""}, new String[]{"earn", earn}, new String[]{"businessId", bussnessId});
//                        initView();
                        pay_success();
                        mActivity.finish();
                    } else {
                        // 判断resultStatus 为非“9000”则代表可能支付失败
                        // “8000”
                        // 代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                        if (TextUtils.equals(resultStatus, "8000")) {
                            UIUtil.showToasts(mActivity, "支付结果确认中");
                        } else {
                            UIUtil.showToasts(mActivity, "支付失败");
                        }
                    }
                    break;
                }
                case Constants.SDK_CHECK_FLAG: {
                    UIUtil.showToasts(mActivity, "检查结果为：" + msg.obj);
                    break;
                }
            }
        }

        ;
    };


}
