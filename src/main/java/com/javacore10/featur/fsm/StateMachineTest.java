package com.javacore10.featur.fsm;

import java.util.Scanner;

public class StateMachineTest {
    public static void main(String[] args) {
        StateMachine stateMachine = new StateMachine();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            String nextLine = scanner.nextLine();

            stateMachine.handle(nextLine);
        }

    }
}
