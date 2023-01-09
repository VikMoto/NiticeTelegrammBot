package com.javacore10.featur.telegram;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class TelegramBotService {
    private NoticeTelegramBot noticeTelegramBot;
    public TelegramBotService() {
        noticeTelegramBot = new NoticeTelegramBot();
        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(noticeTelegramBot);

        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

    }
}
