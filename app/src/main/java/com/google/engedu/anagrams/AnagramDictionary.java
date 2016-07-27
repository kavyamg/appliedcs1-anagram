package com.google.engedu.anagrams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

public class AnagramDictionary {

    private static final int MIN_NUM_ANAGRAMS = 5;
    private static final int DEFAULT_WORD_LENGTH = 3;
    private static final int MAX_WORD_LENGTH = 7;
    private Random random = new Random();

    private static int wordLength = DEFAULT_WORD_LENGTH;

     HashSet<String> wordSet = new HashSet<>();
     ArrayList<String> wordList = new ArrayList<>();
     HashMap<String,ArrayList<String>> letterToWord = new HashMap<>();
     HashMap<Integer,ArrayList<String>> sizeToWords = new HashMap<>();

    public AnagramDictionary(InputStream wordListStream) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(wordListStream));
        String line;
        ArrayList<String> wordMapList;
        while((line = in.readLine()) != null) {
            String word = line.trim();
           wordSet.add(word);
            wordList.add(word);

            if(sizeToWords.containsKey(word.length())){
                wordMapList = sizeToWords.get(word.length());
                wordMapList.add(word);
                sizeToWords.put(word.length(),wordMapList);
            } else {
                ArrayList<String> newWordList = new ArrayList<>();
                newWordList.add(word);
                sizeToWords.put(word.length(),newWordList);
            }

            ArrayList<String> sortedList = new ArrayList<>();
            String sortedword = sortLetters(word);

            if(!(letterToWord.containsKey(sortedword))){
                sortedList.add(word);
                letterToWord.put(sortedword,sortedList);
            }
            else{
                sortedList = letterToWord.get(sortedword);
                sortedList.add(word);
                letterToWord.put(sortedword,sortedList);

            }


        }
    }

    public boolean isGoodWord(String word, String base) {
        if(wordSet.contains(word) && !base.contains(word)){
        return true;
        }
        else{
            return false;
        }
    }

    public ArrayList<String> getAnagrams(String targetWord) {
        ArrayList<String> resultList = new ArrayList<String>();
        return resultList;
    }


    public ArrayList<String> getAnagramsWithOneMoreLetter(String word) {
        ArrayList<String> tempList;
        ArrayList<String> resultList = new ArrayList<String>();
        for (char alpa = 'a'; alpa <= 'z'; alpa++) {
            String anagram = word + alpa;
            String sortedAnagaram = sortLetters(anagram);
            if (letterToWord.containsKey(sortedAnagaram)) {
                tempList = new ArrayList<>();
                tempList = letterToWord.get(sortedAnagaram);
                for (int i = 0; i < tempList.size(); i++)
                    if (!(tempList.get(i).contains(word))) {
                        resultList.add(String.valueOf(tempList.get(i)));
                    }
            }
        }
        return resultList;
    }

    public ArrayList<String> getAnagramsWithTwoMoreLetter(String word) {
        ArrayList<String> tempList1;
        ArrayList<String> resultList1 = new ArrayList<String>();
        for (char alpa = 'a'; alpa <= 'z'; alpa++) {
            for (char firstalpa = 'a'; firstalpa <= 'z'; firstalpa++) {
                String anagram = word + firstalpa + alpa;
                String sortedAnagaram = sortLetters(anagram);
                if (letterToWord.containsKey(sortedAnagaram)) {
                    tempList1 = new ArrayList<>();
                    tempList1 = letterToWord.get(sortedAnagaram);
                    for (int i = 0; i < tempList1.size(); i++)
                        if (!(tempList1.get(i).contains(word))) {
                            resultList1.add(String.valueOf(tempList1.get(i)));
                        }
                }
            }
        }

        return resultList1;
    }


    public String pickGoodStarterWord() {
        int randomNum;
        String starterWord;
        do{
            randomNum = random.nextInt(sizeToWords.get(wordLength).size());
            starterWord = sizeToWords.get(wordLength).get(randomNum);

        }while (getAnagramsWithOneMoreLetter(starterWord).size() < MIN_NUM_ANAGRAMS);

        if(wordLength <MAX_WORD_LENGTH){
            wordLength++;
        }


        return starterWord ;
    }

    public String sortLetters(String word){
        char[] characters = word.toCharArray();
        Arrays.sort(characters);
        String sortedWord = new String(characters);
        return sortedWord;
    }
}
