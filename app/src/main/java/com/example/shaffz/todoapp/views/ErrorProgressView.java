package com.example.shaffz.todoapp.views;

import android.content.Context;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import com.example.shaffz.todoapp.R;


public class ErrorProgressView extends LinearLayoutCompat {
    public static final int TYPE_EMPTY = 1;


    private ContentLoadingProgressBar mProgressBar;

    private TextView mErrorTitleTxt;
    private TextView mErrorMessageTxt;
    private AppCompatImageView mErrorImage;


    public ErrorProgressView(Context context) {
        super(context);
        setOrientation(LinearLayoutCompat.VERTICAL);
        init(context);
    }

    public ErrorProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOrientation(LinearLayoutCompat.VERTICAL);
        init(context);
    }

    public ErrorProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOrientation(LinearLayoutCompat.VERTICAL);
        init(context);
    }




    protected void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.error_progress_view, this);
        mProgressBar = (ContentLoadingProgressBar) findViewById(R.id.error_progress);
        mErrorTitleTxt = (TextView) findViewById(R.id.error_title);
        mErrorMessageTxt = (TextView) findViewById(R.id.error_message);
        mErrorImage = (AppCompatImageView) findViewById(R.id.error_image);
    }

    public void showProgress(boolean show) {
        if (show) {
            mProgressBar.setVisibility(VISIBLE);
            showImage(false);
            // showButton(false);
            // mErrorTitleTxt.setVisibility(GONE);
            mErrorMessageTxt.setVisibility(GONE);
        } else {
            mProgressBar.setVisibility(GONE);
        }
    }

    public void setErrorType(int type) {

        switch (type) {
            case TYPE_EMPTY:
                mErrorTitleTxt.setText("Oops...");
                showError(R.drawable.error_view_empty, R.string.error_view_empty_list);
                break;

            default:
                showError(R.drawable.error_view_empty, R.string.error_view_general);
                break;
        }

    }

    private void showError(int error_view_empty, int error_view_empty_list) {
        showProgress(false);

        showImage(true);
        //mErrorTitleTxt.setVisibility(VISIBLE);
        mErrorImage.setImageResource(error_view_empty);
        mErrorImage.setColorFilter(getResources().getColor(R.color.colorAccent));
        setErrorMessageText(getContext().getString(error_view_empty_list));
        //setButtonText(getContext().getString(error_view_network_text));
    }


    public void setErrorMessageText(String mErrorMessageTxt) {
        showView(this.mErrorMessageTxt);
        this.mErrorMessageTxt.setText(mErrorMessageTxt);
    }


    private void showView(View mErrorMessageTxt) {
        mErrorMessageTxt.setVisibility(VISIBLE);
    }




    public void showImage(boolean show) {
        if (!show) {
            mErrorImage.setVisibility(GONE);
        } else {
            mErrorImage.setVisibility(VISIBLE);
        }
    }



    @Override
    public boolean isInEditMode() {
        return true;
    }


}
