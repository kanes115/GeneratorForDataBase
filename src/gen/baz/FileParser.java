package gen.baz;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by Kanes on 21.01.2017.
 */
public class FileParser {
    List<String> lines = new LinkedList<>();

    public List<String> getLines(){
        return lines;
    }

    public void setPath(String filepath){
        try(Stream<String> s = Files.lines(Paths.get(filepath))){
            lines = s.collect(Collectors.toList());
        } catch (IOException e) {
            System.out.print("File not found. Specify the path to the file correctly. This path is incorrect: " + filepath);
        }
    }


    public FileParser(String filepath){

        try(Stream<String> s = Files.lines(Paths.get(filepath))){
            lines = s.collect(Collectors.toList());
        } catch (IOException e) {
            System.out.print("File not found. Specify the path to the file correctly. This path is incorrect: " + filepath);
        }
    }


}
