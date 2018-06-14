package com.dingshuwang.view;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dingshuwang.R;


/**
 * 自定义 dialog
 * 
 * @author Davis
 * 
 */
public class AlertDialogUI {
	Context context;
	android.app.AlertDialog ad;
	TextView titleView;
	TextView messageView;
	TextView btn_sure, btn_cancle;

	public AlertDialogUI(Context context) {

		// TODO Auto-generated constructor stub
		try {
			this.context = context;
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			if (ad == null) {
				ad = new android.app.AlertDialog.Builder(context).create();
			}
			if (!ad.isShowing()) {
				ad.show();
			}
			// 关键在下面的两行,使用window.setContentView,替换整个对话框窗口的布局
			// Window window = ad.getWindow();
			// window.setContentView(R.layout.alertdialog);
			View layout = inflater.inflate(R.layout.alertdialog, null);

			Window dialogWindow = ad.getWindow();
			DisplayMetrics metrics = new DisplayMetrics();
			((Activity) context).getWindowManager().getDefaultDisplay()
					.getMetrics(metrics);
			int width = (int) ((metrics.widthPixels) * 0.8);
			WindowManager.LayoutParams layoutParams = ad.getWindow()
					.getAttributes();
			layoutParams.width = width;
			dialogWindow.setAttributes(layoutParams);

			ad.setContentView(layout, new LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.WRAP_CONTENT,
					LinearLayout.LayoutParams.WRAP_CONTENT));

			titleView = (TextView) layout.findViewById(R.id.title);
			messageView = (TextView) layout.findViewById(R.id.message);
			btn_sure = (TextView) layout.findViewById(R.id.btn_sure);
			btn_cancle = (TextView) layout.findViewById(R.id.btn_cancle);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void showDialog() {
		if (null != ad && !ad.isShowing()) {
			ad.show();
		}
	}

	public void setDisMissListener() {

	}

	public void setTitle(int resId) {
		titleView.setVisibility(View.VISIBLE);
		titleView.setText(resId);
	}

	public void setTitle(String title) {
		try {
			titleView.setVisibility(View.VISIBLE);
			titleView.setText(title);
		}catch (Exception e){

		}
	}

	public void setMessage(int resId) {
		messageView.setText(resId);
	}

	public void setMessage(String message) {
		try {
			messageView.setText(message);
		}catch (Exception e){

		}
	}

	public void setCanceledOnTouchOutside(boolean flag) {
		ad.setCanceledOnTouchOutside(flag);
	}

	public void setCancleAble(boolean flag) {
		ad.setCancelable(flag);
	}

	/**
	 * 设置按钮
	 * 
	 * @param text
	 * @param listener
	 */
	public void setPositiveButton(String text,
			final View.OnClickListener listener) {
		btn_sure.setText(text);
		btn_sure.setOnClickListener(listener);
	}

	/**
	 * 设置按钮
	 * 
	 * @param text
	 * @param listener
	 */
	public void setNegativeButton(String text,
			final View.OnClickListener listener) {
		btn_cancle.setText(text);
		btn_cancle.setVisibility(View.VISIBLE);
		btn_cancle.setOnClickListener(listener);
	}

	/**
	 * 关闭对话框
	 */
	public void dismiss() {
		try {
			ad.dismiss();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

}