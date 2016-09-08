package com.example.hp.ontouchevent;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Random;

/**
 * Created by HP on 9/7/2016.
 */
public class MyAsyncTask extends AsyncTask<Void,Integer,Void> {
    private AnimatorSet animatorSet;
    private ObjectAnimator hulkMoveRandomX;
    private ObjectAnimator hulkMoveRandomY;
    private Boolean done=false;
    private Activity context;
    private ImageView hulk,captain;

    public MyAsyncTask(Activity context) {
        this.context = context;
        hulk= (ImageView) context.findViewById(R.id.img_hulk);
        captain= (ImageView) context.findViewById(R.id.img_captain);
    }

    @Override
    protected Void doInBackground(Void... voids) {
        while (!done){
            SystemClock.sleep(100);
            publishProgress(1);
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        if (checkHit(hulk,captain)){
            hulk.setImageResource(R.drawable.hulk_collison);
            hulkMoveRandom();
            return;
        }
        hulk.setImageResource(R.drawable.hulk);

    }



    public  Boolean checkHit(ImageView v1, ImageView v2) {
        Rect rect01 = new Rect();
        v1.getHitRect(rect01);
        Rect rect02 = new Rect();
        v2.getHitRect(rect02);
        return Rect.intersects(rect01, rect02);

    }
    private void init01(int...ds){

    }
    public void hulkMoveRandom() {
        Log.d("hi", "hulkMoveRandom: ");
        Toast.makeText(context, "Collision!!!", Toast.LENGTH_SHORT).show();

        animatorSet = new AnimatorSet();
        Random random = new Random();
        //
        hulkMoveRandomX = ObjectAnimator.ofFloat(hulk, "x", hulk.getX(), random.nextInt(getWithOfScreen() - hulk.getWidth()));// check height later
        Log.d("bug", "hulkMoveRandom: "+hulk.getX());
//        hulkMoveRandomX = ObjectAnimator.ofFloat(hulk, "x", hulk.getX(), 500f,40f);// check height later
        hulkMoveRandomX.setDuration(2000);
        //
        hulkMoveRandomY = ObjectAnimator.ofFloat(hulk, "y", hulk.getY(), random.nextInt(getHeightOfScreen() - getNavigationHeight() - captain.getHeight()), getHeightOfScreen());
//        hulkMoveRandomY = ObjectAnimator.ofFloat(hulk, "y", hulk.getY(), 500f,100f);
        hulkMoveRandomY.setDuration(2000);
        //
        animatorSet.playTogether(hulkMoveRandomX, hulkMoveRandomY);
        //
        animatorSet.start();
        Log.d("hi", "end of method: ");

    }
    public int getHeightOfScreen() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((WindowManager) context.getSystemService(context.WINDOW_SERVICE)).getDefaultDisplay()
                .getMetrics(displayMetrics);
        return displayMetrics.heightPixels;
    }

    //
    public int getWithOfScreen() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((WindowManager) context.getSystemService(context.WINDOW_SERVICE)).getDefaultDisplay()
                .getMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }

    //
    public int getNavigationHeight() {
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId > 0) {
            return resources.getDimensionPixelSize(resourceId);
        }
        return 0;
    }
}
