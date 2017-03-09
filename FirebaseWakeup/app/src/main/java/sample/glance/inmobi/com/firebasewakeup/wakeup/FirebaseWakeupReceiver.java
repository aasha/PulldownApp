package sample.glance.inmobi.com.firebasewakeup.wakeup;


import android.content.Context;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import timber.log.Timber;

public class FirebaseWakeupReceiver implements WakeupReceiver {

    private FirebaseDatabase firebaseDatabase;
    private FirebaseValueEventListener firebaseValueEventListener;
   // private static final String FIREBASE_WAKEUP_PATH = "/glance/prod/updates/%s/%s";
   private static final String FIREBASE_WAKEUP_PATH = "/glance/dev/updates/%s/%s";

    WakeupCallback callback;
    private Context context;
    private String apiKey;
    private String region;

    public FirebaseWakeupReceiver(Context context, String apiKey) {
        this.context = context;
        this.apiKey = apiKey;
    }

    @Override
    public void setRegion(String region) {
        this.region = region;
    }

    @Override
    public void start(WakeupCallback wakeupCallback) {
        List<FirebaseApp> firebaseApps = FirebaseApp.getApps(context);
        FirebaseApp firebaseApp = null;
        if (firebaseApps == null || firebaseApps.size() == 0) {
            FirebaseOptions firebaseOptions = new FirebaseOptions.Builder()
                    .setApiKey("AIzaSyCih5R14UdFXZmUqgPRpSLxaSu5PaQuChQ")
                    .setApplicationId(context.getPackageName()).
                            setDatabaseUrl("https://vault-24754.firebaseio.com")
                    .setStorageBucket("vault-24754.appspot.com").build();
            firebaseApp = FirebaseApp.initializeApp(context, firebaseOptions);
        }
        if (firebaseApp == null) {
            firebaseDatabase = FirebaseDatabase.getInstance();
        } else {
            firebaseDatabase = FirebaseDatabase.getInstance(firebaseApp);
        }
        firebaseValueEventListener = new FirebaseValueEventListener();
        firebaseDatabase.getReference(String.format(FIREBASE_WAKEUP_PATH, apiKey, region))
                .addValueEventListener(firebaseValueEventListener);
        this.callback = wakeupCallback;
    }

    public void stop() {
        if (firebaseDatabase != null && firebaseValueEventListener != null) {
            firebaseDatabase.getReference(String.format(FIREBASE_WAKEUP_PATH, apiKey, region))
                    .removeEventListener(firebaseValueEventListener);
        }
        this.callback = null;
    }

    class FirebaseValueEventListener implements ValueEventListener {

        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            Long updatedAt = (Long) dataSnapshot.getValue();
            System.out.println("FirebaseWakeup onDataChange : " + updatedAt);
            if (callback != null) {
                callback.onWakeup();
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    }
}
