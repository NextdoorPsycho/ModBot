package com.volmit.modbot.adapters.slash.json;

import lombok.Getter;
import lombok.Setter;
import okhttp3.internal.http2.Settings;

import java.util.ArrayList;
import java.util.List;
public class Root {
    @Getter
    @Setter
    private String name;
    @Getter
    @Setter
    private String description;
    @Getter
    @Setter
    private List<String> authors = new ArrayList<>();
    @Getter
    @Setter
    private String status;
    @Getter
    @Setter
    private Settings settings;
    @Getter
    @Setter
    private List<Category> categories = new ArrayList<>();
}