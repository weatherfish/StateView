package com.github.nukc.stateview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import java.lang.ref.WeakReference;

/**
 * Created by C on 4/3/2016.
 * https://github.com/nukc
 */
public class StateView extends View {

    private int mEmptyResource;
    private int mRetryResource;
    private int mEmptyViewId;
    private int mRetryViewId;

    private WeakReference<View> mInflatedViewRef;

    private LayoutInflater mInflater;
    private OnInflateListener mInflateListener;

    public StateView(Context context) {
        this(context, null);
    }

    public StateView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StateView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.StateView);
        mEmptyResource = a.getResourceId(R.styleable.StateView_emptyResource, 0);
        mRetryResource = a.getResourceId(R.styleable.StateView_retryResource, 0);
        mEmptyViewId = a.getResourceId(R.styleable.StateView_emptyViewId, NO_ID);
        mRetryViewId = a.getResourceId(R.styleable.StateView_retryViewId, NO_ID);
        a.recycle();

        if (mEmptyResource == 0){
            mEmptyResource = R.layout.view_empty;
        }
        if (mRetryResource == 0){
            mRetryResource = R.layout.view_retry;
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
        if (mInflatedViewRef != null) {
            View view = mInflatedViewRef.get();
            if (view != null) {
                view.setVisibility(visibility);
            } else {
                throw new IllegalStateException("setVisibility called on un-referenced view");
            }

        } else {
            super.setVisibility(visibility);
        }
    }

    public void showContent(){
       removeInflatedView();
    }

    public View inflateEmpty(){
        return inflate(mEmptyResource, mEmptyViewId);
    }

    public View inflateRetry(){
        return inflate(mRetryResource, mRetryViewId);
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
                final View view = factory.inflate(layoutResource, parent,
                        false);

                if (inflatedId != NO_ID) {
                    view.setId(inflatedId);
                }

                Log.e("State", "///-" + parent.getChildCount());

                removeInflatedView(parent);

                final int index = parent.indexOfChild(this);

                Log.e("State1", index + "///-" + parent.getChildCount());

                final ViewGroup.LayoutParams layoutParams = getLayoutParams();
                if (layoutParams != null) {
                    parent.addView(view, index, layoutParams);
                } else {
                    parent.addView(view, index);
                }

                Log.e("State2", index + "///+" + parent.getChildCount());

                mInflatedViewRef = new WeakReference<>(view);

                if (mInflateListener != null) {
                    mInflateListener.onInflate(this, view);
                }

                return view;
            } else {
                throw new IllegalArgumentException("StateView must have a valid layoutResource");
            }
        } else {
            throw new IllegalStateException("StateView must have a non-null ViewGroup viewParent");
        }
    }

    private void removeInflatedView(){
        final ViewParent viewParent = getParent();

        if (viewParent != null && viewParent instanceof ViewGroup) {
            removeInflatedView((ViewGroup) viewParent);
        } else {
            throw new IllegalStateException("StateView must have a non-null ViewGroup viewParent");
        }
    }

    private void removeInflatedView(ViewGroup parent){
        if (mInflatedViewRef != null) {
            View refView = mInflatedViewRef.get();
            if (parent != null) {
                parent.removeView(refView);
            }
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

    /**
     * Specifies the inflate listener to be notified after this StateView successfully
     * inflated its layout resource.
     *
     * @param inflateListener The OnInflateListener to notify of successful inflation.
     *
     */
    public void setOnInflateListener(OnInflateListener inflateListener) {
        mInflateListener = inflateListener;
    }

    /**
     * Listener used to receive a notification after a ViewStub has successfully
     * inflated its layout resource.
     *
     */
    public interface OnInflateListener {
        /**
         * Invoked after a StateView successfully inflated its layout resource.
         * This method is invoked after the inflated view was added to the
         * hierarchy but before the layout pass.
         *
         * @param stateView The StateView that initiated the inflation.
         * @param inflated The inflated View.
         */
        void onInflate(StateView stateView, View inflated);
    }
}
