package org.example.field.game;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Words {
    private ArrayList<String> words;
    private String[] letters;


    public Words() throws FileNotFoundException {
        words = new ArrayList<>();
        Scanner sc = new Scanner(new File("/Users/anastasiafureeva/ОРИС/Fieldofmiracles/Game/src/main/java/org/example/field/game/Words.txt"));
        while (sc.hasNextLine()) {
            words.add(sc.nextLine());
        }
    }
    public String getWord(){
        return words.get(new Random().nextInt(words.size()));
    }

    public static void main(String[] args) throws FileNotFoundException {
        Words words = new Words();
        System.out.println(words.getWord());
    }
}
