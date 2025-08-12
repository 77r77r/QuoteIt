package com.iwon.level11.repository;

import com.iwon.quote.domain.Quote;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class QuoteRepository {

    private final String FILE_PATH = "db/wiseSaying/";
    private final String FILE_NAME_LAST_ID = "lastId.txt";

    public boolean checkFileById(String id) {
        File file = new File(FILE_PATH + id + ".json");
        return file.exists();
    }

    public boolean checkFileByName(String fileName) {
        File file = new File(FILE_PATH + fileName);
        return file.exists();
    }

    private void saveLastId(int id) {
        try (
                BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH + FILE_NAME_LAST_ID))
        ) {
            writer.write(String.valueOf(id));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int readFileLastId() {
        String result = "";
        File file = new File(FILE_PATH + FILE_NAME_LAST_ID);

        if (!file.exists()) {
            saveLastId(0);
            return 0;
        }

        try (
                BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH + FILE_NAME_LAST_ID))
        ) {
            result = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Integer.parseInt(result);
    }

    public Quote saveFileQuote(String content, String author) {
        int id = readFileLastId();

        Quote quote = new Quote(++id, content, author);
        saveLastId(id);

        String filenName = FILE_PATH + id + ".json";

        try (
                BufferedWriter bw = new BufferedWriter(new FileWriter(filenName))) {
            String data = "{\"id\":" + id + ",\"quote\":\"" + content + "\",\"writer\":\"" + author + "\"}";
            bw.write(data);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return quote;
    }

    public Quote saveFileQuote(Quote quote) {
        try (
                BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH + quote.getId() + ".json"))) {
            String data = "{\"id\":" + quote.getId() +
                    ",\"quote\":\"" + quote.getContent() +
                    "\",\"writer\":\"" + quote.getAuthor() + "\"}";
            bw.write(data);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return quote;
    }

    public Quote readQuoteData(int id) {
        List<String> data = new ArrayList<>();

        try (
                BufferedReader br = new BufferedReader(new FileReader(FILE_PATH + id + ".json"))
        ) {
            String line = null;

            while ((line = br.readLine()) != null) {
                Pattern pattern = Pattern.compile(":\\s*(\"([^\"]*)\"|(\\d+))");
                Matcher matcher = pattern.matcher(line);

                while (matcher.find()) {
                    data.add(matcher.group(1).replace("\"", "").trim());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new Quote(id, data.get(1), data.get(2));
    }

    public boolean delectFileQuote(String id) {
        File file = new File(FILE_PATH + id + ".json");
        return file.delete();
    }

    public void buildFileData() {
        int lastid = readFileLastId();
        List<String> data = new ArrayList<>();

        for (int i = 1; i <= lastid; i++) {
            if (checkFileById(String.valueOf(i))) {
                try (
                        BufferedReader br = new BufferedReader(new FileReader(FILE_PATH + i + ".json"))
                ) {
                    String line = "";
                    while ((line = br.readLine()) != null) {
                        data.add(line);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        try (
                BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH + "data.json"))
        ) {
            bw.write(String.join(",\n", data));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
