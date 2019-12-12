package com.boosal.smartlibrary.net.entity.local.book;

import org.litepal.crud.LitePalSupport;

/**
 * Created by boosal on 2019/9/9.
 */

public class Label extends LitePalSupport{

    private long id;
    private String rfid;
    private String book_isbn;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getRfid() {
        return rfid;
    }

    public void setRfid(String rfid) {
        this.rfid = rfid;
    }

    public String getBook_isbn() {
        return book_isbn;
    }

    public void setBook_isbn(String book_isbn) {
        this.book_isbn = book_isbn;
    }
}
