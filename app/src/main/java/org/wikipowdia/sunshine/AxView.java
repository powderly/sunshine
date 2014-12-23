package org.wikipowdia.sunshine;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class AxView extends View {

    public AxView(Context context){
        super(context);
    }

    public AxView(Context context, AttributeSet attrs){
        super(context, attrs);
    }

    public AxView(Context context, AttributeSet attrs, int defaultStyle){
        super(context, attrs, defaultStyle);
    }

    @Override
    protected void onMeasure(int wMeasureSpec, int hMeasureSpec) {

        int hSpecMode = MeasureSpec.getMode(hMeasureSpec);
        int hSpecSize = MeasureSpec.getSize(hMeasureSpec);

        int myHeight =hSpecSize;

        int wSpecMode = MeasureSpec.getMode(wMeasureSpec);
        int wSpecSize = MeasureSpec.getSize(wMeasureSpec);

        int myWidth = wSpecSize;

        if (hSpecMode == MeasureSpec.EXACTLY) {
            myHeight = hSpecSize;
        }else if (hSpecMode == MeasureSpec.AT_MOST){

        }
        if (wSpecMode == MeasureSpec.EXACTLY) {
            myWidth = wSpecSize;
        }else if (wSpecMode == MeasureSpec.AT_MOST){

        }
        setMeasuredDimension(myWidth, myHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int width = canvas.getWidth();
        int height = canvas.getHeight();
        int min = height < width ? height : width;

        float x = (float) (width / 2.0);
        float y = (float) (height / 2.0);
        float r = (float) (0.25 * min);

        Paint p = new Paint();
        p.setColor(Color.BLUE);

        canvas.drawColor(Color.GRAY);
        canvas.drawCircle(x, y, r, p);

    }
}