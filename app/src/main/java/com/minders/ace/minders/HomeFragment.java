package com.minders.ace.minders;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;



public class HomeFragment extends Fragment implements View.OnClickListener {


    int i=0;
    Drawable []imgarr;
    String []arr1;
    String []arr2;
    TextView events;

    public HomeFragment() {
        // Required empty public constructor

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    View v;
    ImageView imagechange;
    TextView textchange1;
    TextView textchange2;
    ImageButton back;
    ImageButton forward;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_home, container, false);
        ///added items
        imagechange=v.findViewById(R.id.imagechange);
        textchange1=v.findViewById(R.id.text1change);
        textchange2=v.findViewById(R.id.text2change);
        back=v.findViewById(R.id.back);
        forward=v.findViewById(R.id.forward);
        back.setOnClickListener(this);
        forward.setOnClickListener(this);
        events=v.findViewById(R.id.events);

        imgarr=new Drawable[]{getResources().getDrawable(R.drawable.image1),getResources().getDrawable(R.drawable.image2),getResources().getDrawable(R.drawable.image3),getResources().getDrawable(R.drawable.image4),getResources().getDrawable(R.drawable.image5),getResources().getDrawable(R.drawable.image6)};
        arr1=new String[]{getResources().getString(R.string.text1_1),getResources().getString(R.string.text1_2),getResources().getString(R.string.text1_3),getResources().getString(R.string.text1_4),getResources().getString(R.string.text1_5),getResources().getString(R.string.text1_6)};
        arr2=new String[]{getResources().getString(R.string.text2_1),getResources().getString(R.string.text2_2),getResources().getString(R.string.text2_3),getResources().getString(R.string.text2_4),getResources().getString(R.string.text2_5),getResources().getString(R.string.text2_6)};

        if (AppStatus.getInstance(getActivity()).isOnline()) {
            FirebaseHandler handler = new FirebaseHandler();
            handler.GetEvents(new FirebaseHandler.FirebaseCallback() {
                @Override
                public void doJob(List<FirebaseHandler.EventSponsor> result) {
                    events.setText(result.size() + " Events");
                }
            });
        }
        else
        {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true)
                    {
                        try {
                            Thread.sleep(1000);
                            if (AppStatus.getInstance(getActivity()).isOnline())
                            {
                                refrsh();
                                break;
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();
        }

        return v;
    }

    @Override
    public void onClick(View view) {

        if(view == back)
        {
            if(--i==-1)
                i+=6;
            imagechange.setImageDrawable(imgarr[i]);
            textchange1.setText(arr1[i]);
            textchange2.setText(arr2[i]);
        }

        else if(view == forward)
        {
            if(++i==6)
                i-=6;
            imagechange.setImageDrawable(imgarr[i]);
            textchange1.setText(arr1[i]);
            textchange2.setText(arr2[i]);
        }

    }

    void refrsh()
    {
        FirebaseHandler handler = new FirebaseHandler();
        handler.GetEvents(new FirebaseHandler.FirebaseCallback() {
            @Override
            public void doJob(List<FirebaseHandler.EventSponsor> result) {
                events.setText(result.size() + " Events");
            }
        });
    }

}
