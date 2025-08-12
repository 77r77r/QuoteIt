package com.iwon.level11;

import com.iwon.level11.controller.QuoteController;
import com.iwon.level11.controller.SystemController;
import com.iwon.quote.domain.CommandList;

import java.io.*;
import java.util.Scanner;

public class App {

    public void run() {
        System.out.println("== 명언 앱 v.11 ==");
        Scanner sc = new Scanner(System.in);
        QuoteController quoteController = new QuoteController(sc);
        SystemController systemController = new SystemController();

        while (true) {
            System.out.print("명령) ");
            String command = sc.nextLine();

            if (command.equals(CommandList.END.getLabel())) {
                systemController.exit();
                return;
            }
            else if (command.equals(CommandList.ADD.getLabel())) {
                quoteController.createQuote();
            }
            else if (command.equals(CommandList.LIST.getLabel())) {
                quoteController.showList();
            }
            else if (command.startsWith(CommandList.DELETE.getLabel())) {
                quoteController.deleteQuote(command.split("="));
            }
            else if (command.startsWith(CommandList.CHANGE.getLabel())) {
                quoteController.changeQuote(command.split("="));
            }
            else if (command.equals(CommandList.BUILD.getLabel())) {
                quoteController.buildData();
            }
        }
    }


}
