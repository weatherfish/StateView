package com.github.nukc.stateview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.v4.view.LayoutInflaterCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.RelativeLayout;

/**
 * Created by C on 4/3/2016.
 * https://github.com/nukc
 */
public class StateView extends View {

    private int mEmptyResource;
    private int mRetryResource;
    private int mLoadingResource;
    private int mEmptyViewId;
    private int mRetryViewId;
    private int mLoadingViewId;

    private View mEmptyView;
    private View mRetryView;
    private View mLoadingView;

    private LayoutInflater mInflater;
    private OnRetryClickListener mRetryClickListener;

    private RelativeLayout.LayoutParams mLayoutParams;

    public StateView(Context context) {
        this(context, null);
    }

    public StateView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StateView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        if (attrs == null) {
            mLayoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
        } else {
            mLayoutParams = new RelativeLayout.LayoutParams(context, attrs);
        }

        final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.StateView);
        mEmptyResource = a.getResourceId(R.styleable.StateView_emptyResource, 0);
        mRetryResource = a.getResourceId(R.styleable.StateView_retryResource, 0);
        mLoadingResource = a.getResourceId(R.styleable.StateView_loadingResource, 0);
        mEmptyViewId = a.getResourceId(R.styleable.StateView_emptyViewId, NO_ID);
        mRetryViewId = a.getResourceId(R.styleable.StateView_retryViewId, NO_ID);
        mLoadingViewId = a.getResourceId(R.styleable.StateView_loadingViewId, NO_ID);
        a.recycle();

        if (mEmptyResource == 0){
            mEmptyResource = R.layout.view_empty;
        }
        if (mRetryResource == 0){
            mRetryResource = R.layout.view_retry;
        }
        if (mLoadingResource == 0) {
            mLoadingResource = R.layout.view_loading;
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(0, 0);
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void draw(Canvas canvas) {
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
    }

    @Override
    public void setVisibility(int visibility) {
        setVisibility(mEmptyView, visibility);
        setVisibility(mRetryView, visibility);
        setVisibility(mLoadingView, visibility);
    }

    private void setVisibility(View view, int visibility){
        if (view != null)
            view.setVisibility(visibility);
    }

    public void showContent(){
       setVisibility(GONE);
    }

    public View showEmpty(){
        if (mEmptyView == null)
            mEmptyView = inflate(mEmptyResource, mEmptyViewId);

        showView(mEmptyView);
        return mEmptyView;
    }

    public View showRetry(){
        if (mRetryView == null) {
            mRetryView = inflate(mRetryResource, mRetryViewId);
            mRetryView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mRetryClickListener != null){
                        showLoading();
                        mRetryView.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mRetryClickListener.onRetryClick();
                            }
                        }, 200);
                    }
                }
            });
        }

        showView(mRetryView);
        return mRetryView;
    }

    public View showLoading(){
        if (mLoadingView == null)
            mLoadingView = inflate(mLoadingResource, mLoadingViewId);

        showView(mLoadingView);
        return mLoadingView;
    }

    private void showView(View view){
        setVisibility(view, VISIBLE);
        if (mEmptyView == view){
            setVisibility(mLoadingView, GONE);
            setVisibility(mRetryView, GONE);
        }else if (mLoadingView == view){
            setVisibility(mEmptyView, GONE);
            setVisibility(mRetryView, GONE);
        }else {
            setVisibility(mEmptyView, GONE);
            setVisibility(mLoadingView, GONE);
        }
    }

    public View inflate(@LayoutRes int layoutResource, @IdRes int inflatedId) {
        final ViewParent viewParent = getParent();

        if (viewParent != null && viewParent instanceof ViewGroup) {
            if (layoutResource != 0) {
                final ViewGroup parent = (ViewGroup) viewParent;
                final LayoutInflater factory;
                if (mInflater != null) {
                    factory = mInflater;
                } else {
                    factory = LayoutInflater.from(getContext());
                }
                final View view = factory.inflate(layoutResource, parent, false);

                if (inflatedId != NO_ID) {
                    view.setId(inflatedId);
                }

                final int index = parent.indexOfChild(this);

                final ViewGroup.LayoutParams layoutParams = getLayoutParams();
                if (layoutParams != null) {
                    if (parent instanceof RelativeLayout) {
                        parent.addView(view, index, mLayoutParams);
                    }else {
                        parent.addView(view, index, layoutParams);
                    }
                } else {
                    parent.addView(view, index);
                }

                if (mLoadingView != null && mRetryView != null && mEmptyView != null){
                    parent.removeViewInLayout(this);
                }

                return view;
            } else {
                throw new IllegalArgumentException("StateView must have a valid layoutResource");
            }
        } else {
            throw new IllegalStateException("StateView must have a non-null ViewGroup viewParent");
        }
    }



    public int getEmptyResource() {
        return mEmptyResource;
    }

    public void setEmptyResource(@LayoutRes int emptyResource) {
        this.mEmptyResource = emptyResource;
    }

    public int getErrorResource() {
        return mRetryResource;
    }

    public void setErrorResource(@LayoutRes int errorResource) {
        this.mRetryResource = errorResource;
    }

    public int getEmptyViewId() {
        return mEmptyViewId;
    }

    public void setEmptyViewId(@IdRes int emptyViewId) {
        this.mEmptyViewId = emptyViewId;
    }

    public int getErrorViewId() {
        return mRetryViewId;
    }

    public void setErrorViewId(@IdRes int errorViewId) {
        this.mRetryViewId = errorViewId;
    }

    public LayoutInflater getInflater() {
        return mInflater;
    }

    public void setInflater(LayoutInflater inflater) {
        this.mInflater = inflater;
    }

    public void setOnRetryClickListener(OnRetryClickListener listener){
        this.mRetryClickListener = listener;
    }

    public interface OnRetryClickListener{
        void onRetryClick();
    }
}
