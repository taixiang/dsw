/**
 * @file XListViewHeader.java
 * @create Apr 18, 2012 5:22:27 PM
 * @author Maxwin
 * @description XListView's header
 */
package com.dingshuwang.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dingshuwang.R;


public class XListViewHeader extends LinearLayout {
	private LinearLayout mContainer;
	private ProgressBar mArrowImageView;
	private RotateAnimation mRotateDownAnim;
	private ProgressBar mProgressBar;
	private TextView mHintTextView;
	private int mState = STATE_NORMAL;

	private final int ROTATE_ANIM_DURATION = 0;

	public final static int STATE_NORMAL = 0;
	public final static int STATE_READY = 1;
	public final static int STATE_REFRESHING = 2;

	public XListViewHeader(Context context) {
		super(context);
		initView(context);
	}
	
	public int getmState() {
		return mState;
	}

	/**
	 * @param context
	 * @param attrs
	 */
	public XListViewHeader(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context);
	}

	private void initView(Context context) {
		try {
			// 初始情况，设置下拉刷新view高度为0
			LayoutParams lp = new LayoutParams(
					LayoutParams.FILL_PARENT, 0);
			mContainer = (LinearLayout) LayoutInflater.from(context).inflate(
					R.layout.xlistview_header, null);
			addView(mContainer, lp);
			setGravity(Gravity.BOTTOM);

			mArrowImageView = (ProgressBar) findViewById(R.id.xlistview_header_arrow);
//			xlistview_header_img = (ImageView) findViewById(R.id.xlistview_header_img);
			mHintTextView = (TextView) findViewById(R.id.xlistview_header_hint_textview);
			mProgressBar = (ProgressBar) findViewById(R.id.xlistview_header_progressbar);

			// mRotateUpAnim = new RotateAnimation(0.0f, 0.0f,
			// Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
			// 0.5f);
			// mRotateUpAnim.setDuration(ROTATE_ANIM_DURATION);
			// mRotateUpAnim.setFillAfter(true);
			// mRotateUpAnim.setRepeatCount(0);
			//
			try {
				// if (mRotateDownAnim == null)
				// mRotateDownAnim = new RotateAnimation(0.0f, 0.0f,
				// Animation.RELATIVE_TO_SELF, 0.5f,
				// Animation.RELATIVE_TO_SELF, 0.5f);
				// mRotateDownAnim.setDuration(ROTATE_ANIM_DURATION);
				// mRotateDownAnim.setFillAfter(true);
				// mRotateDownAnim.setInterpolator(context,
				// interpolator.accelerate_cubic);
				// mRotateDownAnim.setRepeatCount(0);

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void showAninmial() {
		// TODO Auto-generated method stub
		try {
//			xlistview_header_img.setVisibility(View.VISIBLE);
//			AnimationDrawable animationDrawable = (AnimationDrawable) mArrowImageView
//					.getBackground();
//			animationDrawable.start();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void setState(int state) {
		try {
			if (state == mState)
				return;
			if (state == STATE_REFRESHING) { // 显示进度
//				xlistview_header_img.setVisibility(View.INVISIBLE);
				mArrowImageView.setVisibility(View.VISIBLE);
			} else { // 显示箭头图片
				showAninmial();
				// xlistview_header_img.setVisibility(View.VISIBLE);
				mArrowImageView.setVisibility(View.INVISIBLE);
			}
			switch (state) {
			case STATE_NORMAL:
				if (mState == STATE_READY) {
					mArrowImageView.clearAnimation();
					// mArrowImageView.startAnimation(mRotateDownAnim);
					showAninmial();
				}
				if (mState == STATE_REFRESHING) {
					mArrowImageView.clearAnimation();
				}
				mHintTextView.setText(R.string.xlistview_header_hint_normal);
				break;
			case STATE_READY:
				if (mState != STATE_READY) {
					// mArrowImageView.clearAnimation();
					// mArrowImageView.startAnimation(mRotateDownAnim);
					showAninmial();
					mHintTextView.setText(R.string.xlistview_header_hint_ready);
				}
				break;
			case STATE_REFRESHING:
				// mArrowImageView.startAnimation(mRotateUpAnim);
				// mArrowImageView.startAnimation(mRotateDownAnim);
				// showAninmial();
				mHintTextView.setText(R.string.xlistview_header_hint_loading);
				break;
			default:
			}
			mState = state;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void setVisiableHeight(int height) {
		if (height < 0)
			height = 0;
		LayoutParams lp = (LayoutParams) mContainer
				.getLayoutParams();
		lp.height = height;
		mContainer.setLayoutParams(lp);
	}

	public int getVisiableHeight() {
		return mContainer.getHeight();
	}

}
