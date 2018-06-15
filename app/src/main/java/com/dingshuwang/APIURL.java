package com.dingshuwang;

/**
 * Created by Steven on 2015/9/10 0010.
 */
public class APIURL {

    public static final String URL = "http://m.iisbn.com/";
    public static final String BASE_API_URL ="http://www.iisbn.com/api/";


    //首页轮播
   public static final String BANNER_URL = BASE_API_URL + "Default.ashx?action=news_side";

    //主编推荐http://localhost:8081/API/Default.ashx?action=recommend
    public static final String RECOMMEND_URL = BASE_API_URL + "Default.ashx?action=recommend";

    //最新上架http://localhost:8081/API/Default.ashx?action=book_is_top
    public static final String NEW_BOOK_URL = BASE_API_URL + "Default.ashx?action=book_is_top";

    //书商供货http://localhost:8081/API/Default.ashx?action=supplier_book
    public static final String SUPPLY_BOOK_URL = BASE_API_URL +"Default.ashx?action=supplier_book";


    //求购
    public static final String purchase = BASE_API_URL +"users.ashx?action=trade&user_id=%s&isbn=%s&pro_name=%s&author=%s&publishing=%s&price_market=%s&price_sell=%s&quantity=%s&stuff_status=%s&remark=%s&trade_type=%s";

    //http://localhost:8081/API/users.ashx?action=check_isbn&user_id=1&isbn=9787030263377
    public static final String check_id = BASE_API_URL + "users.ashx?action=check_isbn&user_id=%s&isbn=%s";

    //http://localhost:8081/API/users.ashx?action=is_purchase_rate&user_id=1&isbn=9787030263377
    public static final String isPurchase =  BASE_API_URL + "users.ashx?action=is_purchase_rate&user_id=%s&isbn=%s";

    //
    public static final String photo_url = "http://www.iisbn.com/API/images_app_upload";


    //订单详情
    public static final String ORDER_DETAIL = BASE_API_URL +"orders.ashx?action=orders&user_id=%s&order_id=%s";

    //修改密码http://localhost:8081/API/users.ashx?action=update_passwors&user_id=1&orig_password=123456&confirm_password=654789
    public static final String modify_password =BASE_API_URL + "users.ashx?action=update_passwors&user_id=%s&orig_password=%s&confirm_password=%s";

    //修改姓名http://localhost:8081/API/users.ashx?action=member_edit&user_id=1&member_truename=周文凯
    public static final String modify_name =BASE_API_URL + "users.ashx?action=member_edit&user_id=%s&member_truename=%s";

    //待付款数量
    public static final String Pay_count = BASE_API_URL + "orders.ashx?action=orders_count&user_id=%s&order_type=wating_pay_order";

    public static final String two_count = BASE_API_URL + "orders.ashx?action=orders_count&user_id=%s&order_type=wating_exp_order";

    public static final String three_count = BASE_API_URL + "orders.ashx?action=orders_count&user_id=%s&order_type=for_exp_order";

    public static final String four_count = BASE_API_URL + "orders.ashx?action=orders_count&user_id=%s&order_type=complete_order";



    //action=pay_success
    public static final String Pay_Success = BASE_API_URL + "orders.ashx?action=pay_success&user_id=%s&out_trade_no=%s&trade_no=%s&trade_status=success";

    public static final String Point_detail = BASE_API_URL + "users.ashx?action=point_list&user_id=%s";



    //注册
    public static final String REGISTER_URL = BASE_API_URL + "users.ashx?action=reg_user&user_name=%s&passwords=%s&device_token=%s";

    //登录
    public static final String LOGIN_URL = BASE_API_URL + "users.ashx?action=check_user&user_name=%s&passwords=%s&device_token=%s";


    //中间区域 （小说专区）
    public static final String Home_Middle = BASE_API_URL + "Default.ashx?action=default_middle";
    //享图书
    public static final String HOME_SHARE = BASE_API_URL + "Default.ashx?action=default_share";
    //免费领取
    public static final String HOME_FREE = BASE_API_URL + "Default.ashx?action=default_free";
    //底部图片
    public static final String HOME_BOTTOM = BASE_API_URL + "Default.ashx?action=default_bottom";

    //小说专区
    public static final String NOVEL_URL = BASE_API_URL + "pro.ashx?action=novel&page=%s";

    //搜索
    public static final String SEARCH_URL = BASE_API_URL +"pro.ashx?action=search&keywords=%s&pagesize=15&page=%s";

    //高级搜索
    public static final String SEARCH_ADVANCE = BASE_API_URL + "pro.ashx?action=search_advanced&bookname=%s&isbn=%s&author=%s&publishing=%s";

    //发现
    public  static  final String FIND = URL + "Handler/List.ashx?action=findBookListAPI&keywords=%s&pageIndex=%s&rnd=%s";

    //发布http://localhost:8081/API/users.ashx?action=trade
    public static final String publish = BASE_API_URL +"users.ashx?action=trade&user_id=%s&isbn=%s&pro_name=%s&author=%s&publishing=%s&price_market=%s&price_sell=%s&quantity=%s&stuff_status=%s&remark=%s&link_name=%s&link_phone=%s&university=%s&location=%s";


    //详情
    public static final String DETAIL_URL = BASE_API_URL +"pro.ashx?action=details&pro_id=%s";

    //商品详情团购批发API
    public static final String DETAIL_GROUPINFO = BASE_API_URL +"pro.ashx?action=detailsGrouponInfor";

    //商品评价
    public static final  String Comment_url = BASE_API_URL + "pro.ashx?action=comment&pro_id=%s";

    //加入购物车
    public static final String CART_ADD_URL = BASE_API_URL + "buy.ashx?action=add_cart&user_id=%s&pro_id=%s&order_count=%s";

    //收藏
    public static final String COLLECT_URL = BASE_API_URL + "users.ashx?action=add_action&user_id=%s&pro_id=%s";
    //取消收藏
    public static final String COLLECT_CANCEL_URL = BASE_API_URL + "users.ashx?action=add_action_del&user_id=%s&pro_id=%s";

    //购物车列表
    public static final String GOODS_LIST = BASE_API_URL + "buy.ashx?action=user_cart&user_id=%s";

    //删除
    public static final String DELET_GOOD = BASE_API_URL+"buy.ashx?action=drop_cart&user_id=%s&pro_id=%s";

    //条形码扫描
    public static final String CODE_ISBN = BASE_API_URL +"pro.ashx?action=isbn_code&isbn=%s";

    //地址列表
    public static final String ADDRESS_LIST = BASE_API_URL + "users.ashx?action=address_list&user_id=%s";

    //获取城市
    public static final String CITY_URL = BASE_API_URL+"users.ashx?action=province_city";

    //地址详情
    public static final String ADDRESS_INFO_URL = BASE_API_URL +"users.ashx?action=address_infor&user_id=%s&address_id=%s";

    //设置默认地址
    public static final String DEFAULT_ADDRESS_URL = BASE_API_URL + "users.ashx?action=address_set_default&user_id=%s&address_id=%s";

    //添加地址
    public static final String ADD_ADDRESS = BASE_API_URL +"users.ashx?action=address_edit&operation=add&user_id=%s&true_name=%s&area_id=%s&address=%s&phone=%s";

    //编辑地址
    public static final String EDIT_ADDRESS = BASE_API_URL +"users.ashx?action=address_edit&operation=add&user_id=%s&address_id=%s&true_name=%s&area_id=%s&address=%s&phone=%s";


    //删除地址
    public static final String DELETE_ADDRESS = BASE_API_URL + "users.ashx?action=address_edit&user_id=%s&operation=delete&address_id=%s";

    //个人中心
    public static final String USER_INFO_URL = BASE_API_URL + "users.ashx?action=details&user_id=%s";

    //签到
    public static final String sign_in_url = BASE_API_URL + "users.ashx?action=point_sign_in&user_id=%s";

    //优惠券
    public static final String coupons_url = BASE_API_URL + "users.ashx?action=coupons&user_id=%s";
    //我的积分
    public static final String point =BASE_API_URL + "users.ashx?action=point_list&user_id=%s";

    //收藏列表
    public static final String COLLECT_LIST_URL = BASE_API_URL + "users.ashx?action=favorites_list&user_id=%s";

    //配送费
    public static final String Supplier_URL = "http://www.iisbn.com/Handler/supplier.ashx?action=suplier_exp&supplier_id=%s&supplier_exp=%s&area_id=%s&user_id=%s";



  //提交订单http://localhost:8081/API/orders.ashx?action=create_order&user_id=1&address_id=44&exp_id=2&express_id=6&invoice_innerid=""&remark=订单备注
  public static final String ConfirmOrder_url = BASE_API_URL +"orders.ashx?action=create_order&user_id=%s&address_id=%s&exp_id=%s&express_id=%s&invoice_innerid=%s&point=%s&coupons=%s&remark=%s&order_type=%s";


  //待付款http://localhost:8081/API/orders.ashx?action=user_orders&user_id=1&order_type=wating_pay_order
  public static final String TOPAYURL = BASE_API_URL + "orders.ashx?action=user_orders&user_id=%s&order_type=wating_pay_order";

  //等待发货
  public static final String WAIT_ORDER = BASE_API_URL + "orders.ashx?action=user_orders&user_id=%s&order_type=wating_exp_order";

  //待收货http://localhost:8081/API/orders.ashx?action=user_orders&user_id=1&order_type=for_exp_order
  public static final String FOR_EXP = BASE_API_URL + "orders.ashx?action=user_orders&user_id=%s&order_type=for_exp_order";

  //交易完成http://localhost:8081/API/orders.ashx?action=user_orders&user_id=1&order_type=complete_order
  public static final String complete_url = BASE_API_URL + "orders.ashx?action=user_orders&user_id=%s&order_type=complete_order";

 //我的全部订单
 public static final String ALL_ORDER_URL = BASE_API_URL + "orders.ashx?action=user_orders&user_id=%s";

  //我的发布
  public static final String MY_PUBLISH = BASE_API_URL + "users.ashx?action=publish_list&user_id=%s";

  //http://www.iisbn.com/api/pro.ashx?action=scancode_waitmsg&isbn=9787810666398
  public static final String SCANCODE = BASE_API_URL + "pro.ashx?action=scancode_waitmsg&isbn=%s";
}