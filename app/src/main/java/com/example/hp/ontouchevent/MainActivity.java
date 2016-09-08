package com.example.hp.ontouchevent;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener {
    private ObjectAnimator firstMove;
    private ObjectAnimator hulkMoveRandomX;
    private ObjectAnimator hulkMoveRandomY;
    private ObjectAnimator captainRight;
    private ObjectAnimator captainLeft;
    private ObjectAnimator captainStop;
    private AnimatorSet animatorSet;
    private Button button;
    private ImageView captain;
    private ImageView hulk;
    private ImageView left;
    private ImageView right;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

    }

    public void init() {
        captain = (ImageView) findViewById(R.id.img_captain);
        hulk = (ImageView) findViewById(R.id.img_hulk);
        left = (ImageView) findViewById(R.id.img_left);
        right = (ImageView) findViewById(R.id.img_right);

        //
        firstMove = ObjectAnimator.ofFloat(hulk, "y", 10f, getHeightOfScreen() - getNavigationHeight() - 600f);
        firstMove.setDuration(6000);
        firstMove.start();
        //
        left.setOnTouchListener(this);
        right.setOnTouchListener(this);
        MyAsyncTask myAsyncTask= new MyAsyncTask(this);
        myAsyncTask.execute();
    }

    public void hulkMoveRandom() {

        animatorSet = new AnimatorSet();
        Random random = new Random();
        //
        hulkMoveRandomX = ObjectAnimator.ofFloat(hulk, "x", hulk.getX(), random.nextInt(getWithOfScreen() - hulk.getWidth()));// check height later
        hulkMoveRandomX.setDuration(3000);
        //
        hulkMoveRandomY = ObjectAnimator.ofFloat(hulk, "y", hulk.getY(), random.nextInt(getHeightOfScreen() - getNavigationHeight() - captain.getHeight()), getHeightOfScreen());
        hulkMoveRandomY.setDuration(3000);
        //
        animatorSet.playTogether(hulkMoveRandomX, hulkMoveRandomY);
        //
        animatorSet.start();
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {

        if (view == right) {
            //
            captainRight = ObjectAnimator.ofFloat(captain, "x", captain.getX(), getWithOfScreen() - captain.getWidth());
            captainRight.setDuration(3000);
            //
            captainStop = ObjectAnimator.ofFloat(captain, "x", captain.getX(), captain.getX());
            captainStop.setDuration(3000);
            //
            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_UP: {
                    captainStop.start();
                    break;
                }
                case MotionEvent.ACTION_DOWN: {
                    captainRight.start();

                    break;
                }
                default:
                    return false;
            }
        }
        if (view == left) {
            //
            captainLeft = ObjectAnimator.ofFloat(captain, "x", captain.getX(), 0);
            captainLeft.setDuration(3000);
            //
            captainStop = ObjectAnimator.ofFloat(captain, "x", captain.getX(), captain.getX());
            captainStop.setDuration(3000);
            //
            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_UP: {
                    captainStop.start();
                    break;
                }
                case MotionEvent.ACTION_DOWN: {
                    captainLeft.start();
                    break;
                }
                default:
                    return false;
            }

        }
        return true;
    }

    public int getHeightOfScreen() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((WindowManager) getSystemService(WINDOW_SERVICE)).getDefaultDisplay()
                .getMetrics(displayMetrics);
        return displayMetrics.heightPixels;
    }

    //
    public int getWithOfScreen() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((WindowManager) getSystemService(WINDOW_SERVICE)).getDefaultDisplay()
                .getMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }

    //
    public int getNavigationHeight() {
        Resources resources = getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId > 0) {
            return resources.getDimensionPixelSize(resourceId);
        }
        return 0;
    }

    public    Boolean checkHit(ImageView v1, ImageView v2) {
        Rect rect01 = new Rect();
        v1.getHitRect(rect01);
        Rect rect02 = new Rect();
        v2.getHitRect(rect02);
        return Rect.intersects(rect01, rect02);

    }
}
