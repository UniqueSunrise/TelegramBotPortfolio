package com.networkPortfolio.NetworkPortoflioBot.service;


import com.networkPortfolio.NetworkPortoflioBot.config.BotConfig;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;


@Component
public class TelegramBot extends TelegramLongPollingBot {

    final BotConfig config;

    public TelegramBot(BotConfig config) {
        this.config = config;
        List<BotCommand> listofCommands = new ArrayList<>();
        listofCommands.add(new BotCommand("/start", "welcome message"));
        listofCommands.add(new BotCommand("/add_portfolio", "add your portfolio on dropstab"));
        listofCommands.add(new BotCommand("/delete_portfolio", "delete your portfolio"));
        listofCommands.add(new BotCommand("/information", "information how to use this telegram bot"));
        try {
            this.execute(new SetMyCommands(listofCommands, new BotCommandScopeDefault(), null));
        }
        catch (TelegramApiException e) {

        }
    }

    @Override
    public String getBotUsername() {
        return config.getBotName();
    }

    @Override
    public String getBotToken() {
        return config.getToken();
    }

    @Override
    public void onUpdateReceived(Update update) {

        if(update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();


            switch (messageText) {
                case "/start":
                        startCommandReceived(chatId, update.getMessage().getChat().getFirstName());
                        break;

                case "/add_portfolio":
                        sendMessage(chatId, "In progress");
                        break;
                case "/delete_portfolio":
                        sendMessage(chatId, "In progress");
                        break;
                case "/information":
                        sendMessage(chatId, "In progress");
                        break;



                default:
                        sendMessage(chatId, "Sorry, command was not recognized");

            }
        }



    }

    private void startCommandReceived(long chatId, String name){


        String answer = "Hi, " + name +", nice to meet you!";


        sendMessage(chatId, answer);
    }

    private void sendMessage(long chatId, String textToSend)  {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(textToSend);

        try{
            execute(message);
        }
        catch (TelegramApiException e) {

        }
    }
}
