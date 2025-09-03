package com.hjq.logcat.demo;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import com.hjq.bar.OnTitleBarListener;
import com.hjq.bar.TitleBar;
import com.hjq.toast.Toaster;

public final class MainActivity extends AppCompatActivity implements OnTitleBarListener, View.OnClickListener {

    private TitleBar mTitleBar;

    private EditText mTagView;
    private EditText mContentView;

    private View mLogDebugView;
    private View mLogInfoView;
    private View mLogWarnView;
    private View mLogErrorView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTitleBar = findViewById(R.id.tb_main_title);
        mTagView = findViewById(R.id.et_main_input_log_tag);
        mContentView = findViewById(R.id.et_main_input_log_content);

        mLogDebugView = findViewById(R.id.btn_main_print_log_debug);
        mLogInfoView = findViewById(R.id.btn_main_print_log_info);
        mLogWarnView = findViewById(R.id.btn_main_print_log_warn);
        mLogErrorView = findViewById(R.id.btn_main_print_log_error);

        mLogDebugView.setOnClickListener(this);
        mLogInfoView.setOnClickListener(this);
        mLogWarnView.setOnClickListener(this);
        mLogErrorView.setOnClickListener(this);

        mTitleBar.setOnTitleBarListener(this);

        if (NotificationManagerCompat.from(this).areNotificationsEnabled()) {
            Toaster.show(getString(R.string.notification_hint));
        }
    }

    @Override
    public void onClick(View v) {
        String logTag = mTagView.getText().toString();
        String logContent = mContentView.getText().toString();
        if (TextUtils.isEmpty(logTag)) {
            Toaster.show(getString(R.string.log_tag_empty_hint));
            return;
        }
        if (TextUtils.isEmpty(logContent)) {
            Toaster.show(getString(R.string.log_content_empty_hint));
            return;
        }
        String logSuccessHint = getString(R.string.log_success_hint);
        if (v == mLogDebugView) {
            Log.d(logTag, logContent);
            Toaster.show(logSuccessHint);
        } else if (v == mLogInfoView) {
            Log.i(logTag, logContent);
            Toaster.show(logSuccessHint);
        } else if (v == mLogWarnView) {
            Log.w(logTag, logContent);
            Toaster.show(logSuccessHint);
        } else if (v == mLogErrorView) {
            Log.e(logTag, logContent);
            Toaster.show(logSuccessHint);
        }
    }

    @Override
    public void onTitleClick(TitleBar titleBar) {
        Uri uri = Uri.parse(String.valueOf(mTitleBar.getTitle()));
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}