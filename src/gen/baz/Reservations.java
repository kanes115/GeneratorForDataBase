package gen.baz;

import com.sun.org.apache.xpath.internal.operations.Bool;

import java.util.List;
import java.util.Random;

/**
 * Created by Kanes on 21.01.2017.
 */
public class Reservations implements QueryMaker {
    private String query = "";
    private Random gen = new Random();
    private DateBuilder dateB = new DateBuilder();
    private int range;

    private int reservationCounter = 1;
    private int confReservationCounter = 1;
    private int workshopResCounter = 1;

    private int conferencesRange;

    private final int privateClStart;
    private final int privateClEnd;
    private final int companyClStart;
    private final int companyClEnd;
    private List<DayConfWithLimit> days;


    public Reservations(int conferencesRange, int privateClStart, int privateClEnd, int companyClStart, int companyClEnd, List<DayConfWithLimit> days){
        this.conferencesRange = conferencesRange;
        this.privateClStart = privateClStart;
        this.privateClEnd = privateClEnd;
        this.companyClStart = companyClStart;
        this.companyClEnd = companyClEnd;
        this.days = days;
    }

    public void prepareQuery(){

        for(int i = privateClStart; i <= companyClEnd; i ++){

            int amRes = gen.nextInt(4);
            for(int j = 1; j <= amRes; j++){

                int confID = gen.nextInt(conferencesRange) + 1; // do jakiejś konferencji

                DayConfWithLimit daylast = new DayConfWithLimit(0, 0, 0, "", -1);           //ostatni dzień aktualnej konferencji
                DayConfWithLimit dayfirst = new DayConfWithLimit(0, 0, 0, "", 100000);      //pierwszy dzień aktualnej konferencji

                for(DayConfWithLimit d : days){
                    if(d.confID == confID) {
                        if(daylast.whichDay < d.whichDay) {
                            daylast = d;
                        }
                        if(dayfirst.whichDay > d.whichDay){
                            dayfirst = d;
                        }
                    }
                }

                int confDayID = daylast.dayID;
                if(gen.nextBoolean()) {     //losujemy, czy będzie to pierwszy, czy ostatni dzień
                    confDayID = dayfirst.dayID;
                }
                String Confstart = dayfirst.startdate;      //data rozpoczęcia konferencji
                String dateRes = dateB.diffRandomDays(Confstart);
                if(dateRes.isEmpty()) dateRes = "'7/7/2000'";

                query = query.concat("SET IDENTITY_INSERT Reservations ON \n");

                query = query.concat("INSERT INTO Reservations (ReservationID, ClientID, DateOfReservation, DateOfPayment) " +
                        "VALUES (" + reservationCounter + ", " + i + ", " + dateRes + ", " + dateRes + ") \n");

                query = query.concat("SET IDENTITY_INSERT Reservations OFF \n");
                reservationCounter ++;

                //tworzymy conference reservation dla losowo - pierwszego lub ostatniego dnia
                query = query.concat("SET IDENTITY_INSERT ConferenceReservations ON \n");

                query = query.concat("INSERT INTO ConferenceReservations (ConferenceReservationID, ReservationID, ConferenceDayID, ParticipantID) " +
                        "VALUES (" + (confReservationCounter) + ", " + (reservationCounter - 1) + ", " + confDayID + ", " + getRandomParticipantForClientSubquery(i) + ") \n");

                query = query.concat("SET IDENTITY_INSERT ConferenceReservations OFF \n");

                confReservationCounter ++;


//                query = query.concat("SET IDENTITY_INSERT WorkshopReservations ON \n");
//
//                query = query.concat("INSERT INTO WorkshopReservations (WorkshopReservationID, WorkshopID, ConferenceReservationID) " +
//                        "VALUES (" + workshopResCounter + ", " + getRandomWorkshopForConferenceDay(confDayID) + ", " + (confReservationCounter - 1) + ") \n");
//
//                query = query.concat("SET IDENTITY_INSERT WorkshopReservations OFF \n");

            }
        }
    }


    private String getRandomWorkshopForConferenceDay(int ConferenceDayID){
        return "(select WorkshopID from Workshops w" +
                " join ConferenceDay day on w.ConferenceDayID = day.ConferenceDayID " +
                " where day.ConferenceDayID = " + ConferenceDayID + ")";
    }


    private String getRandomParticipantForClientSubquery(int ClientID){
        return "( select top 1 ParticipantID from participants " +
                " where ClientID = " + ClientID + " order by newid())";
    }


    public String getQuery(){
        return query;
    }

}
