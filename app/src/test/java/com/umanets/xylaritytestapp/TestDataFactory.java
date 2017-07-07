package com.umanets.xylaritytestapp;

import com.umanets.xylaritytestapp.data.model.WordItem;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;


/**
 * Factory class that makes instances of data models with random field values.
 * The aim of this class is to help setting up test fixtures.
 */
public class TestDataFactory {

    public static String randomUuid() {
        return UUID.randomUUID().toString();
    }

    public static WordItem makeWord(final String unique, final String country) {
        return new WordItem() {
            @Override
            public String name() {
                return unique;
            }

            @Override
            public String id() {
                return null;
            }

            @Override
            public String url() {
                return null;
            }

            @Override
            public String language() {
                return null;
            }

            @Override
            public String country() {
                return country;
            }
        };
    }

    public static List<WordItem> makeListWords(int number) {
        List<WordItem> words = new ArrayList<>();
        for (int i = 0; i < number; i++) {
            words.add(makeWord(String.valueOf(i),"RU"));
        }
        return words;
    }



}