package com.javacore10.featur.fsm;

public interface StateMachineListener {
     void onSwitchedToWaitForMessage();
     void onSwitchedToWaitForTime();
     void onMassageAndTimeReceived(String message, int time);
}
