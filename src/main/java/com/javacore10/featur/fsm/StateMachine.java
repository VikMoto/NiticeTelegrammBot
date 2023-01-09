package com.javacore10.featur.fsm;

import java.util.ArrayList;
import java.util.List;

public class StateMachine {
    private State state = State.idle;
    private String massage;
    private int time;
    private List<StateMachineListener> listeners = new ArrayList<>();

    public void addListeners(StateMachineListener listener) {
        this.listeners.add(listener);
    }

    public void handle(String text){
        if (text.equals("Create notification")) {
           onCreateNotificationPressed();
           return;
        }
        onTextReceived(text);
        try {
            int number = Integer.parseInt(text);
            onNumberReceived(number);
        }catch (Exception e) {
//            e.printStackTrace();
        }
    }

    private void onCreateNotificationPressed(){
//        System.out.println("onCreateNotificationPressed");
        if (state == State.idle) {
            switchState(State.waiForMessage);

            for (StateMachineListener listener : listeners) {
                listener.onSwitchedToWaitForMessage();
            }
//            state = State.waiForMessage;
        }

    }
    private void onTextReceived(String text){
//        System.out.println("onTextReceived(), text: " + text);
        if (state == State.waiForMessage) {
            this.massage = text;
            switchState(State.waitForTime);
            for (StateMachineListener listener : listeners) {
                listener.onSwitchedToWaitForTime();
            }
//            state = State.waitForTime;
        }
    }
    private void onNumberReceived(int number){
//        System.out.println("onNumberReceived(), number: " + number);
        if (state == State.waitForTime) {
            this.time = number;
            switchState(State.idle);
            System.out.println("Create notification for specific user");
            for (StateMachineListener listener : listeners) {
                listener.onMassageAndTimeReceived(massage, time);
            }
            
            //TODO Create notification for specific user
//            state = State.idle;
        }
    }

    private void switchState(State newState){
        System.out.println("Switch State: " + state + "->" + newState);
        this.state = newState;
    }
}
