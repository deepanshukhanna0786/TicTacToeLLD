package common.repository;

import java.io.File;
import java.io.IOException;

import static common.constants.Constants.FILE_PATH;

public class FileGenerator {

    public void createFile(){
        File myObj = new File(FILE_PATH);
        try {
            myObj.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
