package org.mesibo.messenger;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import com.mesibo.api.Mesibo;
import com.mesibo.messaging.MesiboUI;
import com.mesibo.uihelper.MesiboUiHelperConfig;
public class StartUpActivity extends AppCompatActivity {
    private static final String TAG = "MesiboStartupActivity";
    public final static String INTENTEXIT="exit";
    public final static String SKIPTOUR="skipTour";
    public final static String STARTINBACKGROUND ="startinbackground";
    private boolean mRunInBackground = false;
    private boolean mPermissionAlert = false;
    public static void newInstance(Context context, boolean startInBackground) {
        Intent i = new Intent(context, StartUpActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.putExtra(StartUpActivity.STARTINBACKGROUND, startInBackground);
        context.startActivity(i);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getIntent().getBooleanExtra(INTENTEXIT, false)) {
            Log.d(TAG, "onCreate закрытие");
            finish();
            return;
        }
        mRunInBackground = getIntent().getBooleanExtra(STARTINBACKGROUND, false);
        long elapsed = Mesibo.getTimestamp() - Mesibo.getStartTimestamp();
        Log.e(TAG, "Elapsed " + elapsed + ", " + (mRunInBackground?"background":"foreground"));
        if(elapsed > 30000)
            mRunInBackground = false;
        if(mRunInBackground) {
            Log.e(TAG, "Перенос приложения в фоновый режим");
            moveTaskToBack(true);
        } else {
            Log.e(TAG, "Не перемещает приложение в фоновый режим");
        }
        setContentView(R.layout.activity_blank_launcher);
        startNextActivity();
    }
    void startNextActivity() {
        if(TextUtils.isEmpty(SampleAPI.getToken())) {
            MesiboUiHelperConfig.mDefaultCountry = 1;
            MesiboUiHelperConfig.mPhoneVerificationBottomText = "Обратите внимание: Mesibo может позвонить вместо отправки SMS, если не удалось доставить SMS на ваш телефон.";
            if(getIntent().getBooleanExtra(SKIPTOUR, false)) {
                UIManager.launchLogin(this, MesiboListeners.getInstance());
            } else {
                UIManager.launchWelcomeactivity(this, true, MesiboListeners.getInstance(), MesiboListeners.getInstance());
            }
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        } else {
            MesiboUI.MesiboUserListScreenOptions opts = new MesiboUI.MesiboUserListScreenOptions();
            opts.keepRunning = true;
            opts.startInBackground = mRunInBackground;
            UIManager.launchMesibo(this, opts);
        }
        finish();
    }
    @Override
    protected void onNewIntent(Intent intent) {
        Log.d(TAG, "onNewIntent");
        if(intent.getBooleanExtra(INTENTEXIT, false)) {
            finish();
        }
        super.onNewIntent(intent);
    }
}