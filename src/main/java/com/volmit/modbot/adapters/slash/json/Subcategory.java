package com.volmit.modbot.adapters.slash.json;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class Subcategory {
    @Getter
    @Setter
    private String id;
    @Getter
    @Setter
    private String name;
    @Getter
    @Setter
    private String description;
    @Getter
    @Setter
    private List<Entry> entries = new ArrayList<>();
    @Getter
    @Setter
    private List<Subcategory> subcategories = new ArrayList<>();

}