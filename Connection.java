package main;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class Connection {
    private static Connection instance;
    private String file_path;

    private Connection(String file_path) {
        this.file_path = file_path;
    }

    public static Connection getInstance(String file_path) {
        if (instance == null) {
            instance = new Connection(file_path);
        }
        return instance;
    }

    public BufferedReader openFile() throws IOException {
        return new BufferedReader(new InputStreamReader(new FileInputStream(file_path), StandardCharsets.UTF_8));
    }

    public BufferedWriter writeFile() throws IOException {
        return new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file_path, true), StandardCharsets.UTF_8));
    }

    public void closeFile(Reader reader) throws IOException {
        if (reader != null) {
            reader.close();
        }
    }

    public void closeFile(Writer writer) throws IOException {
        if (writer != null) {
            writer.close();
        }
    }
}
