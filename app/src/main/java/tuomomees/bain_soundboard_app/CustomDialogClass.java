package tuomomees.bain_soundboard_app;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by tuomo on 10.10.2017.
 */


class CustomDialogClass extends Dialog implements
        android.view.View.OnClickListener {

    private Activity c;
    public Dialog d;
    private Button yes, no;

    CustomDialogClass(Activity a) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_dialog);
        yes = (Button) findViewById(R.id.btn_yes);
        no = (Button) findViewById(R.id.btn_no);
        yes.setOnClickListener(this);
        no.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_yes:
                Toast.makeText(c.getApplicationContext(),c.getResources().getString(R.string.support_text), Toast.LENGTH_LONG).show();

                String subject = null;
                try {
                    subject = c.getResources().getString(R.string.app_label_text) + c.getPackageManager()
                            .getPackageInfo(c.getPackageName(), 0).versionName;
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
                ;
                String emailAddress = c.getResources().getString(R.string.feedback_address);
                String templateBody = c.getResources().getString(R.string.feedback_template);
                String whichEmailToUse =  c.getResources().getString(R.string.choose_feedback_delivering);

                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto",emailAddress, null));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
                emailIntent.putExtra(Intent.EXTRA_TEXT, templateBody);
                c.startActivity(Intent.createChooser(emailIntent, whichEmailToUse));

                break;
            case R.id.btn_no:
                //Toast.makeText(c.getApplicationContext(), c.getResources().getString(R.string.notsupport_text), Toast.LENGTH_LONG).show();
                break;
            default:
                break;
        }
        dismiss();
    }
}
