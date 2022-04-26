package com.nobody.runner;

import com.nobody.https.ResponseHendler;
import com.nobody.parser.PageParser;
import com.nobody.sendler.MessageToTelegramSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TaskRunner implements Runnable {

    private MessageToTelegramSender sender;
    private ResponseHendler responseHendler;
    private PageParser pageParser;

    @Autowired
    public TaskRunner(MessageToTelegramSender sender, ResponseHendler responseHendler, PageParser pageParser) {
        this.sender = sender;
        this.responseHendler = responseHendler;
        this.pageParser = pageParser;
    }

    @Override
    public void run() {
        String page = responseHendler.sendRequest();
        sender.sendMessage(pageParser.getData(page));
    }
}
