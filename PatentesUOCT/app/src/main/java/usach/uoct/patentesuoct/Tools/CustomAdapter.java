package usach.uoct.patentesuoct.Tools;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import usach.uoct.patentesuoct.R;

/**
 * Created by pablo on 06-07-16.
 */
public class CustomAdapter extends BaseAdapter {

    private Activity a;
    private String[]  Title;
    private static LayoutInflater inflater=null;
    private boolean[] verde;

    public CustomAdapter(Activity activity, String[] text1, boolean[] verdes) {
        a = activity;
        Title = text1;
        verde=verdes;
        inflater=(LayoutInflater)a.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    public int getCount() {
        // TODO Auto-generated method stub
        return Title.length;
    }

    public Object getItem(int arg0) {
        // TODO Auto-generated method stub
        return Title[arg0];
    }

    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View row=convertView;
        row = inflater.inflate(R.layout.list_row, null);
        TextView title;
        ImageView i1;
        i1 = (ImageView) row.findViewById(R.id.imgIcon);
        title = (TextView) row.findViewById(R.id.txtTitle);
        title.setText(Title[position]);
        title.setTextColor(verde[position]?Color.GREEN:Color.RED);
        i1.setImageResource(R.mipmap.delete);
        return (row);
    }
}