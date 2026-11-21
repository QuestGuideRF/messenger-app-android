package org.mesibo.messenger.fcm;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import androidx.core.app.JobIntentService;
import android.widget.Toast;
public class MesiboJobIntentService extends JobIntentService {
    static final int JOB_ID = 1000;
    static void enqueueWork(Context context, Intent work) {
        try {
        enqueueWork(context, MesiboJobIntentService.class, JOB_ID, work);
        } catch (Exception e) {
        }
    }
    @Override
    protected void onHandleWork(Intent intent) {
        try {
        MesiboRegistrationIntentService.sendMessageToListener( true);
        } catch (Exception e) {
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
    }
    final Handler mHandler = new Handler();
    void toast(final CharSequence text) {
        mHandler.post(new Runnable() {
            @Override public void run() {
                Toast.makeText(MesiboJobIntentService.this, text, Toast.LENGTH_SHORT).show();
            }
        });
    }
}