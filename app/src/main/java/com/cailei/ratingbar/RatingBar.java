package com.cailei.ratingbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * @author : cailei
 * @date : 2020-02-12 13:07
 * @description :
 */
public class RatingBar extends View {
    private Bitmap mStarNormalBitmap, mStarFocusBitmap;

    private int mStarNum = 5;


    private int mSpace = 5;


    private int mNeedDraw=0;

    public RatingBar(Context context) {
        this(context, null);
    }

    public RatingBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RatingBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RatingBar);

        int starNormalId = typedArray.getResourceId(R.styleable.RatingBar_starNormal, 0);

        if (starNormalId == 0) {
            throw new RuntimeException("请设置属性 starNormal");
        }
        mStarNormalBitmap = BitmapFactory.decodeResource(getResources(), starNormalId);

        int starFocusId = typedArray.getResourceId(R.styleable.RatingBar_starFocus, 0);

        if (starFocusId == 0) {
            throw new RuntimeException("请设置属性 starNormal");
        }

        mStarFocusBitmap = BitmapFactory.decodeResource(getResources(), starFocusId);

        mStarNum = typedArray.getInt(R.styleable.RatingBar_starNum, mStarNum);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //指定控件宽高 控件的高即是星星的高

        int starHeight = mStarFocusBitmap.getHeight();
        int starWidth = mStarFocusBitmap.getWidth();

        int height = starHeight + getPaddingBottom() + getPaddingTop();
        int width = 0;
        for (int i = 0; i < mStarNum; i++) {
            width = starWidth + width + mSpace;
        }


        setMeasuredDimension(width + getPaddingLeft() + getPaddingRight(), height);


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        for (int i = 0; i < mStarNum; i++) {
            canvas.drawBitmap(mStarNormalBitmap, mStarFocusBitmap.getWidth() * i + mSpace * i, 0, null);
        }

        for (int i = 0; i < mNeedDraw; i++) {
            canvas.drawBitmap(mStarFocusBitmap, mStarFocusBitmap.getWidth() * i + mSpace * i, 0, null);
        }

    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
//                int moveX= (int) event.getX();
//                mNeedDraw=getNeedDrawNum(moveX);
//                if(mNeedDraw>0){
//                    invalidate();
//                }
            case MotionEvent.ACTION_MOVE:
                int moveX= (int) event.getX();
                mNeedDraw=getNeedDrawNum(moveX);
                if(mNeedDraw>0){
                    invalidate();
                }
                break;
            case MotionEvent.ACTION_UP:
                break;
            default:
                break;
        }
        return true;
    }

    private int getNeedDrawNum(int moveX) {
        return moveX/mStarFocusBitmap.getWidth()+1;
    }
}
