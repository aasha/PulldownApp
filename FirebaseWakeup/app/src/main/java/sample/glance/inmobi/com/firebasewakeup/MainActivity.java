package sample.glance.inmobi.com.firebasewakeup;

import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import sample.glance.inmobi.com.firebasewakeup.wakeup.WakeupReceiver;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity {

    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

       setContentView(R.layout.activity_main);
        imageView = (ImageView) findViewById(R.id.imageView);
        final Animation slide_down = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.slide_down);
        slide_down.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                imageView.setVisibility(View.GONE);
//                Intent intent = new Intent(MainActivity.this, PeekViewActivity.class);
//                overridePendingTransition(0, 0);
                Intent intent = new Intent(MainActivity.this, PeekViewService.class);
                startService(intent);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageView.startAnimation(slide_down);
            }
        });

//        String samsungApiKey = "038ceaabe0a0869aaa74cbf6eba4aadb";
//        FirebaseWakeupReceiver firebaseWakeupReceiver = new FirebaseWakeupReceiver(this, samsungApiKey);
//        firebaseWakeupReceiver.setRegion("IN");
//        firebaseWakeupReceiver.start(new WakeupReceiver.WakeupCallback() {
//            @Override
//            public void onWakeup() {
//                System.out.println("Got an updated message from server...now fetch glances");
//                //TODO: Put a delay before making api call for getGlanceUpdates to avoid herd effect on server
//
//            }
//        });
//
//        System.out.println("Application started");
    }

}
