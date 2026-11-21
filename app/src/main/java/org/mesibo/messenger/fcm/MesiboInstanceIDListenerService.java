package org.mesibo.messenger.fcm;
import com.google.firebase.messaging.FirebaseMessagingService;
public class MesiboInstanceIDListenerService extends FirebaseMessagingService {
    private static final String TAG = "MyInstanceIDLS";
    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        MesiboRegistrationIntentService.startRegistration(this, null, null);
    }
}