package gen.baz;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class Main {

    public static void main(String[] args) {
    try {

        Generator gen = new Generator();
        gen.prepareQuery();

        PrintWriter zapis = new PrintWriter("GeneratedQuery1.txt");
        zapis.println(gen.getMainQuery());
        zapis.close();

        PrintWriter zapis2 = new PrintWriter("GeneratedQuery2.txt");
        zapis2.println(gen.getMainQuery2());
        zapis2.close();

        PrintWriter zapis3 = new PrintWriter("GeneratedQuery3.txt");
        zapis3.println(gen.getMainQuery3());
        zapis3.close();

        PrintWriter zapis4 = new PrintWriter("GeneratedQuery4.txt");
        zapis4.println(gen.getMainQuery4());
        zapis4.close();

        PrintWriter zapis5 = new PrintWriter("GeneratedQuery5.txt");
        zapis5.println(gen.getMainQuery5());
        zapis5.close();

        PrintWriter zapis6 = new PrintWriter("GeneratedQuery6.txt");
        zapis6.println(gen.getMainQuery6());
        zapis6.close();

    }catch(FileNotFoundException e){
        System.out.println(e.getMessage());
    }
    }
}
