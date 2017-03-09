package sample.glance.inmobi.com.firebasewakeup.wakeup;

public interface WakeupReceiver {

    void start(WakeupCallback wakeupCallback);

    void setRegion(String region);

    void stop();

    interface WakeupCallback {

        void onWakeup();

    }
}
