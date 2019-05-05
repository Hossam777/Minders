package com.minders.ace.minders;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;


public class FirebaseHandler
{
    public class EventSponsor
    {
        String name = "";
        String description = "";
        String link = "";
        String img = "";
    }


    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public interface FirebaseCallback
    {
        void doJob(List<EventSponsor> result);
    }

    public void GetEvents(final FirebaseCallback callb)
    {
        db.collection("Events").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful())
                {
                    List<EventSponsor> res = new ArrayList<>();
                    for(DocumentSnapshot document : task.getResult())
                    {
                        EventSponsor eors = new EventSponsor();
                        if(document.getData().get("Name") != null)
                            eors.name = document.getData().get("Name").toString();
                        if(document.getData().get("Desc") != null)
                            eors.description = document.getData().get("Desc").toString();
                        if(document.getData().get("Link") != null)
                            eors.link = document.getData().get("Link").toString();
                        if(document.getData().get("Img") != null)
                            eors.img = document.getData().get("Img").toString();

                        res.add(eors);
                    }
                    callb.doJob(res);
                }
            }
        });
    }

    public void GetSponsors(final FirebaseCallback callb)
    {
        db.collection("Sponsors").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful())
                {
                    List<EventSponsor> res = new ArrayList<>();
                    for(DocumentSnapshot document : task.getResult())
                    {
                        EventSponsor eors = new EventSponsor();
                        if(document.getData().get("Name") != null)
                            eors.name = document.getData().get("Name").toString();
                        if(document.getData().get("Desc") != null)
                            eors.description = document.getData().get("Desc").toString();
                        if(document.getData().get("Link") != null)
                            eors.link = document.getData().get("Link").toString();
                        if(document.getData().get("Img") != null)
                            eors.img = document.getData().get("Img").toString();

                        res.add(eors);
                    }

                    callb.doJob(res);
                }
            }
        });
    }

    public void GetData(String dataName, boolean isEvent, final FirebaseCallback callb)
    {
        db.collection((isEvent) ? "Events" : "Sponsors").document(dataName).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful())
                {
                    DocumentSnapshot document = task.getResult();

                    List<EventSponsor> res = new ArrayList<>();

                    EventSponsor eors = new EventSponsor();
                    if(document.getData().get("Name") != null)
                        eors.name = document.getData().get("Name").toString();
                    if(document.getData().get("Description") != null)
                        eors.description = document.getData().get("Description").toString();
                    if(document.getData().get("Link") != null)
                        eors.link = document.getData().get("Link").toString();
                    if(document.getData().get("Img") != null)
                        eors.img = document.getData().get("Img").toString();

                    res.add(eors);

                    callb.doJob(res);
                }
            }
        });
    }
}
