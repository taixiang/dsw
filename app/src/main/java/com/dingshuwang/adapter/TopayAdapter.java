package com.dingshuwang.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dingshuwang.DetailActivity;
import com.dingshuwang.PayActivity;
import com.dingshuwang.R;
import com.dingshuwang.base.BaseActivity;
import com.dingshuwang.model.WaitPayItem;
import com.dingshuwang.util.GlideImgManager;
import com.dingshuwang.util.UIUtil;

import java.util.List;

/**
 * Created by Administrator on 2016/7/27.
 */
public class TopayAdapter extends BaseAdapter {

    private List<WaitPayItem.ShopItem> list;
    private BaseActivity activity;
    private LayoutInflater inflater;
    private int type;

    public TopayAdapter(List<WaitPayItem.ShopItem> list, BaseActivity activity, int type) {
        this.list = list;
        this.activity = activity;
        inflater = LayoutInflater.from(activity);
        this.type = type;
    }

    @Override
    public int getCount() {
        return list== null ? 0 : list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(convertView == null){
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.adapter_to_pay,null);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.container = (LinearLayout) convertView.findViewById(R.id.container);
        viewHolder.container.removeAllViews();
         final WaitPayItem.ShopItem items  = list.get(position);
        for(int i=0;i<items.Order.size();i++){
            if(items.Order != null){
                View view = inflater.inflate(R.layout.to_pay_item_view,null);
//                TextView tv_name = (TextView) view.findViewById(R.id.tv_name);
//                TextView tv_money = (TextView) view.findViewById(R.id.tv_money);
//                TextView tv_status = (TextView) view.findViewById(R.id.tv_status);
                TextView tv_id = (TextView) view.findViewById(R.id.tv_id);
                TextView order_detail = (TextView) view.findViewById(R.id.order_detail);
                LinearLayout container = (LinearLayout) view.findViewById(R.id.container);
//                tv_name.setText(items.Order.get(i).supplier_name);
                final List<WaitPayItem.ShopItem.OrderItem.Goods> good = items.Order.get(i).order_goods;
                container.removeAllViews();
                for( int j=0;j<good.size();j++){
                    View view2 = inflater.inflate(R.layout.item_good_view,null);
                    ImageView iv_icon = (ImageView) view2.findViewById(R.id.iv_icon);
                    TextView tv_boook = (TextView) view2.findViewById(R.id.tv_book);
                    TextView tv_price = (TextView) view2.findViewById(R.id.price);
                    TextView tv_count = (TextView) view2.findViewById(R.id.tv_count);
                    GlideImgManager.loadImage(activity,good.get(j).img_url,iv_icon);
                    tv_boook.setText(good.get(j).goods_title);
                    tv_price.setText("￥"+good.get(j).price_sell);
                    tv_count.setText("x "+good.get(j).quantity);
                    container.addView(view2);
                    final int finalJ = j;
                    view2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(UIUtil.isfastdoubleClick()){
                                return;
                            }

                            DetailActivity.actionDetail(activity,good.get(finalJ).goods_id);
                        }
                    });
                }

                tv_id.setText("订单号："+items.Order.get(i).id);
                final int finalI = i;
                order_detail.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(UIUtil.isfastdoubleClick()){
                            return;
                        }
                        PayActivity.actConfirm(activity,items.Order.get(finalI).id);
                    }
                });
                if(type == 1){


                }else if(type == 2){

                }else if(type == 3){

                }else if(type == 4){

                }

                viewHolder.container.addView(view);
            }
        }
        return convertView;
    }

    private class ViewHolder{
        private LinearLayout container;
    }

}
