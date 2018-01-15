package tuomomees.bain_soundboard_app;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements SoundPlayerThread.MediaPlayerThreadInterface{

    Context context;

    //Mainokset
    int buttonClickCounter = 0;
    //ca-app-pub-4117339910887954~6776256683
    AdHandler adHandler;

    //Painikeriveistä muodostuva lista
    ArrayList<RowItemModel> models = new ArrayList<>();
    ButtonListViewAdapter buttonListViewAdapter; //Adapteri

    SoundPlayerThread soundPlayerThread = null;
    int resources; //Toistettava tiedosto
    int buttonId;
    Button button;
    TextView bainPhraseTextView;
    ListView listView;
    CustomAboutDialogClass cdd;

    String bainPhraseString;

    int listId;

    Boolean isThreadPlaying = false;
    ArrayList<Integer> audioList;
    int cellAmount = 3;

    Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        activity = this;
        adHandler = new AdHandler(context);

        checkRequiredPermission(); //Tarkistaa onko sovelluksella tarvittavat oikeudet
        checkRawResources();//Hakee kaikki RAW -tiedostot listaan
        initializeWidgets(); //Alustetaan VIEW -elementit käyttöön
    }

    public void initializeWidgets()
    {
        bainPhraseTextView = findViewById(R.id.bainPhraseTextView);
        bainPhraseTextView.setSelected(true);
        buttonListViewAdapter = new ButtonListViewAdapter(this, generateData());

        listView = findViewById(R.id.buttonListView); //Alustetaan listview käyttöön
        listView.setAdapter(buttonListViewAdapter);
        cdd=new CustomAboutDialogClass(this);

        setVolumeControlStream(AudioManager.STREAM_MUSIC); //Asettaa äänensäännön applikaatioista tulevaan ääneen, eikä hälytysääneen

        Window w = getWindow(); //Tekee notification barista läpinäkyvän
        w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }

    @SuppressLint("NewApi") //Tarkistaa onko sovelluksella oikeus kirjoittaa ulkoiseen tallennustilaan
    public void checkRequiredPermission()
    {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            Log.d("Sovelluksella on ", "tarvittavat oikeudet");
        }
        else
        {
            int REQUEST_CODE = 1;
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_SETTINGS) == PackageManager.PERMISSION_DENIED)
            if (!Settings.System.canWrite(getApplicationContext())) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS, Uri.parse("package:" + getPackageName()));
                startActivityForResult(intent, 200);
            }
        }
    }

    public void checkRawResources()
    {
        audioList = new ArrayList<>();
        Field[] raws = R.raw.class.getFields();
        for (Field f : raws) {
            //if the raw name contains "bain" in the filename...
            //if (f.getName().contains("bain"))
            audioList.add(getResources().getIdentifier(f.getName(), "raw", getPackageName()));
        }

        Log.d("RawID", audioList.toString());
        Log.d("AudioListSize", String.valueOf(audioList.size()));
    }

    public void onBainButtonClicked(View v)
    {
        final Context context = this;

        @SuppressLint("ResourceType") Button button12 = findViewById(12);
        Log.d("Button_", String.valueOf(button12));

        final Button button = findViewById(v.getId());
        final String btnText = String.valueOf(button.getText());
        button.setOnLongClickListener(new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            // TODO Auto-generated method stub
            Log.d("Long press", btnText);
            //ResourceShareHandler resourceShareHandler = new ResourceShareHandler(context);
            //resourceShareHandler.shareResource(resources);

            CustomMenuDialog customMenuDialog = new CustomMenuDialog(activity, context, resources);
            customMenuDialog.show();
            return true;
        }
        });


        if(!isThreadPlaying)
        {
        //Laskuri, jotta saadaan mainokset näkymään joka 5. kerta kun klikataan painiketta
        buttonClickCounter++;
        Button bt=(Button)v;
        int id = bt.getId();

        if(id < audioList.size())
        {
            resources = audioList.get(id);
        }
        else
        {
            resources = R.raw.bain_aww_shit_thats_it;
        }

        buttonId = v.getId();
        setButtonPlaying(true, buttonId);
        Log.d("Button pressed", String.valueOf(v.getId()));
        //bainPhraseString = getResources().getResourceName(resources);
        bainPhraseString = getResources().getResourceEntryName(resources);
        bainPhraseString = formatString(bainPhraseString);

        bainPhraseTextView.setText(bainPhraseString);

            if(soundPlayerThread == null)
            {
                soundPlayerThread = new SoundPlayerThread(this, resources, this, buttonId, listId);
                soundPlayerThread.setRunning(true);
                soundPlayerThread.start();
                Log.d("Starting thread", "bainSPThread");
            }

            else
            {
                soundPlayerThread.setRunning(false);
                soundPlayerThread.interrupt();
                soundPlayerThread = new SoundPlayerThread(this, resources, this, buttonId, listId);
                soundPlayerThread.start();
            }
        }
    }

    public String formatString(String str)
    {
        str = str.replace("bain_", "");
        str = str.replace("_i_", "_I_");
        str = str.replace("_thats_", "_that's_");
        str = str.replace("_swat_", "_SWAT_");
        str = str.replace("_didnt_", "_didn't_");
        str = str.replace("_im_", "_I'm_");
        str = str.replace("dont", "don't");
        //str = str.replace("tuomomees.bain_soundboard_app:raw/bain", "");
        str = str.replace("_", " ");
        str = str.substring(0, 1).toUpperCase() + str.substring(1);
        str = str.replace("Plan b", "Plan B");
        str = str + ".";

        return str;
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

                                if(buttonClickCounter % 5 == 0)
                                {
                                    //adHandler.showAd();
                                }

                                //bainPhraseTextView.setText("");
                            }
                        }
                    running = false;
                }
            }
        });
    }

    //Metodi, jolla lisätään tarvittavat rivit listaan
    public ArrayList<RowItemModel> generateData(){

        //rowAmount = montako riviä painikkeita on
        //cellAmount = montako painiketta on yhdessä rivissä
        //buttonAmount = rowAmount * cellAmount kokonaispainikkeiden määrä

        int rowAmount = audioList.size() / cellAmount;

        Log.d("Painikelistan koko", rowAmount + "x" + cellAmount);

        ArrayList<Integer> array = new ArrayList<>();
        //array.add(1);
        int limiter;
        int id1 = 0, id2 = 0, id3 = 0;
        String buttonText1 = "", buttonText2 = "", buttonText3 = "";

        //i = rowAmount : j = cellAmount
        for(int i = 1; i <= rowAmount; i++){
            int counter = 0;
            limiter = array.size() + cellAmount;
            //Log.d("I", String.valueOf(i));
            for(int j = array.size(); j < limiter; j++){

                //Log.d("J", String.valueOf(j));
                array.add(j);
                counter = counter + 1;

                //Log.d("counter", String.valueOf(counter));
                switch (counter)
                {
                    case 1:
                        id1 = array.get(j);
                        //buttonText1 = getResources().getResourceName(audioList.get(j));
                        buttonText1 = getResources().getResourceEntryName(audioList.get(j));
                        break;

                    case 2:
                        id2 = array.get(j);
                        buttonText2 = getResources().getResourceEntryName(audioList.get(j));
                        break;

                    case 3:
                        id3 = array.get(j);
                        buttonText3 = getResources().getResourceEntryName(audioList.get(j));
                        break;
                }
            }

            buttonText1 = formatString(buttonText1);
            buttonText2 = formatString(buttonText2);
            buttonText3 = formatString(buttonText3);
            models.add(new RowItemModel(id1, id2, id3, buttonText1, buttonText2, buttonText3));
        }
        return models;
    }
}
