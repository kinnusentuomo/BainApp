package tuomomees.bain_soundboard_app;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Luokan on luonut tuomo päivämäärällä 11.11.2017.
 */
//Luokka, jonka tarkoituksena on käsitellä tarvittavat tiedostojen jaot
class ResourceShareHandler {

    private Context context;

    ResourceShareHandler(Context c)
    {
        this.context = c;
    }

    //Metodi, jolla voi jakaa halutun RAW resurssin
    void shareResource(int resource)
    {
        try {
            //Copy file to external ExternalStorage.
            //String mediaPath = copyFiletoExternalStorage(R.raw.bain_careful_now, "bain_careful_now.ogg");
            String mediaPath = copyFiletoExternalStorage(resource, context.getResources().getResourceEntryName(resource) + ".ogg");

            Intent shareMedia = new Intent(Intent.ACTION_SEND);
            //set WhatsApp application.
            //shareMedia.setPackage("com.whatsapp");
            shareMedia.setType("audio/ogg");
            //set path of media file in ExternalStorage.
            shareMedia.putExtra(Intent.EXTRA_STREAM, Uri.parse(mediaPath));
            context.startActivity(Intent.createChooser(shareMedia, context.getResources().getString(R.string.share_audio_text)));
        } catch (Exception e) {
            Toast.makeText(context.getApplicationContext(), context.getResources().getString(R.string.share_audio_error_text), Toast.LENGTH_LONG).show();
        }
    }

    private String copyFiletoExternalStorage(int resourceId, String resourceName){
        String pathSDCard = Environment.getExternalStorageDirectory() + "/Android/data/" + resourceName;
        try{
            InputStream in = context.getResources().openRawResource(resourceId);
            FileOutputStream out = new FileOutputStream(pathSDCard + resourceName);

            byte[] buff = new byte[1024];
            int read = 0;
            try {
                while ((read = in.read(buff)) > 0) {
                    out.write(buff, 0, read);
                }
            } finally {
                in.close();
                out.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  pathSDCard;
    }

}
