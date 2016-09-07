package com.example.hp.ontouchevent;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener {
    private ObjectAnimator animatorLeft;
    private ObjectAnimator animatorStop;
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

    private void init() {
        captain= (ImageView) findViewById(R.id.img_captain);
        captain= (ImageView) findViewById(R.id.img_captain);

    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        //
        animatorLeft = ObjectAnimator.ofFloat(captain,"x",captain.getX(),getWithOfScreen()-captain.getWidth());
        animatorLeft.setDuration(3000);
        //
        animatorStop = ObjectAnimator.ofFloat(captain,"x",captain.getX(),captain.getX());
        animatorStop.setDuration(3000);
        //
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_UP: {
                Log.d("bug", "move: ");
                animatorStop.start();
            break;
        }
        case MotionEvent.ACTION_DOWN: {
            Log.d("bug", "down: ");
            animatorLeft.start();

            break;
        }
        case 3: {
            break;
        }
            default:
                return false;
        }
        return true;
    }
    private int getHeightOfScreen() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((WindowManager) getSystemService(WINDOW_SERVICE)).getDefaultDisplay()
                .getMetrics(displayMetrics);
        return displayMetrics.heightPixels;
    }
    //
    private int getWithOfScreen() {
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
}
