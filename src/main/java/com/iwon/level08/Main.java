package com.iwon.level08;

import com.iwon.quote.domain.CommandList;
import com.iwon.quote.domain.Quote;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    // 명언 번호
    public static int id_count = 0;

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        String command = "";

        ArrayList<Quote> dataList = new ArrayList<>();

        try {
            System.out.println("== 명언 앱 ==");

            do {
                System.out.print("명령) ");
                command = sc.nextLine();

                if (command.equals(CommandList.ADD.getLabel())) {
                    // 등록
                    addQuote(dataList, sc);
                }
                else if (command.equals(CommandList.LIST.getLabel())) {
                    // 목록
                    showList(dataList);
                }
                else if (command.startsWith(CommandList.DELETE.getLabel())) {
                    // 삭제?id=
                    int id = Integer.parseInt(command.split("\\?id=")[1]);
                    deleteQuote(dataList, id);
                }
                else if (command.startsWith(CommandList.CHANGE.getLabel())) {
                    // 수정?id=
                    int id = Integer.parseInt(command.split("\\?id=")[1]);
                    changeQuote(dataList, id, sc);
                }
            } while (!(command.equals(CommandList.END.getLabel())));
        }
        catch (Exception e) {
            System.out.println("[오류]" + e);
        }
        finally {
            sc.close();
        }
    }

    private static void changeQuote(ArrayList<Quote> list, int id, Scanner sc) throws Exception{
        Quote found = list.stream()
                .filter(q -> q.getID() == id)
                .findFirst()
                .orElse(null);

        if (found == null) {
            System.out.println(id + "번 명언은 존재하지 않습니다.");
        }
        else {
            System.out.println("명언(기존) : " + found.getContent() + "\n 명언 : ");
            found.setContent(sc.nextLine());
            System.out.println("명언 : " + found.getContent());

            System.out.println("작가(기존) : " + found.getAuthor() + "\n 작가 :");
            found.setAuthor(sc.nextLine());
            System.out.println("작가 : " + found.getAuthor());
        }
    }

    private static void deleteQuote(ArrayList<Quote> list, int id) throws Exception{
        if (list.stream().anyMatch(q -> q.getID() == id)) {
            list.removeIf(q -> q.getID() == id);
            System.out.println(id + "번 명언이 삭제되었습니다.");
        } else {
            System.out.println(id + "번 명언은 존재하지 않습니다.");
        }
    }

    private static void showList(ArrayList<Quote> list) throws  Exception{
        System.out.println("번호 / 작가 / 명언\n" + "----------------------");

        for (int i = list.size() - 1; i >= 0; i--) {
            Quote x = list.get(i);
            System.out.println(x.getID() + " / " + x.getAuthor() + " / " + x.getContent());
        }
    }

    private static void addQuote(ArrayList<Quote> list, Scanner sc) throws Exception {
        System.out.print("명언 : ");
        String content = sc.nextLine();

        System.out.print("작가 : ");
        String author = sc.nextLine();

        Quote data = new Quote(++id_count, author, content);
        list.add(data);

        System.out.println(id_count + "번 명언이 등록되었습니다.");
    }
}