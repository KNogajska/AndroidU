package com.karolina.androidu;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Karolina on 01/01/2018.
 */

class DrawView extends View implements View.OnTouchListener {   //implementacja dotyku

    Paint paint = new Paint();
    //Point point = new Point();         //klasa dostarcza x i y
    List<Point> points = new ArrayList<>();


    public DrawView(Context context) {
        super(context);
        setFocusable(true);
        setFocusableInTouchMode(true);
        this.setOnTouchListener(this);
        paint.setColor(Color.CYAN);
        paint.setAntiAlias(true);        //wygładzanie krawędzi
    }
    @Override
    protected void onDraw(Canvas canvas){             //Canvas płótno po którym malujemy
        for (Point pnt : points) {
            canvas.drawCircle(pnt.x, pnt.y, 30, paint);
        }
        super.onDraw(canvas);
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {  //implementacja metody z OnTouchListnera
        Point point = new Point();
        point.x = (int) motionEvent.getX();
        point.y = (int) motionEvent.getY();
        points.add(point);
        invalidate();                          //wywołanie metody onDraw
        return true;                  //false = przerwanie dotyku- pojedyńcze kliknięcie,  true żeby było płynnie
    }
}
