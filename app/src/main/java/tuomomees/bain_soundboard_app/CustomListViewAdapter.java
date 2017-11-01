package tuomomees.bain_soundboard_app;

/**
 * Luokan on luonut tuomo päivämäärällä 23.10.2017.
 */

//LISTVIEWIÄ VARTEN LUOTU ADAPTERI

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

class CustomListViewAdapter extends ArrayAdapter<String> {
    private int groupid;
    //private String[] item_list;
    private Context context;
    private int buttonCounter = 0;

    int counter = 0;

    //VG = layout ID
    CustomListViewAdapter(Context context, int vg, /*int id, */String[] item_list){
        super(context,vg, /*id,*/ item_list);
        this.context=context;
        groupid=vg;
        //this.item_list=item_list;
    }
    // Hold views of the ListView to improve its scrolling performance
    private static class ViewHolder {
        TextView textview;
        Button button1, button2, button3;
    }

    @NonNull //BUG FIX
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {

        View rowView = convertView;
        // Inflate the list_item.xml file if convertView is null
        if(rowView==null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            assert inflater != null;
            rowView= inflater.inflate(groupid, parent, false);
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.button1 = (Button) rowView.findViewById(R.id.button1);
            viewHolder.button2 = (Button) rowView.findViewById(R.id.button2);
            viewHolder.button3 = (Button) rowView.findViewById(R.id.button3);
            rowView.setTag(viewHolder);
        }

            // Set text to each TextView of ListView item
        ViewHolder holder = (ViewHolder) rowView.getTag();
            buttonCounter = buttonCounter + 1;
            holder.button1.setText(String.valueOf(buttonCounter));
            holder.button1.setId(position);
            buttonCounter = buttonCounter + 1;
            holder.button2.setText(String.valueOf(position));
            holder.button2.setId(position);
            buttonCounter = buttonCounter + 1;
            holder.button3.setText(String.valueOf(position));
            holder.button3.setId(position);
            buttonCounter = buttonCounter + 1;

        return rowView;
    }
}