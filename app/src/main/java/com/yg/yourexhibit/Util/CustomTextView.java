package com.yg.yourexhibit.Util;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 2yg on 2017. 10. 25..
 */

public class CustomTextView extends TextView{

    private int mAvailableWidth = 0;
    private Paint mPaint;
    private List<String> mCutStr = new ArrayList<String>();

    public CustomTextView(Context context) {
        super(context);
    }

    public CustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private int setTextInfo(String text, int textWidth, int textHeight) {
        // 그릴 페인트 세팅
        mPaint = getPaint();
        mPaint.setColor(getTextColors().getDefaultColor());
        mPaint.setTextSize(getTextSize());

        int mTextHeight = textHeight;

        if (textWidth > 0) {
            // 값 세팅
            mAvailableWidth = textWidth - this.getPaddingLeft() - this.getPaddingRight();

            mCutStr.clear();
            int end = 0;
            do {
                // 글자가 width 보다 넘어가는지 체크
                end = mPaint.breakText(text, true, mAvailableWidth, null);
                if (end > 0) {
                    // 자른 문자열을 문자열 배열에 담아 놓는다.
                    mCutStr.add(text.substring(0, end));
                    // 넘어간 글자 모두 잘라 다음에 사용하도록 세팅
                    text = text.substring(end);
                    // 다음라인 높이 지정
                    if (textHeight == 0) mTextHeight += getLineHeight();
                }
            } while (end > 0);
        }
        mTextHeight += getPaddingTop() + getPaddingBottom();
        return mTextHeight;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // 글자 높이 지정
        float height = getPaddingTop() + getLineHeight();
        for (String text : mCutStr) {
            // 캔버스에 라인 높이 만큰 글자 그리기
            canvas.drawText(text, getPaddingLeft(), height, mPaint);
            height += getLineHeight();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int parentWidth = MeasureSpec.getSize(widthMeasureSpec);
        int parentHeight = MeasureSpec.getSize(heightMeasureSpec);
        int height = setTextInfo(this.getText().toString(), parentWidth, parentHeight);
        // 부모 높이가 0인경우 실제 그려줄 높이만큼 사이즈를 놀려줌...
        if (parentHeight == 0)
            parentHeight = height;
        this.setMeasuredDimension(parentWidth, parentHeight);
    }

    @Override
    protected void onTextChanged(final CharSequence text, final int start, final int before, final int after) {
        // 글자가 변경되었을때 다시 세팅
        setTextInfo(text.toString(), this.getWidth(), this.getHeight());
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        // 사이즈가 변경되었을때 다시 세팅(가로 사이즈만...)
        if (w != oldw) {
            setTextInfo(this.getText().toString(), w, h);
        }
    }
}
