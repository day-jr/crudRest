package br.com.escola.client.myTools;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.util.Scanner;

public class HtmlFileReader {

    public String read(File html) throws FileNotFoundException {
        var content = new String();

        ////////// path stuff ;D
        html= new File("E:/java/escola-crud/src/main/java/br/com/escola/client/web/html/" +html );


        var scan = new Scanner(html);
        while (scan.hasNextLine()){
            content+=scan.nextLine();
        }
        return content;
    }
}
