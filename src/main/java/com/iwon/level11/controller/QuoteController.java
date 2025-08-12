package com.iwon.level11.controller;

import com.iwon.level11.service.QuoteService;
import com.iwon.quote.domain.Quote;

import java.util.List;
import java.util.Scanner;

public class QuoteController {

    private Scanner sc;
    private QuoteService quoteService;

    public QuoteController(Scanner sc) {
        this.sc = sc;
        quoteService = new QuoteService();
    }

    public void createQuote() {
        System.out.print("명언 : ");
        String content = sc.nextLine();
        System.out.print("작가 : ");
        String author = sc.nextLine();

        Quote quote = quoteService.setQuote(content, author);

        System.out.println(quote.getId() + "번 명언이 등록되었습니다.");
    }

    public void showList() {
        System.out.println("번호 / 작가 / 명언\n" + "----------------------");

        List<Quote> quoteList = quoteService.getQuoteList();

        quoteList.reversed().stream()
                .forEach(q -> {
                    System.out.println(q.getId() + " / " + q.getAuthor() + " / " + q.getContent());
                });
    }

    public void changeQuote(String[] command) {
        if (command.length < 2) {
            System.out.println("번호를 입력해주세요.");
            return;
        }

        // 명언 확인
        if (!quoteService.checkQuote(command[1])) {
            System.out.println(command[1] + "번 명언은 존재하지 않습니다.");
            return;
        }

        Quote quote = quoteService.getQuote(Integer.parseInt(command[1]));

        System.out.println("명언(기존) : " + quote.getContent());
        System.out.print("명언 : ");
        String content = sc.nextLine();

        System.out.println("작가(기존) : " + quote.getAuthor());
        System.out.print("작가 : ");
        String author = sc.nextLine();

        quoteService.setQuote(quote, content, author);

        System.out.println(quote.getId() + "번 명언이 등록되었습니다.");

    }

    public void deleteQuote(String[] command) {
        if (command.length < 2) {
            System.out.println("번호를 입력해주세요.");
            return;
        }

        if (!quoteService.checkQuote(command[1])) {
            System.out.println(command[1] + "번 명언은 존재하지 않습니다.");
            return;
        }

        boolean deleted = quoteService.deleteQuote(command[1]);

        if (deleted) {
            System.out.println(command[1] + "번 명언이 삭제되었습니다.");
        } else {
            System.out.println(command[1] + "번 명언 삭제 중 오류");
        }
    }

    public void buildData() {
        quoteService.setbuilData();
    }
}

