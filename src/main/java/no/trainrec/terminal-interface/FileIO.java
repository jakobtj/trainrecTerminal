package no.trainrec.terminal_interface;

import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;

public class FileIO {
    private File file;
    private BufferedReader reader;
    private BufferedWriter writer;

    public FileIO(File inputFile) {
        file = inputFile;
    }

    public void openReader() throws IOException {
        reader = new BufferedReader(new FileReader(file));
    }

    public String readLine() throws IOException {
        return reader.readLine();
    }

    public void closeReader() throws IOException {
        reader.close();
    }

    public void openWriter() throws IOException {
        writer = new BufferedWriter(new FileWriter(file));
    }

    public void write(String s) throws IOException {
        writer.write(s);
    }

    public void closeWriter() throws IOException {
        writer.close();
    }
}
