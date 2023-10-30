package com.fatecorehelper.model;

import java.util.ArrayList;

public class Character {
    public ArrayList<String> aspects = new ArrayList<>();
    public ArrayList<ArrayList<String>> skillGrid = new ArrayList<>();
    int skillGridWidth;

    public Character(int skillGridWidth){
        this.skillGridWidth = skillGridWidth;
        for (int i = 0; i < skillGridWidth; i++) {
            skillGrid.add(new ArrayList<>());
        }
    }
}
