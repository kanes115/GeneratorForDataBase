package gen.baz;

/**
 * Created by Kanes on 21.01.2017.
 */
public class Generator implements QueryMaker {
    private String mainQuery = "";
    private String mainQuery2 = "";
    private String mainQuery3 = "";
    private String mainQuery4 = "";
    private String mainQuery5 = "";
    private String mainQuery6 = "";

    public void prepareQuery(){
        CountriesCities c = new CountriesCities();      //adding countries and citities
        c.prepareQuery();
        mainQuery = mainQuery.concat(c.getQuery());

        Conferences conf = new Conferences(c.getRange());
        conf.prepareQuery();
        mainQuery2 = mainQuery2.concat(conf.getQuery());

        Workshops work = new Workshops(conf.getRange());
        work.prepareQuery();
        mainQuery3 = mainQuery3.concat(work.getQuery());

        Clients cl = new Clients(500);              // tworzy najpierw od 1 - 499 prywatnych klientów, a potem od 500 do 999 firmowych
        cl.prepareQuery();
        mainQuery4 = mainQuery4.concat(cl.getQuery());

        Participants part = new Participants(1, 249, 250, 499);
        part.prepareQuery();
        mainQuery5 = mainQuery5.concat(part.getQuery());

        Reservations res = new Reservations(conf.getRange(), 1, 249, 250, 499, conf.getDays());
        res.prepareQuery();
        mainQuery6 = mainQuery6.concat(res.getQuery());

    }



    public String getQuery(){
        return mainQuery + mainQuery2 + mainQuery3 + mainQuery4 + mainQuery5 + mainQuery6;
    }

    public String getMainQuery() {
        return mainQuery;
    }

    public String getMainQuery2() {
        return mainQuery2;
    }

    public String getMainQuery3() {
        return mainQuery3;
    }

    public String getMainQuery4() {
        return mainQuery4;
    }

    public String getMainQuery5() {
        return mainQuery5;
    }

    public String getMainQuery6() {
        return mainQuery6;
    }
}
