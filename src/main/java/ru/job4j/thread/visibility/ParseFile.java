package ru.job4j.thread.visibility;

import java.io.*;
import java.util.function.Predicate;

public final class ParseFile {
    private final File file;

    public ParseFile(File file) {
        this.file = file;
    }

    private String content(Predicate<Character> filter) {
        StringBuilder output = new StringBuilder();
        int data;
        try (BufferedInputStream i = new BufferedInputStream(new FileInputStream(file))) {
            while ((data = i.read()) != -1) {
                 if (filter.test((char) data)) {
                     output.append((char) data);
                 }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return output.toString();
    }

    public String defaultContent() {
        return content(character -> character > 0);
    }

    public String contentWithoutUnicode() {
        return content(character -> character < 0x80);
    }
}
