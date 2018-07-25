package wangfeixixi.github.com.widget_dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import wangfeixixi.dialog.ShareDialog;
import wangfeixixi.dialog.SweetAlertDialog;

public class ShareDialogActivity extends AppCompatActivity implements View.OnClickListener {

    private int i = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.share_dialog_activity);
        findViewById(R.id.btn_1).setOnClickListener(this);
        findViewById(R.id.btn_2).setOnClickListener(this);
        findViewById(R.id.btn_3).setOnClickListener(this);
        findViewById(R.id.btn_4).setOnClickListener(this);
        findViewById(R.id.btn_5).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        final ShareDialog dialog = new ShareDialog(this);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);

        switch (v.getId()) {
            case R.id.btn_1://内容，单按钮
                // default title "Here's a message!"
                dialog.changeAlertType(ShareDialog.CONTENT_ONE_BTN)
                        .setConfirmText("知道了")
                        .show();
                break;
            case R.id.btn_2://内容，双按钮
                dialog.changeAlertType(ShareDialog.CONTENT_TWO_BTN);
                dialog.setContentText("哈哈哈哈哈")
                        .setConfirmText("走了")
                        .setCancelText("就不")
                        .show();
                break;
            case R.id.btn_3://标题，内容，双按钮
                dialog.changeAlertType(ShareDialog.TITLE_CONTENT_TWO_BTN);
                dialog.setContentText("It's pretty, isn't it?")
                        .setTitleText("猜一下")
                        .setConfirmText("走了")
                        .setCancelText("就不")
                        .show();
                break;
            case R.id.btn_4://标题，内容，单按钮
                dialog.changeAlertType(ShareDialog.TITLE_CONTENT_ONE_BTN);
                dialog.setContentText("It's pretty, isn't it?")
                        .setTitleText("猜一下")
                        .setConfirmText("走了")
                        .show();
                break;
            case R.id.btn_5://loading
//                final SweetAlertDialog pDialog = new SweetAlertDialog(this, SweetAlertDialog.LOADING_TYPE)
                dialog.changeAlertType(ShareDialog.LOADING);
                dialog.setTitleText("Loading");
                dialog.show();
                dialog.setCancelable(false);
                new CountDownTimer(800 * 7, 800) {
                    public void onTick(long millisUntilFinished) {
                        // you can change the progress bar color by ProgressHelper every 800 millis
                        i++;
                        switch (i) {
                            case 0:
                                dialog.getProgressHelper().setBarColor(getResources().getColor(R.color.blue_btn_bg_color));
                                break;
                            case 1:
                                dialog.getProgressHelper().setBarColor(getResources().getColor(R.color.material_deep_teal_50));
                                break;
                            case 2:
                                dialog.getProgressHelper().setBarColor(getResources().getColor(R.color.success_stroke_color));
                                break;
                            case 3:
                                dialog.getProgressHelper().setBarColor(getResources().getColor(R.color.material_deep_teal_20));
                                break;
                            case 4:
                                dialog.getProgressHelper().setBarColor(getResources().getColor(R.color.material_blue_grey_80));
                                break;
                            case 5:
                                dialog.getProgressHelper().setBarColor(getResources().getColor(R.color.warning_stroke_color));
                                break;
                            case 6:
                                dialog.getProgressHelper().setBarColor(getResources().getColor(R.color.success_stroke_color));
                                break;
                        }
                    }

                    public void onFinish() {
                        i = -1;
                        dialog.setTitleText("Success!")
                                .setConfirmText("OK")
                                .changeAlertType(ShareDialog.CONTENT_ONE_BTN);
                    }
                }.start();
                break;
        }
    }
}
