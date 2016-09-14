package com.master.vocab.vocabmaster.SharedPref;

import android.os.Bundle;

import com.master.vocab.vocabmaster.Activity.WordActivity;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by sahildeswal on 15/09/16.
 */

public class EventController {

    private static EventController eventController = null;
    private static CopyOnWriteArrayList<EventListeners> listeners;

    private EventController(){
        init();
    }

    public static EventController getEventController(){
        if(eventController == null){
            eventController = new EventController();

        }
        return eventController;

    }



    public interface EventHolder{


        int Word_Change = 101;
    }

    public void register(EventListeners listener){
        if(listeners != null && !listeners.contains(listener)){
            listeners.add(listener);
        }
    }

    public void unregister(EventListeners listener){
        if(listener != null && listeners.contains(listener)){
            listeners.remove(listener);
        }
    }

    public boolean notify(int event, Bundle bundle){
        boolean eventHandled = false;
        for(EventListeners listener : listeners){
            eventHandled |= listener.handleEvent(event, bundle);

        }
        return  eventHandled;
    }

    private void init() {
        listeners = new CopyOnWriteArrayList<EventListeners>();
    }


    public void reset(){
        listeners = new CopyOnWriteArrayList<EventListeners>();
    }


}
