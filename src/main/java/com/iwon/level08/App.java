package com.iwon.level08;

import com.iwon.quote.domain.CommandList;
import com.iwon.quote.domain.Quote;

import java.util.*;

public class App {

    // 명언 번호
    private static int id_count = 0;

    public void run() {

        Scanner sc = new Scanner(System.in);
        String command = "";

        List<Quote> dataList = new ArrayList<>();

        try {
            System.out.println("== 명언 앱 v.8 ==");

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

    private static void changeQuote(List<Quote> list, int id, Scanner sc) throws Exception{
        list.stream()
                .filter(q -> q.getId() == id)
                .findFirst()
                .ifPresentOrElse(found -> {
                    System.out.print("명언(기존) : " + found.getContent() + "\n 명언 : ");
                    found.setContent(sc.nextLine());
                    System.out.println("명언 : " + found.getContent());

                    System.out.print("작가(기존) : " + found.getAuthor() + "\n 작가 :");
                    found.setAuthor(sc.nextLine());
                    System.out.println("작가 : " + found.getAuthor());
                }, () -> {
                    System.out.println(id + "번 명언은 존재하지 않습니다.");
                });
        /*
        Quote found = findById(list, id);

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
         */
    }

    private static void deleteQuote(List<Quote> list, int id) throws Exception{
        list.stream()
                .filter(q -> q.getId() == id)
                .findFirst()
                .ifPresentOrElse(q -> {
                    list.remove(q);
                    System.out.println(id + "번 명언이 삭제되었습니다.");
                }, () -> System.out.println(id + "번 명언은 존재하지 않습니다."));
    }

    private static Quote findById(List<Quote> list, int id) {
        return list.stream()
                .filter(q -> q.getId() == id)
                .findFirst()
                .orElse(null);
    }

    private static void showList(List<Quote> list) throws  Exception{
        System.out.println("번호 / 작가 / 명언\n" + "----------------------");

        list.stream()
                .sorted(Comparator.comparing(Quote::getId).reversed())
                .forEach(x -> System.out.println(x.getId() + " / " + x.getAuthor() + " / " + x.getContent()));
    }

    private static void addQuote(List<Quote> list, Scanner sc) throws Exception {
        System.out.print("명언 : ");
        String content = sc.nextLine();

        System.out.print("작가 : ");
        String author = sc.nextLine();

        list.add(new Quote(++id_count, author, content));

        System.out.println(id_count + "번 명언이 등록되었습니다.");
    }
}