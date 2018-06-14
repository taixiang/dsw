/**
 * @file XListView.java
 * @package me.maxwin.view
 * @create Mar 18, 2012 6:28:41 PM
 * @author Maxwin
 * @description An ListView support (a) Pull down to refresh, (b) Pull up to load more.
 * 		Implement IXListViewListener, and see stopRefresh() / stopLoadMore().
 */
package com.dingshuwang.view;


import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.animation.DecelerateInterpolator;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Scroller;
import android.widget.TextView;

import com.dingshuwang.R;
import com.dingshuwang.base.MMApplication;
import com.dingshuwang.util.UIUtil;


public class XListView extends ListView implements OnScrollListener {

	private float mLastY = -1; // save event y
	private Scroller mScroller; // used for scroll back
	private OnScrollListener mScrollListener; // user's scroll listener

	// the interface to trigger refresh and load more.
	private IXListViewListener mListViewListener;

	// -- header view
	private XListViewHeader mHeaderView;
	// header view content, use it to calculate the Header's height. And hide it
	// when disable pull refresh.
	private RelativeLayout mHeaderViewContent;
	private TextView mHeaderTimeView;
	private int mHeaderViewHeight; // header view's height
	private boolean mEnablePullRefresh = true;
	private boolean mPullRefreshing = false; // is refreashing.

	// -- footer view
	private XListViewFooter mFooterView;
	private boolean mEnablePullLoad;
	private boolean mPullLoading;
	private boolean mIsFooterReady = false;

	// total list items, used to detect is at the bottom of listview.
	private int mTotalItemCount;

	// for mScroller, scroll back from header or footer.
	private int mScrollBack;
	private final static int SCROLLBACK_HEADER = 0;
	private final static int SCROLLBACK_FOOTER = 1;

	private final static int SCROLL_DURATION = 400; // scroll back duration
	private final static int PULL_LOAD_MORE_DELTA = 20; // when pull up >= 50px
														// at bottom, trigger
														// load more.
	private final static float OFFSET_RADIO = 1.8f; // support iOS like pull
													// feature.

	private Context mContext;
	private boolean isLoadMore = false;

	public boolean ismPullLoading() {
		return mPullRefreshing;
	}

	public boolean isLoadMore() {
		return isLoadMore;
	}

	public void setLoadMore(boolean isLoadMore) {
		this.isLoadMore = isLoadMore;
	}

	
	public XListViewHeader getmHeaderView() {
		return mHeaderView;
	}

	/**
	 * @param context
	 */
	public XListView(Context context) {
		super(context);
		mContext = context;
		initWithContext(context, isLoadMore);
	}

	public XListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// mGestureDetector = new GestureDetector(new YScrollDetector());
		// setFadingEdgeLength(0);
		mContext = context;
		initWithContext(context, isLoadMore);
	}

	public XListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		mContext = context;
		initWithContext(context, isLoadMore);
	}

	class YScrollDetector extends SimpleOnGestureListener {
		@Override
		public boolean onScroll(MotionEvent e1, MotionEvent e2,
				float distanceX, float distanceY) {
			if (distanceY != 0 && distanceX != 0) {
				return false;
			}
			if (Math.abs(distanceY) >= Math.abs(distanceX)) {
				return true;
			}
			return false;
		}
	}

	private void initWithContext(Context context, boolean isLoadMore) {
		try {
			mScroller = new Scroller(context, new DecelerateInterpolator());
			// XListView need the scroll event, and it will dispatch the event
			// to
			// user's listener (as a proxy).
			super.setOnScrollListener(this);

			// init header view
			mHeaderView = new XListViewHeader(context);
			mHeaderViewContent = (RelativeLayout) mHeaderView
					.findViewById(R.id.xlistview_header_content);
			mHeaderTimeView = (TextView) mHeaderView
					.findViewById(R.id.xlistview_header_time);
			addHeaderView(mHeaderView);

			// init footer view
			mFooterView = new XListViewFooter(context, isLoadMore);

			// init header height
			mHeaderView.getViewTreeObserver().addOnGlobalLayoutListener(
					new OnGlobalLayoutListener() {
						@Override
						public void onGlobalLayout() {
							mHeaderViewHeight = mHeaderViewContent.getHeight();
							getViewTreeObserver().removeGlobalOnLayoutListener(
									this);
						}
					});
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void setAdapter(ListAdapter adapter) {
		// make sure XListViewFooter is the last footer view, and only add once.
		try {
			if (mIsFooterReady == false) {
				mIsFooterReady = true;
				addFooterView(mFooterView);
			}
			super.setAdapter(adapter);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void showFooter() {
		removeFooterView(mFooterView);
		addFooterView(mFooterView);
		mFooterView.show();
		setPullLoadEnable(true);
		mFooterView.setVisibility(View.VISIBLE);
	}

	public void goneFooter() {
		try {
			removeFooterView(mFooterView);
			mFooterView.hide();
			setPullLoadEnable(false);
			mFooterView.setVisibility(View.GONE);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@SuppressLint("NewApi")
	public void showHeader() {
		try {
			removeHeaderView(mHeaderView);
			addHeaderView(mHeaderView);
			setPullRefreshEnable(true);
			mHeaderView.setActivated(true);
			mHeaderView.setVisibility(View.VISIBLE);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@SuppressLint("NewApi")
	public void goneHeader() {
		removeHeaderView(mFooterView);
		setPullRefreshEnable(false);
		mHeaderView.setActivated(false);
		mHeaderView.removeAllViews();
		mHeaderView.setVisibility(View.GONE);
	}

	/**
	 * enable or disable pull down refresh feature.
	 * 
	 * @param enable
	 */
	public void setPullRefreshEnable(boolean enable) {
		try {
			mEnablePullRefresh = enable;
			if (!mEnablePullRefresh) { // disable, hide the content
				mHeaderViewContent.setVisibility(View.INVISIBLE);
			} else {
				mHeaderViewContent.setVisibility(View.VISIBLE);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * enable or disable pull up load more feature.
	 * 
	 * @param enable
	 */
	public void setPullLoadEnable(boolean enable) {
		try {
			mEnablePullLoad = enable;
			if (!mEnablePullLoad) {
				mFooterView.hide();
				mFooterView.setOnClickListener(null);
			} else {
				mPullLoading = false;
				mFooterView.show();
				mFooterView.setState(XListViewFooter.STATE_NORMAL);
				// both "pull up" and "click" will invoke load more.
				mFooterView.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						startLoadMore();
					}
				});
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * stop refresh, reset header view.
	 */
	public void stopRefresh() {
		try {
			if (mPullRefreshing == true) {
				mPullRefreshing = false;
				resetHeaderHeight();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * stop load more, reset footer view.
	 */
	public void stopLoadMore() {
		try {
			if (mPullLoading == true) {
				mPullLoading = false;
				mFooterView.setState(XListViewFooter.STATE_NORMAL);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * set last refresh time
	 * 
	 * @param time
	 */
	public void setRefreshTime(String time) {
		try {
			mHeaderTimeView.setText(time);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void invokeOnScrolling() {
		try {
			if (mScrollListener instanceof OnXScrollListener) {
				OnXScrollListener l = (OnXScrollListener) mScrollListener;
				l.onXScrolling(this);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void updateHeaderHeight(float delta) {
		try {
			mHeaderView.setVisiableHeight((int) delta
					+ mHeaderView.getVisiableHeight());
			if (mEnablePullRefresh && !mPullRefreshing) { // 未处于刷新状态，更新箭头
				if (mHeaderView.getVisiableHeight() > mHeaderViewHeight) {
					mHeaderView.setState(XListViewHeader.STATE_READY);
				} else {
					mHeaderView.setState(XListViewHeader.STATE_NORMAL);
				}
			}
			setSelection(0); // scroll to top each time
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * reset header view's height.
	 */
	private void resetHeaderHeight() {
		try {
			int height = mHeaderView.getVisiableHeight();
			if (height == 0) // not visible.
				return;
			// refreshing and header isn't shown fully. do nothing.
			if (mPullRefreshing && height <= mHeaderViewHeight) {
				return;
			}
			int finalHeight = 0; // default: scroll back to dismiss header.
			// is refreshing, just scroll back to show all the header.
			if (mPullRefreshing && height > mHeaderViewHeight) {
				finalHeight = mHeaderViewHeight;
			}
			mScrollBack = SCROLLBACK_HEADER;
			mScroller.startScroll(0, height, 0, finalHeight - height,
					SCROLL_DURATION);
			// trigger computeScroll
			invalidate();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void updateFooterHeight(float delta) {
		try {
			int height = (int) delta;
			// mFooterView.getBottomMargin() + (int) delta;
			if (mEnablePullLoad && !mPullLoading) {
				if (height > PULL_LOAD_MORE_DELTA) { // height enough to invoke
														// load
														// more.
					mFooterView.setState(XListViewFooter.STATE_READY);
				} else {
					mFooterView.setState(XListViewFooter.STATE_NORMAL);
				}
			}
			mFooterView.setBottomMargin(height);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// setSelection(mTotalItemCount - 1); // scroll to bottom
	}

	private void resetFooterHeight() {
		try {
			int bottomMargin = mFooterView.getBottomMargin();
			if (bottomMargin > 0) {
				mScrollBack = SCROLLBACK_FOOTER;
				mScroller.startScroll(0, bottomMargin, 0, -bottomMargin,
						SCROLL_DURATION);
				invalidate();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void startLoadMore() {
		try {
			mPullLoading = true;
			mFooterView.setState(XListViewFooter.STATE_LOADING);
			if (mListViewListener != null) {
				// by steven when on network dealwith
				if (UIUtil.isConnect(mContext)) {
					mListViewListener.onLoadMore();
				} else {
					MMApplication.setNetWork(mContext);
					stopLoadMore();
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		try {
			if (mLastY == -1) {
				mLastY = ev.getRawY();
			}
			switch (ev.getAction()) {
			case MotionEvent.ACTION_DOWN:
				mLastY = ev.getRawY();
				break;
			case MotionEvent.ACTION_MOVE:
				final float deltaY = ev.getRawY() - mLastY;
				mLastY = ev.getRawY();
				if (getFirstVisiblePosition() == 0
						&& (mHeaderView.getVisiableHeight() > 0 || deltaY > 0)) {
					// the first item is showing, header has shown or pull down.
					updateHeaderHeight(deltaY / OFFSET_RADIO);
					invokeOnScrolling();
				} else if (getLastVisiblePosition() == mTotalItemCount - 1
						&& (mFooterView.getBottomMargin() > 0 || deltaY < 0)) {
					// last item, already pulled up or want to pull up.
					updateFooterHeight(-deltaY / OFFSET_RADIO);
				}
				break;
			default:
				mLastY = -1; // reset
				if (getFirstVisiblePosition() == 0) {
					// invoke refresh
					if (mEnablePullRefresh
							&& mHeaderView.getVisiableHeight() > mHeaderViewHeight) {
						mPullRefreshing = true;
						mHeaderView.setState(XListViewHeader.STATE_REFRESHING);
						if (mListViewListener != null) {
							// by steven when on network dealwith
							if (UIUtil.isConnect(mContext)) {
								mListViewListener.onRefresh();
							} else {
								// set network stopRefreh
								MMApplication.setNetWork(mContext);
								stopRefresh();
							}
						}
					}
					resetHeaderHeight();
				} else if (getLastVisiblePosition() == mTotalItemCount - 1) {
					// invoke load more.
					if (mEnablePullLoad
							&& mFooterView.getBottomMargin() > PULL_LOAD_MORE_DELTA) {
						startLoadMore();
					}
					resetFooterHeight();
				}
				break;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			return super.onTouchEvent(ev);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public void computeScroll() {
		try {
			if (mScroller.computeScrollOffset()) {
				if (mScrollBack == SCROLLBACK_HEADER) {
					mHeaderView.setVisiableHeight(mScroller.getCurrY());
				} else {
					mFooterView.setBottomMargin(mScroller.getCurrY());
				}
				postInvalidate();
				invokeOnScrolling();
			}
			super.computeScroll();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void setOnScrollListener(OnScrollListener l) {
		mScrollListener = l;
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		if (mScrollListener != null) {
			mScrollListener.onScrollStateChanged(view, scrollState);
		}
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		// send to user's listener
		try {
			mTotalItemCount = totalItemCount;
			if (mScrollListener != null) {
				mScrollListener.onScroll(view, firstVisibleItem,
						visibleItemCount, totalItemCount);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void setXListViewListener(IXListViewListener l) {
		mListViewListener = l;
	}

	/**
	 * you can listen ListView.OnScrollListener or this one. it will invoke
	 * onXScrolling when header/footer scroll back.
	 */
	public interface OnXScrollListener extends OnScrollListener {
		public void onXScrolling(View view);
	}

	/**
	 * implements this interface to get refresh/load more event.
	 */
	public interface IXListViewListener {
		public void onRefresh();

		public void onLoadMore();
	}

	// 滑动距离及坐标
	private float xDistance, yDistance, xLast, yLast;

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		try {
			switch (ev.getAction()) {
			case MotionEvent.ACTION_DOWN:
				xDistance = yDistance = 0f;
				xLast = ev.getX();
				yLast = ev.getY();
				break;
			case MotionEvent.ACTION_MOVE:
				final float curX = ev.getX();
				final float curY = ev.getY();

				xDistance += Math.abs(curX - xLast);
				yDistance += Math.abs(curY - yLast);
				xLast = curX;
				yLast = curY;

				if (xDistance > yDistance) {
					return false; // 表示向下传递事件
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return super.onInterceptTouchEvent(ev);
	}
}
