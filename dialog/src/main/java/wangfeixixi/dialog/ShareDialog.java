package wangfeixixi.dialog;


import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.Transformation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ShareDialog extends Dialog implements View.OnClickListener {
    private View mDialogView;
    private AnimationSet mModalInAnim;
    private AnimationSet mModalOutAnim;
    private Animation mOverlayOutAnim;
    private TextView mTitleTextView;
    private TextView mContentTextView;
    private String mTitleText;
    private String mContentText;
    private boolean mShowCancel;
    private boolean mShowContent;
    private String mCancelText;
    private String mConfirmText;
    private int mAlertType;
    private LinearLayout mProgressFrame;
    private Button mConfirmButton;
    private Button mCancelButton;
    private ProgressHelper mProgressHelper;
    private OnSweetClickListener mCancelClickListener;
    private OnSweetClickListener mConfirmClickListener;
    private boolean mCloseFromCancel;

    public static final int LOADING = 0;
    public static final int LOADING_ONE_BTN = 1;
    public static final int CONTENT_ONE_BTN = 2;
    public static final int CONTENT_TWO_BTN = 3;
    public static final int TITLE_CONTENT_ONE_BTN = 4;
    public static final int TITLE_CONTENT_TWO_BTN = 5;
    private View mLine_btn;
    private View mLine_content;
    private LinearLayout mLL_content;

    public static interface OnSweetClickListener {
        public void onClick(ShareDialog sweetAlertDialog);
    }

    public ShareDialog(Context context) {
        this(context, CONTENT_ONE_BTN);
    }

    public ShareDialog(Context context, int alertType) {
        super(context, R.style.alert_dialog);
        setCancelable(true);
        setCanceledOnTouchOutside(false);
        mProgressHelper = new ProgressHelper(context);
        mAlertType = alertType;

        mModalInAnim = (AnimationSet) OptAnimationLoader.loadAnimation(getContext(), R.anim.modal_in);
        mModalOutAnim = (AnimationSet) OptAnimationLoader.loadAnimation(getContext(), R.anim.modal_out);
        mModalOutAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mDialogView.setVisibility(View.GONE);
                mDialogView.post(new Runnable() {
                    @Override
                    public void run() {
                        if (mCloseFromCancel) {
                            ShareDialog.super.cancel();
                        } else {
                            ShareDialog.super.dismiss();
                        }
                    }
                });
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        // dialog overlay fade out
        mOverlayOutAnim = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                WindowManager.LayoutParams wlp = getWindow().getAttributes();
                wlp.alpha = 1 - interpolatedTime;
                getWindow().setAttributes(wlp);
            }
        };
        mOverlayOutAnim.setDuration(120);
    }

    @SuppressLint("WrongViewCast")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.share_dialog);

        mDialogView = getWindow().getDecorView().findViewById(android.R.id.content);
        mTitleTextView = (TextView) findViewById(R.id.title_text);
        mContentTextView = (TextView) findViewById(R.id.content_text);
        mLine_btn = findViewById(R.id.v_line_btn);
        mLine_content = findViewById(R.id.v_line_content);
        mProgressFrame = (LinearLayout) findViewById(R.id.progress_dialog);
        mLL_content = (LinearLayout) findViewById(R.id.ll_content);
        mConfirmButton = (Button) findViewById(R.id.confirm_button);
        mCancelButton = (Button) findViewById(R.id.cancel_button);
        mProgressHelper.setProgressWheel((ProgressWheel) findViewById(R.id.progressWheel));
        mConfirmButton.setOnClickListener(this);
        mCancelButton.setOnClickListener(this);

        setTitleText(mTitleText);
        setContentText(mContentText);
        setCancelText(mCancelText);
        setConfirmText(mConfirmText);
        changeAlertType(mAlertType, true);

    }

    private void restore() {
        mProgressFrame.setVisibility(View.GONE);
        mConfirmButton.setVisibility(View.VISIBLE);
    }

    private void changeAlertType(int alertType, boolean fromCreate) {
        mAlertType = alertType;// call after created views
        if (mDialogView != null) {
            if (!fromCreate)// restore all of views state before switching alert type
                restore();
            switch (mAlertType) {
                case LOADING:
                    mProgressFrame.setVisibility(View.VISIBLE);
                    mConfirmButton.setVisibility(View.GONE);
                    mCancelButton.setVisibility(View.GONE);
                    mLine_btn.setVisibility(View.GONE);
                    mLine_content.setVisibility(View.GONE);
                    mTitleTextView.setVisibility(View.GONE);
                    mContentTextView.setVisibility(View.GONE);
                    mLL_content.setVisibility(View.GONE);
                    break;
                case LOADING_ONE_BTN:
                    mProgressFrame.setVisibility(View.VISIBLE);
                    mConfirmButton.setVisibility(View.VISIBLE);
                    mCancelButton.setVisibility(View.GONE);
                    mLine_btn.setVisibility(View.GONE);
                    mLine_content.setVisibility(View.VISIBLE);
                    mTitleTextView.setVisibility(View.GONE);
                    mContentTextView.setVisibility(View.VISIBLE);
                    mLL_content.setVisibility(View.GONE);
                    break;
                case CONTENT_ONE_BTN:
                    mProgressFrame.setVisibility(View.GONE);
                    mConfirmButton.setVisibility(View.VISIBLE);
                    mCancelButton.setVisibility(View.GONE);
                    mLine_btn.setVisibility(View.GONE);
                    mLine_content.setVisibility(View.VISIBLE);
                    mTitleTextView.setVisibility(View.GONE);
                    mContentTextView.setVisibility(View.VISIBLE);
                    mLL_content.setVisibility(View.VISIBLE);
                    break;
                case CONTENT_TWO_BTN:
                    mProgressFrame.setVisibility(View.GONE);
                    mConfirmButton.setVisibility(View.VISIBLE);
                    mCancelButton.setVisibility(View.VISIBLE);
                    mLine_btn.setVisibility(View.VISIBLE);
                    mLine_content.setVisibility(View.VISIBLE);
                    mTitleTextView.setVisibility(View.GONE);
                    mContentTextView.setVisibility(View.VISIBLE);
                    mLL_content.setVisibility(View.VISIBLE);
                    break;
                case TITLE_CONTENT_ONE_BTN:
                    mProgressFrame.setVisibility(View.GONE);
                    mConfirmButton.setVisibility(View.VISIBLE);
                    mCancelButton.setVisibility(View.GONE);
                    mLine_btn.setVisibility(View.GONE);
                    mLine_content.setVisibility(View.VISIBLE);
                    mTitleTextView.setVisibility(View.VISIBLE);
                    mContentTextView.setVisibility(View.VISIBLE);
                    mLL_content.setVisibility(View.VISIBLE);
                    break;
                case TITLE_CONTENT_TWO_BTN:
                    mProgressFrame.setVisibility(View.GONE);
                    mConfirmButton.setVisibility(View.VISIBLE);
                    mCancelButton.setVisibility(View.VISIBLE);
                    mLine_btn.setVisibility(View.VISIBLE);
                    mLine_content.setVisibility(View.VISIBLE);
                    mTitleTextView.setVisibility(View.VISIBLE);
                    mContentTextView.setVisibility(View.VISIBLE);
                    mLL_content.setVisibility(View.VISIBLE);
                    break;
            }
        }
    }

    public int getAlerType() {
        return mAlertType;
    }

    public ShareDialog changeAlertType(int alertType) {
        changeAlertType(alertType, false);
        return this;
    }


    public String getTitleText() {
        return mTitleText;
    }

    public ShareDialog setTitleText(String text) {
        mTitleText = text;
        if (mTitleTextView != null && mTitleText != null) {
            mTitleTextView.setText(mTitleText);
        }
        return this;
    }

    public String getContentText() {
        return mContentText;
    }

    public ShareDialog setContentText(String text) {
        mContentText = text;
        if (mContentTextView != null && mContentText != null) {
            showContentText(true);
            mContentTextView.setText(mContentText);
        }
        return this;
    }

    public boolean isShowCancelButton() {
        return mShowCancel;
    }

    public ShareDialog showCancelButton(boolean isShow) {
        mShowCancel = isShow;
        if (mCancelButton != null) {
            mCancelButton.setVisibility(mShowCancel ? View.VISIBLE : View.GONE);
        }
        return this;
    }

    public boolean isShowContentText() {
        return mShowContent;
    }

    public ShareDialog showContentText(boolean isShow) {
        mShowContent = isShow;
        if (mContentTextView != null) {
            mContentTextView.setVisibility(mShowContent ? View.VISIBLE : View.GONE);
        }
        return this;
    }

    public String getCancelText() {
        return mCancelText;
    }

    public ShareDialog setCancelText(String text) {
        mCancelText = text;
        if (mCancelButton != null && mCancelText != null) {
            showCancelButton(true);
            mCancelButton.setText(mCancelText);
        }
        return this;
    }

    public String getConfirmText() {
        return mConfirmText;
    }

    public ShareDialog setConfirmText(String text) {
        mConfirmText = text;
        if (mConfirmButton != null && mConfirmText != null) {
            mConfirmButton.setText(mConfirmText);
        }
        return this;
    }

    public ShareDialog setCancelClickListener(OnSweetClickListener listener) {
        mCancelClickListener = listener;
        return this;
    }

    public ShareDialog setConfirmClickListener(OnSweetClickListener listener) {
        mConfirmClickListener = listener;
        return this;
    }

    protected void onStart() {
        mDialogView.startAnimation(mModalInAnim);
    }

    /**
     * The real Dialog.cancel() will be invoked async-ly after the animation finishes.
     */
    @Override
    public void cancel() {
        dismissWithAnimation(true);
    }

    /**
     * The real Dialog.dismiss() will be invoked async-ly after the animation finishes.
     */
    public void dismissWithAnimation() {
        dismissWithAnimation(false);
    }

    private void dismissWithAnimation(boolean fromCancel) {
        mCloseFromCancel = fromCancel;
        mConfirmButton.startAnimation(mOverlayOutAnim);
        mDialogView.startAnimation(mModalOutAnim);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.cancel_button) {
            if (mCancelClickListener != null) {
                mCancelClickListener.onClick(ShareDialog.this);
            } else {
                dismissWithAnimation();
            }
        } else if (v.getId() == R.id.confirm_button) {
            if (mConfirmClickListener != null) {
                mConfirmClickListener.onClick(ShareDialog.this);
            } else {
                dismissWithAnimation();
            }
        }
    }

    public ProgressHelper getProgressHelper() {
        return mProgressHelper;
    }
}