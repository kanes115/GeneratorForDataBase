package gen.baz;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Created by Kanes on 21.01.2017.
 */
public class Workshops implements QueryMaker {
    private String query = "";
    private Random gen = new Random();
    private DateBuilder dateB = new DateBuilder();
    private int range;
    private int conferencesRange;
    private int workshopTopicsRange;


    public Workshops(int conferencesRange){
        this.conferencesRange = conferencesRange;
    }



    public void prepareQuery(){
        prepareWorkshopsTopics();
        prepareWorkshops();

    }



    private void prepareWorkshops(){

        query = query.concat("SET IDENTITY_INSERT Workshops ON; \n");
        for(int i = 1; i < conferencesRange*3*3; i++){
            int conferenceid = gen.nextInt(conferencesRange-1) + 1;
            int workshopTopicid = gen.nextInt(workshopTopicsRange - 1) + 1;
            int price = gen.nextInt(200);
            String starttime = dateB.makeRandomTime();
            int roomno = gen.nextInt(200) + 1;

            query = query.concat("INSERT INTO Workshops (WorkshopID, ConferenceDayID, WorkshopTopicID, MaxParticipants, BasicPrice, StartTime, EndTime, RoomNo)"
                    + " VALUES (" + i + ", " + getConferenceDaySubquery(conferenceid) + ", " + workshopTopicid + ", " + getMaxParticipantsForConfSubquery(conferenceid) + ", " + price
                    + ", " + starttime + ", " + dateB.makeRandomTimeBiggerThan(starttime) + ", " + roomno + ") \n");

            this.range = i;
        }
        query = query.concat("SET IDENTITY_INSERT Workshops OFF; \n");
    }



    private void prepareWorkshopsTopics(){
        FileParser parser = new FileParser("src/WorkshopTopics");

        int i = 1;
        query = query.concat("SET IDENTITY_INSERT WorkshopTopics ON; \n");
        for(String c : parser.getLines()){
            query = query.concat("INSERT INTO WorkshopTopics (WorkshopTopicID, Name, Syllabus) VALUES (" + i + ", '" + c + "', '-'" + ") \n");
            i++;
        }
        query = query.concat("SET IDENTITY_INSERT WorkshopTopics OFF; \n");
        this.workshopTopicsRange = i;

    }

    private String getConferenceDaySubquery(int conferenceID){
        int whichDay = gen.nextInt();
        return "( select top 1 ConferenceDayID from ConferenceDay day" +
                " join Conferences c on c.ConferenceID = day.ConferenceID" +
                " where c.ConferenceID = " + conferenceID + " order by newid())";
    }

    private String getMaxParticipantsForConfSubquery(int conferenceID){
        return "( select maxParticipants from Conferences c" +
                "" +
                " where c.ConferenceID = " + conferenceID + ")";
    }


    public String getQuery(){
        return query;
    }

    public int getRange() {
        return range;
    }
}
