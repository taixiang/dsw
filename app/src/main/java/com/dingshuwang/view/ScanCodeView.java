package com.dingshuwang.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dingshuwang.R;
import com.dingshuwang.model.ScanItem;
import com.dingshuwang.util.GlideImgManager;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author tx
 * @date 2018/6/15
 */
public class ScanCodeView extends LinearLayout {


    @Bind(R.id.iv)
    ImageView iv;
    @Bind(R.id.tv_name)
    TextView tvName;
    @Bind(R.id.tv_sale_nums)
    TextView tvSale;
    @Bind(R.id.tv_goods_nums)
    TextView tvGoods;
    @Bind(R.id.tv_infor)
    TextView tvInfo;

    public ScanCodeView(Context context) {
        this(context, null);
    }

    public ScanCodeView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScanCodeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.layout_scan_view, this, true);
        ButterKnife.bind(this);
    }

    public void setData(Context context, ScanItem item) {
        GlideImgManager.loadImage(context, item.getPros().getImg_url(), iv);
        tvName.setText(item.getPros().getName());
        tvSale.setText("识别号：" + item.getPros().getSale_nums());
        tvGoods.setText("编码数：" + item.getPros().getGoods_nums());
        tvInfo.setText("出版日期：" + item.getPros().getInfor());
    }

}
