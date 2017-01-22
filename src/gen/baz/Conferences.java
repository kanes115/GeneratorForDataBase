package gen.baz;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Created by Kanes on 21.01.2017.
 */
public class Conferences implements QueryMaker {
    private String query = "";
    private Random gen = new Random();
    private int cityIDRange;
    private List<String> streets = new LinkedList<>();
    private int range;
    private int confdayCounter = 1;

    private List<String> TimeDiscs = new LinkedList<>();
    private List<DayConfWithLimit> days = new LinkedList<>();

    public Conferences(int cityIDRange){
        this.cityIDRange = cityIDRange;
        FileParser parser = new FileParser("src/Streets");
        streets = parser.getLines();

        TimeDiscs.add("(7, 0.1, ");
        TimeDiscs.add("(14, 0.3, ");
        TimeDiscs.add("(21, 0.5, ");
    }



    public void prepareQuery(){
        FileParser parser = new FileParser("src/ConferencesNames");
        DateBuilder dateB = new DateBuilder();

        int i = 1;
        query = query.concat("SET IDENTITY_INSERT Conferences ON; \n");
        for(String c : parser.getLines()){
            String startdate = dateB.makeRandomDate(2013);
            String enddate = dateB.addRandomLittleDays(startdate);
            int pricePerDay = gen.nextInt(300);
            int cityID = gen.nextInt(cityIDRange - 1) + 1;
            String street = "'" + streets.get(gen.nextInt(streets.size())) + " " + (gen.nextInt(200)) + "'";
            String studDisc = "0." + (gen.nextInt(9));
            int maxPart = gen.nextInt(500) + 1;

            query = query.concat("INSERT INTO Conferences (ConferenceID, Name, StartDate, EndDate, BasicPricePerDay, CityID, Street, StudentsDiscount, maxParticipants) "
                    + "VALUES (" + i +  ", '" + c + "', " + startdate + ", " + enddate + ", " + pricePerDay + ", " + cityID + ", " + street + ", " + studDisc + ", " + maxPart + ") \n");

            for(int j = 1; j <= dateB.diff(enddate, startdate); j++) {
                query = query.concat("SET IDENTITY_INSERT Conferences OFF; \n");
                query = query.concat("SET IDENTITY_INSERT ConferenceDay ON; \n");
                query = query.concat("INSERT INTO ConferenceDay (ConferenceDayID, ConferenceID, whichDay) VALUES (" + confdayCounter + ", " + i + ", " + j + ") \n");
                query = query.concat("SET IDENTITY_INSERT ConferenceDay OFF; \n");
                query = query.concat("SET IDENTITY_INSERT Conferences ON; \n");
                days.add(new DayConfWithLimit(confdayCounter, i, maxPart, startdate, j));
                confdayCounter++;
            }

            for(String g : TimeDiscs){
                query = query.concat("INSERT INTO TimeDiscounts VALUES " + g + i +  ") \n");
            }
            i ++;
        }
        query = query.concat("SET IDENTITY_INSERT Conferences OFF; \n");
        this.range = i;

    }


    public String getQuery(){
        return query;
    }

    public int getRange() {
        return range;
    }

    public int getConfdayCounter() {
        return confdayCounter;
    }

    public List<DayConfWithLimit> getDays() {
        return days;
    }
}
