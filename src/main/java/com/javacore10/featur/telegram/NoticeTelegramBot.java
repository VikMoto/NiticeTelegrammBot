package com.javacore10.featur.telegram;

import com.javacore10.featur.currency.CurrencyService;
import com.javacore10.featur.currency.PrivatBankCurrencyService;
import com.javacore10.featur.currency.dto.Currency;
import com.javacore10.featur.fsm.StateMachine;
import com.javacore10.featur.fsm.StateMachineListener;
import com.javacore10.featur.telegram.command.StartCommand;
import com.javacore10.featur.ui.PrettyPrintCurrencyServise;
import lombok.SneakyThrows;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class NoticeTelegramBot extends TelegramLongPollingCommandBot {
    private Map<String, StateMachine> stateMachines;
    private ScheduledExecutorService scheduledExecutorService;

    public NoticeTelegramBot() {
       register(new StartCommand());
        stateMachines = new ConcurrentHashMap<>();
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
    }

    @Override
    public String getBotUsername() {
        return BotConstants.BOT_NAME;
    }

    @Override
    public String getBotToken() {
        return BotConstants.BOT_TOKEN;
    }

    @Override
    public void onRegister() {
        super.onRegister();
    }



    @Override
    public void onUpdatesReceived(List<Update> updates) {
        super.onUpdatesReceived(updates);
    }




    @SneakyThrows
    @Override
    public void processNonCommandUpdate(Update update) {

        if (update.hasMessage()){
            String message = update.getMessage().getText();
            String responseText = "You wrote - " + message;
            String chatId = Long.toString(update.getMessage().getChatId());
/** if new user -> create map with new chat */
            if (!stateMachines.containsKey(chatId)) {
                StateMachine fsm = new StateMachine();

                fsm.addListeners(new MessageListener(chatId));

                stateMachines.put(chatId, fsm);
            }
/**if old user -> for this user start handle() with update message  */
            stateMachines.get(chatId).handle(message);



        }

//        System.out.println("Non-command here!");
    }

    @Override
    public void processInvalidCommandUpdate(Update update) {
        super.processInvalidCommandUpdate(update);
    }

    @Override
    public boolean filter(Message message) {
        return super.filter(message);
    }


    private class MessageListener implements StateMachineListener {
        private  String chatId;

        public MessageListener(String chatId) {
            this.chatId = chatId;
        }

        @Override
        public void onSwitchedToWaitForMessage() {
            sendText("Write notice text");
        }

        @Override
        public void onSwitchedToWaitForTime() {
            sendText("Ok, get. how many minutes to remind? ");
        }

        @Override
        public void onMassageAndTimeReceived(String message, int time) {
            sendText("Note posted, minutes before triggering  " + time);
            //TODO really notification
            scheduledExecutorService.schedule(
                    () -> sendText(message),
                            time,
                            TimeUnit.MINUTES);
        }

        private void sendText(String text) {
            SendMessage messageText = new SendMessage();
            messageText.setText(text);
            messageText.setChatId(chatId);
            try {
                execute(messageText);//@SneakyThrows instead try/Catch block
            } catch (TelegramApiException e) {
                //NOP
            }
        }
    }

}
