package tuomomees.bain_soundboard_app;

import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.io.InputStream;
import java.lang.reflect.Field;
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
    ArrayList<Integer> audioList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Hakee kaikki RAW -tiedostot listaan
        checkRawResources();

        bainPhraseTextView = findViewById(R.id.bainPhraseTextView);
        bainPhraseTextView.setSelected(true);

        buttonListViewAdapter = new ButtonListViewAdapter(this, generateData());

        //Alustetaan listview käyttöön
        listView = findViewById(R.id.buttonListView);
        listView.setAdapter(buttonListViewAdapter);
        cdd=new CustomDialogClass(this);

        //Asettaa äänensäännön applikaatioista tulevaan ääneen, eikä hälytysääneen
        setVolumeControlStream(AudioManager.STREAM_MUSIC);

        //Tekee notification barista läpinäkyvän
        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }


    public void checkRawResources()
    {
        audioList = new ArrayList<>();
        Field[] raws = R.raw.class.getFields();
        for (Field f : raws) {
            //if the raw name contains "bain" in the filename...
            //if (f.getName().contains("bain"))
            audioList.add(getResources().getIdentifier(f.getName(), "raw", getPackageName()));
            Log.d("RAW", String.valueOf(getResources().getIdentifier(f.getName(), "raw", getPackageName())));
        }
    }

    public void onBainButtonClicked(View v)
    {
        if(!isThreadPlaying)
        {
        Button bt=(Button)v;
        int id = bt.getId();

        if(id < audioList.size())
        {
            resources = audioList.get(id);
        }
        else
        {
            resources = R.raw.bain_looks_like_we_are_about_to_get_busy;
        }

        buttonId = v.getId();
        setButtonPlaying(true, buttonId);
        Log.d("Button pressed", String.valueOf(v.getId()));
        bainPhraseString = getResources().getResourceName(resources);
        bainPhraseString = bainPhraseString.replace("_i_", "_I_");
        bainPhraseString = bainPhraseString.replace("tuomomees.bain_soundboard_app:raw/bain", "");
        bainPhraseString = bainPhraseString.replace("_", " ");
        bainPhraseString = bainPhraseString.substring(1, 2).toUpperCase() + bainPhraseString.substring(2);
        bainPhraseString = bainPhraseString + ".";

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
                    button = findViewById(btnId);
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

                                //bainPhraseTextView.setText("");
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

        models.add(new RowItemModel(28,29,30));
        models.add(new RowItemModel(31,32,33));
        models.add(new RowItemModel(34,35,36));
        models.add(new RowItemModel(37,38,39));
        models.add(new RowItemModel(40,41,42));
        models.add(new RowItemModel(43,44,45));

        return models;
    }
}
