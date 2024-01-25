package com.fatecorehelper.model;

import java.util.ArrayList;
import java.util.Optional;

public class Cache {
    public Optional<CharacterDTO> characterDTO = Optional.empty();
    public Optional<ArrayList<String>> skills = Optional.empty();
    public Optional<ArrayList<Integer>> disabledSkillColumnIndexes = Optional.empty();
    public Optional<ArrayList<Integer>> disabledAspectIndexes = Optional.empty();
    public Optional<Integer> skillPoints = Optional.empty();
    public Optional<Integer> skillCap = Optional.empty();
    final static Cache INSTANCE = new Cache();

    public static Cache gerInstance(){
        return INSTANCE;
    }
}
