package tuomomees.bain_soundboard_app;

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
