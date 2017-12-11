package com.wulee.webactivitylib;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class WebActivity extends AppCompatActivity implements WebFragment.OnWebViewChangeListener,View.OnClickListener {

    private RelativeLayout titleLayout;
    private ImageView ivBack;
    private TextView tvTitle;

    private WebFragment mWebFragment;
    private ProgressBar mProgressBar;

    private int mBgTitleColorRes;
    private String url;
    private String title;

    /**
     * 启动 Web 容器页面
     * @param from
     * @param url  URL 链接
     */
    public static void launch(@NonNull Activity from, @NonNull String url, String title,int bgTitleColorRes) {
        Intent intent = new Intent(from, WebActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("url", url);
        intent.putExtra("bgTitleColorRes", bgTitleColorRes);
        from.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        initView();
        initData();
    }

    private void initView() {
        titleLayout =  (RelativeLayout) findViewById(R.id.titlelayout);
        ivBack =  (ImageView) findViewById(R.id.iv_back);
        tvTitle =  (TextView) findViewById(R.id.title);
        mProgressBar = (ProgressBar) findViewById(R.id.pb_web);

        ivBack.setOnClickListener(this);
    }


    private void initData() {
        url = getIntent().getStringExtra("url");
        title = getIntent().getStringExtra("title");
        mBgTitleColorRes = getIntent().getIntExtra("bgTitleColorRes",-1);

        setTitle(title);
        tvTitle.setText(title);
        titleLayout.setBackgroundResource(mBgTitleColorRes);

        mWebFragment = WebFragment.newInstance(url);
        mWebFragment.setListener(this);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.activity_web, mWebFragment);
        transaction.commit();
    }


    @Override
    public void onWebViewTitleChanged(String title) {
        setTitle(title);
    }

    @Override
    public void onWebViewProgressChanged(int newProgress) {
        if (newProgress >= 100) {
            mProgressBar.setVisibility(View.GONE);
        } else {
            if (mProgressBar.getVisibility() == View.GONE) {
                mProgressBar.setVisibility(View.VISIBLE);
            }
            mProgressBar.setProgress(newProgress);
        }
    }

    @Override
    public void onBackPressed() {
        if (!mWebFragment.onBackPressed()) {
            super.onBackPressed();
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.iv_back) {
            finish();
        }
    }

}
