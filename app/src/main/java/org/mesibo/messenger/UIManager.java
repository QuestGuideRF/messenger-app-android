package org.mesibo.messenger;
import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.mesibo.mediapicker.AlbumListData;
import com.mesibo.mediapicker.MediaPicker;
import org.mesibo.messenger.AppSettings.SettingsActivity;
import com.mesibo.uihelper.MesiboLoginUiHelperListener;
import com.mesibo.uihelper.IProductTourListener;
import com.mesibo.uihelper.MesiboUiHelper;
import com.mesibo.uihelper.MesiboUiHelperConfig;
import com.mesibo.uihelper.WelcomeScreen;
import com.mesibo.messaging.MesiboUI;
import java.util.ArrayList;
import java.util.List;
public class UIManager {
    public static void launchStartupActivity(Context context, boolean skipTour) {
        Intent intent = new Intent(context, StartUpActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(StartUpActivity.SKIPTOUR, skipTour);
        context.startActivity(intent);
    }
    public static boolean mMesiboLaunched = false;
    public static void launchMesibo(Context context, MesiboUI.MesiboUserListScreenOptions opts) {
        mMesiboLaunched = true;
        MesiboUI.launchUserList(context, opts);
    }
    public static void launchUserSettings(Context context) {
        Intent intent = new Intent(context, SettingsActivity.class);
        context.startActivity(intent);
    }
    public static void launchEditProfile(Context context, int flag, long groupid, boolean launchMesibo) {
        Intent subActivity = new Intent(context, EditProfileActivity.class);
        if(flag > 0)
            subActivity.setFlags(flag);
        subActivity.putExtra("groupid", groupid);
        subActivity.putExtra("launchMesibo", launchMesibo);
        context.startActivity(subActivity);
    }
    public static void launchImageViewer(Activity context, String filePath) {
        MediaPicker.launchImageViewer(context, filePath);
    }
    public static void launchImageEditor(Context context, int type, int drawableid, String title, String filePath, boolean showEditControls, boolean showTitle, boolean showCropOverlay, boolean squareCrop, int maxDimension, MediaPicker.ImageEditorListener listener){
        MediaPicker.launchEditor((AppCompatActivity)context, type, drawableid, title, filePath, showEditControls, showTitle, showCropOverlay, squareCrop, maxDimension, listener);
    }
    public static boolean mProductTourShown = false;
    public static void initUiHelper() {
        MesiboUiHelperConfig config = new MesiboUiHelperConfig();
        List<WelcomeScreen> res = new ArrayList<WelcomeScreen>();
        res.add(new WelcomeScreen("Сообщения и звонки в ваших приложениях", "Добавьте обмен сообщениями, видео- и голосовые звонки и конференции в свои приложения в кратчайшие сроки. Mesibo создан с нуля, чтобы обеспечить это!", 0, R.drawable.welcome, 0xff00868b));
        res.add(new WelcomeScreen("Сообщения, голос и видео", "Полная инфраструктура с мощными API, которые помогут вам начать работу прямо сейчас!", 0, R.drawable.videocall, 0xff0f9d58));
        res.add(new WelcomeScreen("Открытый исходный код", "Быстро интегрируйте mesibo в свое приложение, используя свободно доступный исходный код.", 0, R.drawable.opensource_ios, 0xff054a61));
        res.add(new WelcomeScreen("", ":", 0, R.drawable.welcome, 0xff00868b));
        config.mScreens = res;
        config.mWelcomeBottomText = "Mesibo никогда не передаст вашу информацию";
        config.mWelcomeBackgroundColor = 0xff00868b;
        config.mBackgroundColor = 0xffffffff;
        config.mPrimaryTextColor = 0xff172727;
        config.mButttonColor = 0xff00868b;
        config.mButttonTextColor = 0xffffffff;
        config.mSecondaryTextColor = 0xff666666;
        config.mScreenAnimation = true;
        List<String> permissions = new ArrayList<>();
        permissions.add(Manifest.permission.READ_CONTACTS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            permissions.add(Manifest.permission.READ_MEDIA_IMAGES);
            permissions.add(Manifest.permission.READ_MEDIA_AUDIO);
            permissions.add(Manifest.permission.READ_MEDIA_VIDEO);
        } else {
            permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        config.mPermissions = permissions;
        config.mPermissionsRequestMessage = "mesibo требуются разрешения на хранение и контакты, чтобы вы могли отправлять сообщения и звонить своим контактам. Пожалуйста, дайте разрешение на продолжение!";
        config.mPermissionsDeniedMessage = "mesibo сейчас закроется, так как необходимые разрешения не были предоставлены";
        config.mPhoneVerificationBottomText = "IMPORTANT: We will NOT send OTP.  Instead, you can generate OTP for any number from the mesibo console. Sign up at https://mesibo.com/console";
        config.mLoginBottomDescColor = 0xAAFF0000;
        MesiboUiHelper.setConfig(config);
    }
    public static void launchWelcomeactivity(Activity context, boolean newtask, MesiboLoginUiHelperListener loginInterface, IProductTourListener tourListener){
        initUiHelper();
        if(mMesiboLaunched) {
            launchLogin(context, MesiboListeners.getInstance());
            return;
        }
        mProductTourShown = true;
        MesiboUiHelper.launchTour(context, newtask, tourListener);
    }
    public static void launchLogin(Activity context, MesiboLoginUiHelperListener loginInterface){
        initUiHelper();
        MesiboUiHelper.launchLogin(context, true, 2, loginInterface);
    }
    public static void showAlert(Context context, String title, String message, DialogInterface.OnClickListener pl, DialogInterface.OnClickListener nl) {
        if(null == context) {
            return;
        }
        androidx.appcompat.app.AlertDialog.Builder dialog = new androidx.appcompat.app.AlertDialog.Builder(context);
        dialog.setTitle(title);
        dialog.setMessage(message);
        dialog.setCancelable(true);
        dialog.setPositiveButton(android.R.string.ok, pl);
        dialog.setNegativeButton(android.R.string.cancel, nl);
        try {
            dialog.show();
        } catch (Exception e) {
        }
    }
    public static void showAlert(Context context, String title, String message) {
        if(null == context) return;
        showAlert(context, title, message, null, null);
    }
}