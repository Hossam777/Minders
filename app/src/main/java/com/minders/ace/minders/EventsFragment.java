package com.minders.ace.minders;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;


public class EventsFragment extends Fragment {

    View v;
    TextView textcenter;
    Handler UIthreadhandler = new Handler();


    public EventsFragment() {
        // Required empty public constructor

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        v = inflater.inflate(R.layout.fragment_events, container, false);
        textcenter = v.findViewById(R.id.eventcentertext);
        textcenter.setText("");
        if (AppStatus.getInstance(getActivity()).isOnline()) {
            FirebaseHandler handler = new FirebaseHandler();
            handler.GetEvents(new FirebaseHandler.FirebaseCallback() {
                @Override
                public void doJob(final List<FirebaseHandler.EventSponsor> result) {
                    if(result.isEmpty())
                    {
                        textcenter.setText("No Events now, Stay Tuned");
                    }
                    else {

                        ListView listView = (ListView) v.findViewById(R.id.eventlist);
                        listView.setAdapter(new Event_List_Adapter(getActivity(),result));
                    }

                }
            });
        } else {
            textcenter.setText("No Internet Connection");

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


    void refrsh()
    {
        UIthreadhandler.post(new Runnable() {
            @Override
            public void run() {
                textcenter.setText("");
            }
        });
        FirebaseHandler handler = new FirebaseHandler();
        handler.GetEvents(new FirebaseHandler.FirebaseCallback() {
            @Override
            public void doJob(final List<FirebaseHandler.EventSponsor> result) {
                textcenter.setText("");
                if(result.isEmpty())
                {
                    textcenter.setText("no Event now, stay tuned");
                }
                else {

                    ListView listView = (ListView) v.findViewById(R.id.eventlist);
                    listView.setAdapter(new Event_List_Adapter(getActivity(),result));
                }

            }
        });
    }
}
