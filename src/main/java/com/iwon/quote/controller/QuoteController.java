package com.iwon.quote.controller;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class QuoteController {

    private final String FILE_PATH = "db/wiseSaying";
    private final String FILE_LIST_ID = "lastId.txt";

    private Scanner sc;

    public QuoteController(Scanner sc) {
        this.sc = sc;
    }

    public void createQuote(int id) {
        System.out.print("명언 : ");
        String content = sc.nextLine();
        System.out.print("작가 : ");
        String author = sc.nextLine();

        saveQuote(++id, content, author);

        System.out.println(id + "번 명언이 등록되었습니다.");
        saveLastId(id);
    }











    //

    private void buildData() {
        int lastid = Integer.parseInt(getLastId());
        List<String> data = new ArrayList<>();

        for (int i = 1; i <= lastid; i++) {
            try (
                    BufferedReader br = new BufferedReader(new FileReader(FILE_PATH + "/" + i + ".json"))
            )
            {
                String line = "";
                while ((line = br.readLine()) != null) {
                    data.add(line);
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }

        try(
                BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH + "/data.json"))
        )
        {
            bw.write(String.join(",", data));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void changeQuote(String command) {
        String[] findId = command.split("=");

        if (findId.length < 2) {
            System.out.println("번호를 입력해주세요.");
            return;
        }

        File fileName = new File(FILE_PATH + "/" + findId[1] + ".json");

        if (!fileName.exists()) {
            System.out.println(findId[1] + "번 명언은 존재하지 않습니다.");
        }
        else {
            String[] data = getQuoteData(Integer.parseInt(findId[1]));
            System.out.println("명언(기존) : " + data[1]);
            System.out.print("명언 : ");
            String content = sc.nextLine();

            System.out.println("작가(기존) : " + data[2]);
            System.out.print("작가 : ");
            String author = sc.nextLine();

            saveQuote(Integer.parseInt(findId[1]), content, author);

            System.out.println(Integer.parseInt(findId[1]) + "번 명언이 등록되었습니다.");
        }
    }

    private void deleteQuote(String command) {
        String[] findId = command.split("=");

        if (findId.length < 2) {
            System.out.println("번호를 입력해주세요.");
            return;
        }

        File file = new File(FILE_PATH + "/" + findId[1] + ".json");

        if (file.exists()) {
            boolean deleted = file.delete();

            if (deleted) {
                System.out.println(findId[1] + "번 명언이 삭제되었습니다.");
            } else {
                System.out.println(findId[1] + "번 명언 삭제 중 오류");
            }
        } else {
            System.out.println(findId[1] + "번 명언은 존재하지 않습니다.");
        }

    }

    public void saveLastId(int id) {
        String fileName = FILE_PATH + "/" + FILE_LIST_ID;

        try (
                BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(String.valueOf(id));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getLastId() {
        String fileName = FILE_PATH + "/" + FILE_LIST_ID;
        String result = "0";

        try (
                BufferedReader reader = new BufferedReader(new FileReader(fileName))
        ) {
            result = reader.readLine();
        }
        catch (FileNotFoundException e) {
            File file = new File(fileName);
            if (!file.exists()) {
                saveLastId(0);
            }
            return "0";
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    public void showList() {
        int lastId = Integer.parseInt(getLastId());

        System.out.println("번호 / 작가 / 명언\n" + "----------------------");

        for (int i = lastId; i > 0; i--) {
            String pr = String.join(" / ", getQuoteData(i));
            System.out.println(pr);
        }
    }

    private String[] getQuoteData(int id) {
        String fileName = FILE_PATH + "/" + id + ".json";
        List<String> data = new ArrayList<>();

        try (
                BufferedReader br = new BufferedReader(new FileReader(fileName))
        )
        {
            String line = null;

            while ((line = br.readLine()) != null) {
                Pattern pattern = Pattern.compile(":\\s*(\"([^\"]*)\"|(\\d+))");
                Matcher matcher = pattern.matcher(line);

                while (matcher.find()) {
                    data.add(matcher.group(1).replace("\"", "").trim());
                }
            }
        }
        catch (FileNotFoundException e) {
            System.out.println(e);
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return data.toArray(new String[0]);
    }



    public void saveQuote(int id, String content, String author) {
        String filenName = FILE_PATH + "/" + id + ".json";

        try (
                BufferedWriter bw = new BufferedWriter(new FileWriter(filenName)))
        {
            String data = "{\"id\":" + id + ",\"quote\":\"" + content + "\",\"writer\":\"" + author + "\"}";
            bw.write(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
