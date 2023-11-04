package com.fatecorehelper.model;

import java.util.ArrayList;

import static com.fatecorehelper.FateCoreHelperApp.skillGridWidth;

public class CharacterDTO {
    public ArrayList<String> aspects = new ArrayList<>();
    public ArrayList<ArrayList<String>> skillGrid = new ArrayList<>();

    public CharacterDTO(){
        for (int i = 0; i < skillGridWidth; i++) {
            skillGrid.add(new ArrayList<>());
        }
    }
}
