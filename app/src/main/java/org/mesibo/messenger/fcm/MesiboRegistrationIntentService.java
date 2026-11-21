package org.mesibo.messenger.fcm;
import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.core.app.JobIntentService;
import android.text.TextUtils;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.firebase.FirebaseApp;
import com.google.firebase.iid.FirebaseInstanceId;
import java.io.IOException;
public class MesiboRegistrationIntentService extends JobIntentService {
    private static final String TAG = "RegIntentService";
    private static final String[] TOPICS = {"global"};
    private static String SENDER_ID= "";
    private static GCMListener mListener = null;
    public MesiboRegistrationIntentService() {
        super();
    }
    public interface GCMListener {
        void Mesibo_onGCMToken(String token);
        void Mesibo_onGCMMessage( boolean inService);
    }
    @Override
    protected void onHandleWork(@NonNull Intent intent) {
      onHandleIntent(intent);
    }
    protected void onHandleIntent(Intent intent) {
        String token = null;
        try {
            FirebaseInstanceId instanceID = (FirebaseInstanceId) FirebaseInstanceId.getInstance(FirebaseApp.initializeApp(this));
            if(null == instanceID) {
                Log.d(TAG, "Ошибка получения экземпляра FCM");
                return;
            }
          token = instanceID.getToken();
            Log.d(TAG, "Регистрационный токен FCM: " + token);
        } catch (Exception e) {
            Log.d(TAG, "Не удалось завершить обновление токена.", e);
        }
        if(null != mListener) {
            mListener.Mesibo_onGCMToken(token);
        }
    }
    private void sendRegistrationToServer(String token) {
        Log.d("Token", token);
    }
    private void subscribeTopics(String token) throws IOException {
    }
    private boolean checkPlayServices() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);
        return (resultCode == ConnectionResult.SUCCESS);
    }
    public static final int JOB_ID = 1;
    public static void enqueueWork(Context context, Intent work) {
        enqueueWork(context, MesiboRegistrationIntentService.class, JOB_ID, work);
    }
    public static void startRegistration(Context context, String senderId, GCMListener listener) {
        if(!TextUtils.isEmpty(senderId))
            SENDER_ID = senderId;
        if(listener != null)
            mListener = listener;
        try {
            Intent intent = new Intent(context, MesiboRegistrationIntentService.class);
            enqueueWork(context, intent);
        } catch (Exception e) {
        }
    }
    public static void sendMessageToListener( boolean inService) {
        if(null != mListener) {
            mListener.Mesibo_onGCMMessage( inService);
        }
    }
}