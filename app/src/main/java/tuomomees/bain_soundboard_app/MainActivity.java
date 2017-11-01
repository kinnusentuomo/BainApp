package tuomomees.bain_soundboard_app;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements BainSoundPlayerThread.MediaPlayerThreadInterface{


    //Painikeriveistä muodostuva lista
    ArrayList<RowItemModel> models = new ArrayList<>();
    ButtonListViewAdapter buttonListViewAdapter; //Adapteri

    BainSoundPlayerThread bainSoundPlayerThread = null;
    int resources; //Toistettava tiedosto
    int buttonId;
    Button button;
    TextView bainPhraseTextView;
    ListView listView;
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

        buttonListViewAdapter = new ButtonListViewAdapter(this, generateData());

        //Alustetaan listview käyttöön
        listView = (ListView) findViewById(R.id.buttonListView);
        listView.setAdapter(buttonListViewAdapter);
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
            case 0:
                resources = R.raw.bain_civilians;
                bainPhraseString = "Civilians.";
                break;
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
            case 19:
                resources = R.raw.bain_you_guys_are_killing_it_there_snipers_are_brought_in_people;
                bainPhraseString = "You guys are killing it in there, snipers are brought in people.";
                break;
            case 20:
                resources = R.raw.bain_we_are_trying_our_luck_out_here;
                bainPhraseString = "We are trying our luck out here";
                break;
            case 21:
                resources = R.raw.bain_two_minutes;
                bainPhraseString = "Two minutes!";
                break;
            case 22:
                resources = R.raw.bain_they_dont_want_to_this_be_over_sniper_are_in_the_surrounding_buildings;
                bainPhraseString = "They dont want to this to be over, sniper are in the surrounding_buildings";
                break;
            case 23:
                resources = R.raw.bain_they_brought_in_snipers;
                bainPhraseString = "They brought in snipers.";
                break;
            case 24:
                resources = R.raw.bain_they_are_coming;
                bainPhraseString = "They are coming.";
                break;
            case 25:
                resources = R.raw.bain_police_heli_coming_in;
                bainPhraseString = "Police  heli coming in.";
                break;
            case 26:
                resources = R.raw.bain_get_the_bag_on_the_van_guys;
                bainPhraseString = "Get the bag on the van guys.";
                break;
            case 27:
                resources = R.raw.bain_come_on_come_on_only_couple_of_seconds_left_guys_police_are_on_their_way;
                bainPhraseString = "Come on come on only couple of seconds left guys police are on their way";
                break;
            default:
                resources = R.raw.bain_civilians;
                bainPhraseString = "Civilians.";
                break;
        }

        buttonId = v.getId();
        setButtonPlaying(true, buttonId);
        Log.d("Button pressed", String.valueOf(v.getId()));
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

        cdd.show();
    }

    public void setButtonPlaying(final boolean isButtonPlaying, final int btnId)
    {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                boolean running = true;
                Drawable icon = ResourcesCompat.getDrawable(getResources(), R.drawable.play_icon, null);
                while(running)
                {
                    button = (Button) findViewById(btnId);
                        if(isButtonPlaying)
                        {
                            isThreadPlaying = true;
                                //icon = getResources().getDrawable(R.drawable.play_icon); //Vanhentunut metodi
                                try
                                {
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                        button.setForeground(icon);
                                    }
                                }
                                catch (NullPointerException e)
                                {
                                    e.printStackTrace();
                                }

                        }
                        else
                        {
                            isThreadPlaying = false;
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                try
                                {
                                    button.setForeground(new ColorDrawable(Color.TRANSPARENT));
                                }
                                catch (NullPointerException e)
                                {
                                    e.printStackTrace();
                                }

                                bainPhraseTextView.setText("");
                                boolean isEllipsize = !((bainPhraseTextView.getLayout().getText().toString()).equalsIgnoreCase(bainPhraseString));
                            }
                        }
                    running = false;
                }
            }
        });
    }

    //Metodi, jolla lisätään tarvittavat rivit listaan
    public ArrayList<RowItemModel> generateData(){

        models.add(new RowItemModel(1, 2, 3));
        models.add(new RowItemModel(4, 5, 6));
        models.add(new RowItemModel(7, 8, 9));
        models.add(new RowItemModel(10, 11, 12));
        models.add(new RowItemModel(13, 14, 15));
        models.add(new RowItemModel(16, 17, 18));
        models.add(new RowItemModel(19, 20, 21));
        models.add(new RowItemModel(22, 23, 24));
        models.add(new RowItemModel(25,26,27));
        return models;
    }
}
