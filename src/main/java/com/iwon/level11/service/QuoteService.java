package com.iwon.level11.service;

import com.iwon.level11.repository.QuoteRepository;
import com.iwon.quote.domain.Quote;

import java.util.ArrayList;
import java.util.List;

public class QuoteService {

    private QuoteRepository quoteRepository = new QuoteRepository();

    public Quote setQuote(String content, String author) {
        Quote quote = quoteRepository.saveFileQuote(content, author);
        return quote;
    }

    public Quote setQuote(Quote quote, String content, String author) {
        quote.setContent(content);
        quote.setAuthor(author);
        return quoteRepository.saveFileQuote(quote);
    }

    public boolean checkQuote(String id) {
        return quoteRepository.checkFileById(id);
    }

    public Quote getQuote(int id) {
        return quoteRepository.readQuoteData(id);
    }

    public List<Quote> getQuoteList() {
        int lastid = quoteRepository.readFileLastId();
        List<Quote> quoteList = new ArrayList<>();

        for (int i = 1; i <= lastid; i++) {
            if (quoteRepository.checkFileById(String.valueOf(i))) {
//                quoteList.add(quoteRepository.readQuoteData(i));
                quoteList.add(getQuote(i));
            }
        }

        return quoteList;
    }

    public boolean deleteQuote(String id) {
        return quoteRepository.delectFileQuote(id);
    }

    public void setbuilData() {
        quoteRepository.buildFileData();
    }
}
