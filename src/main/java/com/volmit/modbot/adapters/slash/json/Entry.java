package com.volmit.modbot.adapters.slash.json;

import lombok.Getter;
import lombok.Setter;

public class Entry {
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
    private String status;
    @Getter
    @Setter
    private Integer weight;
}