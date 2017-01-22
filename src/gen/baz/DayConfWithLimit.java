package gen.baz;

/**
 * Created by Kanes on 21.01.2017.
 */
public class DayConfWithLimit {
    public int dayID;
    public int confID;
    public int max;
    public String startdate;
    public int whichDay;

    public DayConfWithLimit(int dayID, int confID, int max, String startdate, int whichDay){

        this.dayID = dayID;
        this.confID = confID;
        this.max = max;
        this.startdate = startdate;
        this.whichDay = whichDay;
    }

    public String toString(){
        return " dayID: " + dayID +  " confID: " + confID + " max" + max + " startdate: " + startdate + " whichday: " + whichDay;
    }
}
