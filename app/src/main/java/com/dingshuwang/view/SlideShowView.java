//package com.dingshuwang.view;
//
//import android.app.Activity;
//import android.content.Context;
//import android.content.Intent;
//import android.graphics.drawable.Drawable;
//import android.os.AsyncTask;
//import android.os.Handler;
//import android.os.Message;
//import android.os.Parcelable;
//import android.support.v4.view.PagerAdapter;
//import android.support.v4.view.ViewPager;
//import android.support.v4.view.ViewPager.OnPageChangeListener;
//import android.util.AttributeSet;
//import android.view.LayoutInflater;
//import android.view.MotionEvent;
//import android.view.View;
//import android.widget.FrameLayout;
//import android.widget.ImageView;
//import android.widget.ImageView.ScaleType;
//import android.widget.LinearLayout;
//
//import com.dingshuwang.R;
//import com.dingshuwang.base.BaseActivity;
//import com.dingshuwang.model.BannerItem;
//import com.dingshuwang.util.UIUtil;
//import com.nostra13.universalimageloader.core.ImageLoader;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.concurrent.Executors;
//import java.util.concurrent.ScheduledExecutorService;
//import java.util.concurrent.TimeUnit;
//
///**
// * ViewPager实现的轮播图广告自定义视图，如京东首页的广告轮播图效果； 既支持自动轮播页面也支持手势滑动切换页面
// */
//
//public class SlideShowView extends FrameLayout {
//
//    // 轮播图图片数量
//    private final static int IMAGE_COUNT = 3;
//    // 自动轮播的时间间隔
//    private final static int TIME_INTERVAL = 3;
//    // 自动轮播启用开关
//    private static boolean isAutoPlay = true;
//
//    // 自定义轮播图的资源
//    private List<BannerItem.NewsItem> imageUrls;
//    // 放轮播图片的ImageView 的list
//    private List<ImageView> imageViewsList;
//    // 放圆点的View的list
//    private List<View> dotViewsList;
//
//    private ViewPager viewPager;
//    // 当前轮播页
//    private int currentItem = 0;
//    // 定时任务
//    private ScheduledExecutorService scheduledExecutorService;
//
//    private Context context;
//    private BaseActivity mActivity;
//
//    // Handler
//    private Handler handler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            try {
//                viewPager.setCurrentItem(currentItem);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    };
//
//    public SlideShowView(Context context) {
//        this(context, null);
//    }
//
//    public SlideShowView(Context context, AttributeSet attrs) {
//        this(context, attrs, 0);
//    }
//
//    public SlideShowView(Context context, AttributeSet attrs, int defStyle) {
//        super(context, attrs, defStyle);
//        this.context = context;
//        initData();
//        // if (isAutoPlay) {
//        startPlay();
//        // }
//    }
//
//    public void setImges(BaseActivity mActivity, List<BannerItem.NewsItem> imageUrls) {
//        try {
//            this.mActivity = mActivity;
//            if (null != this.imageUrls) {
//                this.imageUrls.clear();
//                this.imageUrls.addAll(imageUrls);
//            } else {
//                this.imageUrls = imageUrls;
//            }
//            initData();
//            System.gc();
//            initUI(context);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * 开始轮播图切换
//     */
//    public void startPlay() {
//        stopPlay();
//        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
//        scheduledExecutorService.scheduleAtFixedRate(new SlideShowTask(), 1, 3,
//                TimeUnit.SECONDS);
//    }
//
//    /**
//     * 停止轮播图切换
//     */
//    private void stopPlay() {
//        if (null != scheduledExecutorService) {
//            scheduledExecutorService.shutdown();
//        }
//    }
//
//    /**
//     * 初始化相关Data
//     */
//    private void initData() {
//        if (null == imageViewsList) {
//            imageViewsList = new ArrayList<ImageView>();
//        } else {
//            imageViewsList.clear();
//        }
//        if (null == dotViewsList) {
//            dotViewsList = new ArrayList<View>();
//        } else {
//            dotViewsList.clear();
//        }
//        // 一步任务获取图片
//        // new GetListTask().execute("");
//    }
//
//    /**
//     * 初始化Views等UI
//     */
//    MyPagerAdapter adapter = null;
//
//    private void initUI(Context context) {
//        try {
//            if (imageUrls == null || imageUrls.size() == 0)
//                return;
//
//            LayoutInflater.from(context).inflate(R.layout.layout_slideshow,
//                    this, true);
//
//            LinearLayout dotLayout = (LinearLayout) findViewById(R.id.dotLayout);
//            dotLayout.removeAllViews();
//
//            if (imageUrls.size() == 0) {
//                //商家默认图片配置
//                ImageView view = new ImageView(context);
////                view.setBackgroundResource(R.mipmap.ic_banner);
//                view.setScaleType(ScaleType.CENTER_CROP);
//                imageViewsList.add(view);
//            } else {
//                // 热点个数与图片特殊相等
//                for (int i = 0; i < imageUrls.size(); i++) {
//                    ImageView view = new ImageView(context);
//                    view.setTag(imageUrls.get(i));
//                    if (i == 0)// 给一个默认图
////                        view.setBackgroundResource(R.mipmap.ic_logo);
//                    view.setScaleType(ScaleType.CENTER_CROP);
//                    imageViewsList.add(view);
//
//                    ImageView dotView = new ImageView(context);
//                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
//                    params.leftMargin = 5;
//                    params.rightMargin = 5;
//                    dotLayout.addView(dotView, params);
//                    dotViewsList.add(dotView);
//                }
//            }
//
//            viewPager = (ViewPager) findViewById(R.id.viewPager);
//            viewPager.setFocusable(true);
//            if (null == adapter) {
//                adapter = new MyPagerAdapter();
//                viewPager.setAdapter(adapter);
//                viewPager.setOnPageChangeListener(new MyPageChangeListener());
//            } else {
//                adapter.notifyDataSetChanged();
//            }
////            adapter.notifyDataSetChanged();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * 填充ViewPager的页面适配器
//     */
//    private class MyPagerAdapter extends PagerAdapter {
//
//        @Override
//        public void destroyItem(View container, int position, Object object) {
//            // ((ViewPag.er)container).removeView((View)object);
//            try {
//                ((ViewPager) container)
//                        .removeView(imageViewsList.get(position));
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//
//        @Override
//        public Object instantiateItem(View container, final int position) {
//            final ImageView imageView = imageViewsList.get(position);
////            for (int i = 0; i < dotViewsList.size(); i++) {
////                if (i == position) {
////                    ((View) dotViewsList.get(position))
////                            .setBackgroundResource(R.mipmap.icon_point_pre);
////                } else {
////                    ((View) dotViewsList.get(i))
////                            .setBackgroundResource(R.mipmap.icon_point);
////                }
////            }
//            imageView.setScaleType(ScaleType.FIT_XY);
//
//            ImageLoader.getInstance().displayImage(imageUrls.get(position).img_url,imageView);
//
//            ((ViewPager) container).addView(imageViewsList.get(position));
//
//            imageView.setOnTouchListener(new OnTouchListener() {
//                @Override
//                public boolean onTouch(View v, MotionEvent event) {
//                    switch (event.getAction()) {
//                        case MotionEvent.ACTION_DOWN:
//                            isAutoPlay = false;
//                            break;
//                        case MotionEvent.ACTION_UP:
//                            isAutoPlay = true;
//                            break;
//                        case MotionEvent.ACTION_MOVE:
//                            isAutoPlay = false;
//                            break;
//                        default:
//                            break;
//                    }
//                    return false;
//                }
//            });
//
//            imageView.setOnClickListener(new OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (UIUtil.isfastdoubleClick()) {
//                        return;
//                    }
//
//                }
//
//
////                    switch (((CouponBannerInfo) v.getTag()).getId()) {
////                        case "0":
////                            Intent intent = new Intent();
////                            intent.setClass(context, SpecialPriceActivity.class);
////                            context.startActivity(intent);
////                            ((Activity) context).overridePendingTransition(R.anim.push_left_in,
////                                    R.anim.push_left_out);
////                            break;
////                        case "1":
////
////                            String json = mActivity.getPreferences().getString(PREF.PREF_HOME_SHAKE, null);
////                            ShakeOnProcessItem shakeOnProcessItem = GsonUtils.jsonToClass(json, ShakeOnProcessItem.class);
////                            ShakeActivity.actionShake(mActivity,shakeOnProcessItem.onProcessingShakeActivities.get(0).id);
////
////                            Intent intentShake = new Intent();
////                            intentShake.putExtra("id",shakeOnProcessItem.onProcessingShakeActivities.get(0).id);
////                            intentShake.setClass(context, ShakeActivity.class);
////                            context.startActivity(intentShake);
////                            ((Activity) context).overridePendingTransition(R.anim.push_left_in,
////                                    R.anim.push_left_out);
////                            break;
////                        case "2":
////                            break;
////                        case "3":
////                            Intent intentGepTicket = new Intent();
////                            intentGepTicket.setClass(context, GetTicketActivity.class);
////                            context.startActivity(intentGepTicket);
////                            ((Activity) context).overridePendingTransition(R.anim.push_left_in,
////                                    R.anim.push_left_out);
////                            break;
////                    }
////					Intent intent = new Intent();
////					intent.setClass(context, ShakeActivity.class);
////					context.startActivity(intent);
////					if (((CouponBannerInfo) imageView.getTag()).getFlag()
////							.equals("1")) {
////						Intent intent = new Intent();
////						if (MMApplication.mIsLogin) {
////							intent.setClass(context, MineInviteActivity.class);
////							context.startActivity(intent);
////						} else {
////							intent.setClass(context, MineLoginAct.class);
////							context.startActivity(intent);
////						}
////					} else {
////						final String url = ((CouponBannerInfo) imageView
////								.getTag()).getUrl();
////						final String title = ((CouponBannerInfo) imageView
////								.getTag()).getTitle();
////						if (!url.equals("#")) {
////							Intent intentAbout = new Intent(context,
////									HuiLifeHelpAct.class);
////							intentAbout.putExtra("h_title", title);
////							intentAbout.putExtra("h_url", url);
////							context.startActivity(intentAbout);
////						}
////					}
//            });
//            return imageViewsList.get(position);
//        }
//
//        @Override
//        public int getCount() {
//            return imageViewsList.size();
//        }
//
//        @Override
//        public boolean isViewFromObject(View arg0, Object arg1) {
//            return arg0 == arg1;
//        }
//
//        @Override
//        public void restoreState(Parcelable arg0, ClassLoader arg1) {
//
//        }
//
//        @Override
//        public Parcelable saveState() {
//            return null;
//        }
//
//        @Override
//        public void startUpdate(View arg0) {
//
//        }
//
//        @Override
//        public void finishUpdate(View arg0) {
//
//        }
//
//    }
//
//    /**
//     * ViewPager的监听器 当ViewPager中页面的状态发生改变时调用
//     */
//    private class MyPageChangeListener implements OnPageChangeListener {
//
//        boolean isAutoPlay = false;
//
//        @Override
//        public void onPageScrollStateChanged(int arg0) {
//            switch (arg0) {
//                case 1:// 手势滑动，空闲中
//                    isAutoPlay = true;
//                    break;
//                case 2:// 界面切换中
//                    isAutoPlay = true;
//                    break;
//                case 0:// 滑动结束，即切换完毕或者加载完毕
//                    // 当前为最后一张，此时从右向左滑，则切换到第一张
//                    try {
//                        if (viewPager.getCurrentItem() == viewPager.getAdapter()
//                                .getCount() - 1 && !isAutoPlay) {
//                            viewPager.setCurrentItem(0);
//                        }
//                        // 当前为第一张，此时从左向右滑，则切换到最后一张
//                        else if (viewPager.getCurrentItem() == 0 && !isAutoPlay) {
//                            viewPager.setCurrentItem(viewPager.getAdapter()
//                                    .getCount() - 1);
//                        }
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                    break;
//            }
//        }
//
//        @Override
//        public void onPageScrolled(int arg0, float arg1, int arg2) {
//
//        }
//
//        @Override
//        public void onPageSelected(int pos) {
//            //TODO 轮播焦点修改
//            try {
//                currentItem = pos;
//                for (int i = 0; i < dotViewsList.size(); i++) {
//                    if (i == pos) {
//                        ((View) dotViewsList.get(pos))
//                                .setBackgroundResource(R.mipmap.icon_point_pre);
//                    } else {
//                        ((View) dotViewsList.get(i))
//                                .setBackgroundResource(R.mipmap.icon_point);
//                    }
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    /**
//     * 执行轮播图切换任务
//     */
//    private class SlideShowTask implements Runnable {
//
//        @Override
//        public void run() {
//            synchronized (viewPager) {
//                currentItem = (currentItem + 1) % imageViewsList.size();
//                handler.obtainMessage().sendToTarget();
//            }
//        }
//    }
//
//    /**
//     * 销毁ImageView资源，回收内存
//     */
//    private void destoryBitmaps() {
//
//        for (int i = 0; i < IMAGE_COUNT; i++) {
//            ImageView imageView = imageViewsList.get(i);
//            Drawable drawable = imageView.getDrawable();
//            if (drawable != null) {
//                // 解除drawable对view的引用
//                drawable.setCallback(null);
//            }
//        }
//    }
//
//    /**
//     * 异步任务,获取数据
//     */
//    private String msg_result = "";
//
//    class GetListTask extends AsyncTask<String, Integer, Boolean> {
//
//        @Override
//        protected Boolean doInBackground(String... params) {
//            try {
//                // 这里一般调用服务端接口获取一组轮播图片，下面是从百度找的几个图片
////				msg_result = HuiLifeAPI.get_request(API.advertisingPromotion,
////						false, context,
////						SharedPre.getCityInfo(context, ShareKey.cityCode));
//
//                return true;
//            } catch (Exception e) {
//                e.printStackTrace();
//                return false;
//            }
//        }
//
//        @Override
//        protected void onPostExecute(Boolean result) {
//            super.onPostExecute(result);
//            if (result) {
//                System.gc();
//
//                initUI(context);
//            }
//        }
//    }
//}