package org.mesibo.messenger;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.mesibo.api.Mesibo;
public class AutoStart extends BroadcastReceiver
{
    @Override
    public void onReceive(Context context, Intent intent)
    {
        if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED) ||
                intent.getAction().equals(MainApplication.getRestartIntent())) {
            StartUpActivity.newInstance(context, true);
        }
    }
}