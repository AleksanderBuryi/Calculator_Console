package fileMethods;

import interfaces.Writer;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class InFileWriter implements Writer {

    private final String path = "src/history.txt";

    @Override
    public void write(String message) {
        File file = new File(path);
        FileWriter fr = null;
        try {
            fr = new FileWriter(file, true);
            fr.write(message + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            try {
                fr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
