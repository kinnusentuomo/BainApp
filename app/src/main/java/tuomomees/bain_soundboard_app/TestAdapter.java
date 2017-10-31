package tuomomees.bain_soundboard_app;

/**
 * Luokan on luonut tuomo päivämäärällä 29.10.2017.
 */



/**
 * Luokan on luonut tuomo päivämäärällä 22.9.2017.
 */

//LISTVIEWIÄ VARTEN LUOTU ADAPTERI

        import android.content.Context;
        import android.graphics.drawable.Drawable;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ArrayAdapter;
        import android.widget.Button;
        import android.widget.ImageView;
        import android.widget.TextView;

        import java.util.ArrayList;

public class TestAdapter extends ArrayAdapter<RowItemModel> {

    private final Context context;
    private final ArrayList<RowItemModel> modelsArrayList;

    public TestAdapter(Context context, ArrayList<RowItemModel> modelsArrayList) {

        super(context, R.layout.listview_item, modelsArrayList);

        this.context = context;
        this.modelsArrayList = modelsArrayList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // 1. Create inflater
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // 2. Get rowView from inflater

        View rowView;

            rowView = inflater.inflate(R.layout.listview_item, parent, false);

            // 3. Get icon,title & counter views from the rowView

            Button button1 = (Button) rowView.findViewById(R.id.button1);
            Button button2 = (Button) rowView.findViewById(R.id.button2);
            Button button3 = (Button) rowView.findViewById(R.id.button3);

            // 4. Set the text for textView
            //imgView.setImageResource(modelsArrayList.get(position).getIcon());
            button1.setId(modelsArrayList.get(position).getButtonId1());
            button1.setText(String.valueOf(button1.getId()));
            button2.setId(modelsArrayList.get(position).getButtonId2());
            button2.setText(String.valueOf(button2.getId()));
            button3.setId(modelsArrayList.get(position).getButtonId3());
            button3.setText(String.valueOf(button3.getId()));


        // 5. retrn rowView
        return rowView;
    }
}
