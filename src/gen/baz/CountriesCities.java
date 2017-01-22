package gen.baz;

/**
 * Created by Kanes on 21.01.2017.
 */
public class CountriesCities implements QueryMaker {
    private String query = "";
    private int range;



    public void prepareQuery(){
        FileParser parser = new FileParser("src/CCodes");

        for(String c : parser.getLines()){
            query = query.concat("INSERT INTO Country VALUES (\'" + c + "\')" + "\n");
        }

        parser.setPath("src/Cities");

        int i = 1;
        query = query.concat("SET IDENTITY_INSERT City ON; \n");
        for(String c : parser.getLines()) {
            query = query.concat("INSERT INTO City (CityID, CityName, CountryCode) VALUES (" + i + ",'" + c + "', 'PL')" + "\n");
            i++;
        }
        query = query.concat("SET IDENTITY_INSERT City OFF; \n");

        range = i;

    }


    public String getQuery(){
        return query;
    }


    public int getRange() {
        return range;
    }
}
