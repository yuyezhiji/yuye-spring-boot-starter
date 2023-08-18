package com.yuye.boot.file.test;

import com.yuye.boot.file.store.MappedFile;
import java.io.IOException;

/**
 * @author: xgf
 * @date: 2023-08-09 9:26
 */
public class A {
    public static void main(String[] args) throws IOException {
        MappedFile mappedFile = new MappedFile("1.txt", 100);
        mappedFile.appendMessage("qwe".getBytes());


    }
}
