package com.boosal.smartlibrary.net.entity.local.book;

import org.litepal.crud.LitePalSupport;

/**
 * Created by boosal on 2019/9/9.
 */

public class Category extends LitePalSupport{

    private long id;
    private String name;

    public Category() {
    }

    public Category(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
