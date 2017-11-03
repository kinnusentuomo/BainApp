package tuomomees.bain_soundboard_app;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Luokan on luonut tuomo päivämäärällä 29.10.2017.
 */

public class RowItemModel {

    private String buttonNumber;
    private int buttonId1;
    private int buttonId2;
    private int buttonId3;


    RowItemModel(int buttonId1, int buttonId2, int buttonId3)
    {
        this.buttonId1 = buttonId1;
        this.buttonId2 = buttonId2;
        this.buttonId3 = buttonId3;
    }


    /*
    RowItemModel(int rowAmount, int cellAmount)
    {
        ArrayList<Integer> array = new ArrayList<>();
        array.add(1);

        int limiter;
        //i = rowAmount : j = cellAmount
        for(int i = 1; i <= rowAmount; i++){



            limiter = array.size() + cellAmount;
            Log.d("I", String.valueOf(i));
            for(int j = array.size(); j < limiter; j++){
                Log.d("J", String.valueOf(j));
                array.add(j);

                switch (j / cellAmount)
                {
                    case 1:
                        this.buttonId1 = array.get(j);
                        break;
                    case 2:
                        this.buttonId2 = array.get(j);
                        break;
                    case 3:
                        this.buttonId3 = array.get(j);
                        break;
                }
            }

        }
    }

*/
    public String getButtonNumber() {
        return buttonNumber;
    }

    public void setButtonNumber(String buttonNumber) {
        this.buttonNumber = buttonNumber;
    }

    int getButtonId1() {
        return buttonId1;
    }
    int getButtonId2() {
        return buttonId2;
    }
    int getButtonId3() {
        return buttonId3;
    }

    public void setButtonId(int buttonId) {
        this.buttonId1 = buttonId;
    }
}
