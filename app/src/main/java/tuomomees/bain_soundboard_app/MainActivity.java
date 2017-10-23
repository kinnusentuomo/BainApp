package tuomomees.bain_soundboard_app;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements BainSoundPlayerThread.MediaPlayerThreadInterface{

    BainSoundPlayerThread bainSoundPlayerThread = null;
    int resources; //Toistettava tiedosto
    int buttonId;
    Button button;
    TextView bainPhraseTextView;
    ListView listView;
    CustomListViewAdapter customListViewAdapterdapter;
    CustomDialogClass cdd;

    String bainPhraseString;

    int listId;

    Boolean isThreadPlaying = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bainPhraseTextView = (TextView) findViewById(R.id.bainPhraseTextView);
        bainPhraseTextView.setSelected(true);

        //LISTVIEW TESTI
        listView = (ListView) findViewById(R.id.buttonListView);
        String[] items={"1","2","3","4","5", "6", "7", "8"}; //ROW AMOUNT
        customListViewAdapterdapter = new CustomListViewAdapter(this, R.layout.listview_item ,R.id.textViewInList, items);
        listView.setAdapter(customListViewAdapterdapter);
        //END

        cdd=new CustomDialogClass(this);

        //Asettaa äänensäännön applikaatioista tulevaan ääneen, eikä hälytysääneen
        setVolumeControlStream(AudioManager.STREAM_MUSIC);

        //Tekee notification barista läpinäkyvän
        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void onBainButtonClicked(View v)
    {

        if(!isThreadPlaying)
        {
        Button bt=(Button)v;
        int id = bt.getId();
        switch (id)
        {
            case 1:
                resources = R.raw.bain_alarm;
                bainPhraseString = "That's the alarm, time to do some heavy lifting.";
                break;
            case 2:
                resources = R.raw.bain_boss;
                bainPhraseString = "Show them whos boss.";
                break;
            case 3:
                resources = R.raw.bain_busy;
                bainPhraseString = "Looks like we're about to get busy.";
                break;
            case 4:
                resources = R.raw.bain_cleanercosts;
                bainPhraseString = "Don't kill civilians.";
                break;
            case 5:
                resources = R.raw.bain_flashlight;
                bainPhraseString = "Stay out of the flashlights people.";
                break;
            case 6:
                resources = R.raw.bain_go;
                bainPhraseString = "Come on guys go!";
                break;
            case 7:
                resources = R.raw.bain_guys;
                bainPhraseString = "Thats my guys.";
                break;
            case 8:
                resources = R.raw.bain_here_we_go;
                bainPhraseString = "Here we go.";
                break;
            case 9:
                resources = R.raw.bain_hostage;
                bainPhraseString = "Alright, let the hostage go and the trade is done.";
                break;
            case 10:
                resources = R.raw.bain_hostage2;
                bainPhraseString = "a hostage.";
                break;
            case 11:
                resources = R.raw.bain_let_the_hostage_go;
                bainPhraseString = "Let the hostage go and the trade is done.";
                break;
            case 12:
                resources = R.raw.bain_loot;
                bainPhraseString = "Don't let them take your loot.";
                break;
            case 13:
                resources = R.raw.bain_sniper;
                bainPhraseString = "Sniper rooftop.";
                break;
            case 14:
                resources = R.raw.bain_yes;
                bainPhraseString = "YES.";
                break;
            case 15:
                resources = R.raw.bain_two;
                bainPhraseString = "That's two one more to go.";
                break;
            case 16:
                resources = R.raw.bain_escape;
                bainPhraseString = "Vans here, say it again the escape vans here.";
                break;
            case 17:
                resources = R.raw.bain_needcivilians;
                bainPhraseString = "Remember guys we need ____ civilians.";
                break;
            case 18:
                resources = R.raw.bain_civilians;
                bainPhraseString = "Civilians.";
                break;
            default:
                resources = R.raw.bain_civilians;
                bainPhraseString = "Civilians.";
                break;
        }

        buttonId = v.getId();
        setButtonPlaying(true, buttonId);
        Log.d("Button pressed", String.valueOf(v.getId()));

        /*
        switch (v.getId()){
            case R.id.buttonBain1:
                resources = R.raw.bain_alarm;
                bainPhraseString = "That's the alarm, time to do some heavy lifting.";
                break;
            case R.id.buttonBain2:
                resources = R.raw.bain_boss;
                bainPhraseString = "Show them whos boss.";
                break;
            case R.id.buttonBain3:
                resources = R.raw.bain_busy;
                bainPhraseString = "Looks like we're about to get busy.";
                break;
            case R.id.buttonBain4:
                resources = R.raw.bain_cleanercosts;
                bainPhraseString = "Don't kill civilians.";
                break;
            case R.id.buttonBain5:
                resources = R.raw.bain_flashlight;
                bainPhraseString = "Stay out of the flashlights people.";
                break;
            case R.id.buttonBain6:
                resources = R.raw.bain_go;
                bainPhraseString = "Come on guys go!";
                break;
            case R.id.buttonBain7:
                resources = R.raw.bain_guys;
                bainPhraseString = "Thats my guys.";
                break;
            case R.id.buttonBain8:
                resources = R.raw.bain_here_we_go;
                bainPhraseString = "Here we go.";
                break;
            case R.id.buttonBain9:
                resources = R.raw.bain_hostage;
                bainPhraseString = "Alright, let the hostage go and the trade is done.";
                break;
            case R.id.buttonBain10:
                resources = R.raw.bain_hostage2;
                bainPhraseString = "a hostage.";
                break;
            case R.id.buttonBain11:
                resources = R.raw.bain_let_the_hostage_go;
                bainPhraseString = "Let the hostage go and the trade is done.";
                break;
            case R.id.buttonBain12:
                resources = R.raw.bain_loot;
                bainPhraseString = "Don't let them take your loot.";
                break;
            case R.id.buttonBain13:
                resources = R.raw.bain_sniper;
                bainPhraseString = "Sniper rooftop.";
                break;
            case R.id.buttonBain14:
                resources = R.raw.bain_yes;
                bainPhraseString = "YES.";
                break;
            case R.id.buttonBain15:
                resources = R.raw.bain_two;
                bainPhraseString = "That's two one more to go.";
                break;
            case R.id.buttonBain16:
                resources = R.raw.bain_escape;
                bainPhraseString = "Vans here, say it again the escape vans here.";
                break;
            case R.id.buttonBain17:
                resources = R.raw.bain_needcivilians;
                bainPhraseString = "Remember guys we need ____ civilians.";
                break;
            case R.id.buttonBain18:
                resources = R.raw.bain_civilians;
                bainPhraseString = "Civilians.";
                break;

            default:
                resources = R.raw.bain_cleanercosts;
                bainPhraseString = "Don't kill civilians. (DEFAULT)";
        }
        */

        bainPhraseTextView.setText(bainPhraseString);

            if(bainSoundPlayerThread == null)
            {
                bainSoundPlayerThread = new BainSoundPlayerThread(this, resources, this, buttonId, listId);
                bainSoundPlayerThread.setRunning(true);
                bainSoundPlayerThread.start();
                Log.d("Starting thread", "bainSPThread");
            }

            else
            {
                bainSoundPlayerThread.setRunning(false);
                bainSoundPlayerThread.interrupt();
                bainSoundPlayerThread = new BainSoundPlayerThread(this, resources, this, buttonId, listId);
                bainSoundPlayerThread.start();
            }
        }

    }

    public void onAboutButtonClicked(View v)
    {
        cdd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        cdd.getWindow().setWindowAnimations(R.style.dialog_animation_fade);
        cdd.show();
    }

    public void setButtonPlaying(final boolean isButtonPlaying, final int btnId)
    {
        this.runOnUiThread(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void run() {
                boolean running = true;
                Drawable icon;
                while(running)
                {
                    button = (Button) findViewById(btnId);
                        if(isButtonPlaying)
                        {
                            isThreadPlaying = true;
                            //button.setBackground(playingOn);
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                icon = getResources().getDrawable(R.drawable.play_icon);
                                button.setForeground(icon);
                            }
                        }
                        else
                        {
                            //button.setForeground();

                            isThreadPlaying = false;
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                button.setForeground(null);
                                bainPhraseTextView.setText("");
                            }
                        }

                    running = false;
                }
            }
        });
    }
}
