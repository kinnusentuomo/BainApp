package tuomomees.bain_soundboard_app;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

/**
 * Luokan on luonut tuomo päivämäärällä 12.11.2017.
 */


class CustomMenuDialog extends Dialog implements
        android.view.View.OnClickListener {

    private Activity activity;
    private Context context;
    private int resourceId;
    private NotificationAndRingtoneHandler notificationAndRingtoneHandler;

    CustomMenuDialog(Activity a, Context context, int resourceId) {
        super(a);
        this.activity = a;
        this.context = context;
        this.resourceId = resourceId;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_menu_dialog);
        Button ringtone = findViewById(R.id.btn_ringtone);
        Button notification_sound = findViewById(R.id.btn_notification_sound);
        Button share = findViewById(R.id.btn_share);
        ringtone.setOnClickListener(this);
        notification_sound.setOnClickListener(this);
        share.setOnClickListener(this);

        notificationAndRingtoneHandler = new NotificationAndRingtoneHandler(context, activity);

        try
        {
            getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            getWindow().setWindowAnimations(R.style.dialog_animation_fade);
        }
        catch (NullPointerException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_ringtone:
                notificationAndRingtoneHandler.saveas(resourceId);
                notificationAndRingtoneHandler.startRingtonePicker(resourceId);
                break;
            case R.id.btn_notification_sound:
                notificationAndRingtoneHandler.saveas(resourceId);
                notificationAndRingtoneHandler.startNotificationPicker(resourceId);
                break;
            case R.id.btn_share:
                ResourceShareHandler resourceShareHandler = new ResourceShareHandler(context);
                resourceShareHandler.shareResource(resourceId);
                break;
            default:
                break;
        }
        dismiss();
    }
}
