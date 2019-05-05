package com.minders.ace.minders;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

import static android.support.v4.content.ContextCompat.startActivity;

public class Sponsor_List_Adapter extends BaseAdapter {

    List<FirebaseHandler.EventSponsor> items;
    LayoutInflater l;
    Activity x;


    void openurl(String url)
    {
        try {
            Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            x.startActivity(myIntent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(x, "No application can handle this request."
                    + " Please install a webbrowser",  Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    private void popupmessage(Context c,String s) {
        final TextView taskEditText = new TextView(c);
        AlertDialog dialog = new AlertDialog.Builder(c)
                .setTitle("Description")
                .setMessage(s)
                .setView(taskEditText)
                .setNegativeButton("Close", null)
                .create();
        dialog.show();
    }

    Sponsor_List_Adapter(Activity x,List<FirebaseHandler.EventSponsor> items)
    {
        this.x=x;
        l = (LayoutInflater)x.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.items=items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int i) {
        return items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {

        View v = l.inflate(R.layout.sponsorcard,null);
        ImageView image = v.findViewById(R.id.sponsor_item_logo);
        TextView name = v.findViewById(R.id.sponsor_item_name);
        Picasso.get().load(items.get(i).img).into(image);
        name.setText(items.get(i).name);
        Button sponsor_descriition=v.findViewById(R.id.sponsor_item_desc);
        Button sponsor_link=v.findViewById(R.id.sponsor_item_link);
        sponsor_descriition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(items.get(i).description.equals(""))
                    popupmessage(x,"No Description Yet!");
                else
                    popupmessage(x,items.get(i).description);
            }
        });
        sponsor_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openurl(items.get(i).link);
            }
        });

        return v;
    }
}
