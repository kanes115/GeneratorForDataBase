package gen.baz;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Created by Kanes on 21.01.2017.
 */
public class DateBuilder {
    private Random generator = new Random();
    List<Integer> _31 = new LinkedList<>();
    List<Integer> _30 = new LinkedList<>();


    public DateBuilder(){
        _31.add(1);
        _31.add(3);
        _31.add(5);
        _31.add(7);
        _31.add(8);
        _31.add(10);
        _31.add(12);
        _30.add(4);
        _30.add(6);
        _30.add(9);
        _30.add(11);
    }


    public String addRandomLittleDays(String start){
        int toAdd = generator.nextInt(4);
        if(toAdd == 0)
            return start;
        int day = Integer.parseInt(start.split("/")[1]);
        if(day + toAdd >= 30 || (start.split("/")[0].equals("2") && day + toAdd >= 28)) {
            return start;
        }
        if((start.split("/")[0] + "/" +  (day + toAdd) + "/" + start.split("/")[2]).equals("'2/29/2014'"))
            return start;
        return start.split("/")[0] + "/" +  (day + toAdd) + "/" + start.split("/")[2];
    }

    public String diffRandomDays(String start){
        int toRem = generator.nextInt(4) + 1;

        start = start.replace("'", "");
        if(start.split("/").length < 2){
            System.out.println(start);
            return "";
        }
        int day = Integer.parseInt(start.split("/")[1]);
        int month = Integer.parseInt(start.split("/")[0]);
        int year = Integer.parseInt(start.split("/")[2]);

        if(day - toRem < 1){
            if(month - toRem < 1){
                year--;
                return "'" + month + "/" +  day + "/" + year + "'";
            }
            month--;
            return "'" + month + "/" +  day + "/" + year + "'";
        }
        day -= toRem;
        return "'" + month + "/" +  day + "/" + year + "'";
    }



    public String makeRandomDate(int year){     // tworzy randomową datę w roku year lub w dwóch następnych
        int month = generator.nextInt(12) + 1;
        int day = 0;
        if(month == 2)
            day = generator.nextInt(28) + 1;
        else if(_31.contains(month))
            day = generator.nextInt(31) + 1;
        else
            day = generator.nextInt(30) + 1;

        int toAdd = generator.nextInt(2);

        return "'" + month + "/" + day + "/" + (year + toAdd) + "'";

    }

    public String makeRandomTime(){
        int hour = generator.nextInt(12) + 6;
        int minutes = generator.nextInt(59) + 1;

        if(minutes < 10) {
            return "'" + hour + ":0" + minutes + "'";
        }
        else {
            return "'" + hour + ":" + minutes + "'";
        }
    }

    public String makeRandomTimeBiggerThan(String start){
        int hour = generator.nextInt(3) + 1;

        start = start.replace("'", "");

        String [] time = start.split(":");

        return "'" + (Integer.parseInt(time[0]) + hour) + ":" + time[1] + "'";
    }

    public int diff(String end, String start){      //nie ma konferencji na przełomie miesiąca
        int eday = Integer.parseInt(end.split("/")[1]);
        int sday = Integer.parseInt(start.split("/")[1]);
        return eday - sday + 1;
    }


}
