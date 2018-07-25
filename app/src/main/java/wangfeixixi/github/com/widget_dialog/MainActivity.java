package wangfeixixi.github.com.widget_dialog;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import wangfeixixi.dialog.SweetAlertDialog;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private int i = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        findViewById(R.id.basic_test).setOnClickListener(this);
//        findViewById(R.id.under_text_test).setOnClickListener(this);
//        findViewById(R.id.error_text_test).setOnClickListener(this);
//        findViewById(R.id.success_text_test).setOnClickListener(this);
//        findViewById(R.id.warning_confirm_test).setOnClickListener(this);
//        findViewById(R.id.warning_cancel_test).setOnClickListener(this);
//        findViewById(R.id.custom_img_test).setOnClickListener(this);
//        findViewById(R.id.progress_dialog).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        final SweetAlertDialog dialog = new SweetAlertDialog(this);
//        final ShareDialog dialog = new ShareDialog(this);

        switch (v.getId()) {
            case R.id.basic_test:
                // default title "Here's a message!"
                dialog.setCancelable(true);
                dialog.setCanceledOnTouchOutside(true);
                dialog.show();
                break;
            case R.id.under_text_test:
                dialog.setContentText("It's pretty, isn't it?")
                        .show();
                break;
            case R.id.error_text_test:
                dialog.changeAlertType(SweetAlertDialog.ERROR_TYPE);
                dialog.setTitleText("Oops...")
                        .setContentText("Something went wrong!")
                        .show();
                break;
            case R.id.success_text_test:
//                new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                dialog.changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                dialog.setTitleText("Good job!")
                        .setContentText("You clicked the button!")
                        .show();
                break;
            case R.id.warning_confirm_test:
                dialog.changeAlertType(SweetAlertDialog.WARNING_TYPE);
                dialog.setTitleText("Are you sure?")
                        .setContentText("Won't be able to recover this file!")
                        .setConfirmText("Yes,delete it!")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                // reuse previous dialog instance
                                sDialog.setTitleText("Deleted!")
                                        .setContentText("Your imaginary file has been deleted!")
                                        .setConfirmText("OK")
                                        .setConfirmClickListener(null)
                                        .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                            }
                        })
                        .show();
                break;
            case R.id.warning_cancel_test:
//                new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                dialog.changeAlertType(SweetAlertDialog.WARNING_TYPE);
                dialog.setTitleText("Are you sure?")
                        .setContentText("Won't be able to recover this file!")
                        .setCancelText("No,cancel plx!")
                        .setConfirmText("Yes,delete it!")
                        .showCancelButton(true)
                        .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                // reuse previous dialog instance, keep widget user state, reset them if you need
                                sDialog.setTitleText("Cancelled!")
                                        .setContentText("Your imaginary file is safe :)")
                                        .setConfirmText("OK")
                                        .showCancelButton(false)
                                        .setCancelClickListener(null)
                                        .setConfirmClickListener(null)
                                        .changeAlertType(SweetAlertDialog.ERROR_TYPE);

                                // or you can new a SweetAlertDialog to show
                               /* sDialog.dismiss();
                                new SweetAlertDialog(SampleActivity.this, SweetAlertDialog.ERROR_TYPE)
                                        .setTitleText("Cancelled!")
                                        .setContentText("Your imaginary file is safe :)")
                                        .setConfirmText("OK")
                                        .show();*/
                            }
                        })
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.setTitleText("Deleted!")
                                        .setContentText("Your imaginary file has been deleted!")
                                        .setConfirmText("OK")
                                        .showCancelButton(false)
                                        .setCancelClickListener(null)
                                        .setConfirmClickListener(null)
                                        .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                            }
                        })
                        .show();
                break;
            case R.id.custom_img_test:
//                new SweetAlertDialog(this, SweetAlertDialog.CUSTOM_IMAGE_TYPE)
                dialog.changeAlertType(SweetAlertDialog.ERROR_TYPE);
                dialog.setTitleText("Sweet!")
                        .setContentText("Here's a custom image.")
                        .setCustomImage(R.drawable.custom_img)
                        .show();
                break;
            case R.id.progress_dialog:
//                final SweetAlertDialog pDialog = new SweetAlertDialog(this, SweetAlertDialog.LOADING_TYPE)
                dialog.changeAlertType(SweetAlertDialog.ERROR_TYPE);
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
                                .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                    }
                }.start();
                break;
        }
    }
}
