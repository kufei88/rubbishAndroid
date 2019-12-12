package com.boosal.smartlibrary.net.entity.local.book;

import android.os.Environment;

import org.litepal.annotation.Column;
import org.litepal.crud.LitePalSupport;

import java.io.File;
import java.io.FileOutputStream;

import jxl.Cell;

/**
 * Created by boosal on 2019/9/6.
 */

public class Book extends LitePalSupport{

    private long id;
    private String imgpath;
    private String name;
    private String isbn;
    private String press;
    private String publish_time;
    private String category_name;

    @Column(ignore = true)
    private int bindTimes;
    @Column(ignore = true)
    private int errorTimes;
    @Column(ignore = true)
    private boolean isChecked;

    public Book() {
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        Book book = (Book) o;

        return isbn.equals(book.isbn);
    }

    @Override
    public int hashCode()
    {
        int result = isbn.hashCode();
        return result;
    }


    public Book(Cell[] cells) {
        //图片，书名，书号，出版社，出版时间，类别
        this.name = cells[1].getContents();
        this.isbn = cells[2].getContents();
        this.press = cells[3].getContents();
        this.publish_time = cells[4].getContents();
        this.category_name = cells[5].getContents();
    }

    public long getId() {
        return id;
    }

    public String getImgpath() {
        return imgpath;
    }

    public void setImgpath(String imgpath) {
        this.imgpath = imgpath;
    }

    public void setImgpath(byte[] picData) {
        if(picData != null) {
            try {
                String folder = Environment.getExternalStorageDirectory().getPath() +"/"+"libraryimg";
                //创建文件夹
                File file = new File(folder);
                if(!file.exists()){//如果文件夹不存在，则创建
                    file.mkdirs();
                }
                String imgPath = folder + "/" + name+isbn+".png";
                FileOutputStream out = new FileOutputStream(imgPath);
                out.write(picData);
                out.close();
                this.imgpath = imgPath;
            }catch(Exception e) {
                e.printStackTrace();
            }
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getPress() {
        return press;
    }

    public void setPress(String press) {
        this.press = press;
    }

    public String getPublish_time() {
        return publish_time;
    }

    public void setPublish_time(String publish_time) {
        this.publish_time = publish_time;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public int getBindTimes() {
        return bindTimes;
    }

    public void setBindTimes(int bindTimes) {
        this.bindTimes = bindTimes;
    }

    public int getErrorTimes() {
        return errorTimes;
    }

    public void setErrorTimes(int errorTimes) {
        this.errorTimes = errorTimes;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id='" + id + '\'' +
                ", imgpath='" + imgpath + '\'' +
                ", name='" + name + '\'' +
                ", isbn='" + isbn + '\'' +
                ", press='" + press + '\'' +
                ", publish_time='" + publish_time + '\'' +
                ", category_name='" + category_name + '\'' +
                ", bindTimes=" + bindTimes +
                ", errorTimes=" + errorTimes +
                '}';
    }
}
