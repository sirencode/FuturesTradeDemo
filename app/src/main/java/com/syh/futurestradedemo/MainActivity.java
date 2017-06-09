package com.syh.futurestradedemo;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import com.syh.futurestradedemo.bean.BookOuterClass;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends Activity {

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BookOuterClass.Book book = BookOuterClass.Book.newBuilder()
                .setId(1)
                .setName("Prime")
                .setDesc("Code Book")
                .build();
        findViewById(R.id.write).setOnClickListener(v -> {
            save(book);
        });

        findViewById(R.id.read).setOnClickListener(v -> {
            read();
        });

        textView = (TextView) findViewById(R.id.book);
    }

    private void save(BookOuterClass.Book book) {
        File dir = getFilesDir();
        File file = new File(dir, "book");
        try {
            FileOutputStream outputStream = new FileOutputStream(file);
            book.writeTo(outputStream);
            outputStream.close();
        } catch (IOException e) {
            Log.e("IOException", e.getMessage());
        }
    }

    private void read() {
        File dir = getFilesDir();
        File file = new File(dir, "book");
        try {
            FileInputStream inputStream = new FileInputStream(file);
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            byte[] data = new byte[1024];
            int len = -1;
            while ((len = inputStream.read(data)) != -1) {
                out.write(data, 0, len);
                out.flush();
            }
            BookOuterClass.Book book = BookOuterClass.Book.parseFrom(out.toByteArray());
            out.close();
            textView.setText("name:" + book.getName() + ",desc:" + book.getDesc());
        } catch (IOException e) {
            Log.e("", e.getMessage());
        }
    }
}

