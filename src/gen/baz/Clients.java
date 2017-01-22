package gen.baz;

import java.util.Random;

/**
 * Created by Kanes on 21.01.2017.
 */
public class Clients implements QueryMaker {

    private String query = "";
    private Random gen = new Random();
    private DateBuilder dateB = new DateBuilder();
    private int range;

    private int privateClientsRange;
    private int companyClientsRange;


    public Clients(int amountOfClients){
        if(amountOfClients % 2 == 0) this.range = amountOfClients;
        else this.range = amountOfClients + 1;
        this.privateClientsRange = range/2;
        this.companyClientsRange = range/2;

    }



    public void prepareQuery(){
        preparePrivateClients();
        preapreCompanyClients();

    }





    public String getQuery(){
        return query;
    }


    private void preapreCompanyClients(){
        FileParser cgen = new FileParser("src/Companies");

        for(int i = range/2; i < range; i++){
            String companyname = cgen.getLines().get(gen.nextInt(cgen.getLines().size()));

            query = query.concat("SET IDENTITY_INSERT Clients ON; \n");
            query = query.concat("INSERT INTO Clients (ClientID) VALUES (" + i + ") \n");
            query = query.concat("SET IDENTITY_INSERT Clients OFF; \n");
            query = query.concat("SET IDENTITY_INSERT Companies ON; \n");
            query = query.concat("INSERT INTO Companies (CompanyID, CompanyName, Phone) " +
                    "VALUES (" + i + ", '" + companyname + "', '" + generateRandomPhone() + "') \n");
            query = query.concat("SET IDENTITY_INSERT Companies OFF; \n");
        }

    }


    private void preparePrivateClients(){
        FileParser fgen = new FileParser("src/Firstnames");
        FileParser lgen = new FileParser("src/Lastnames");

        for(int i = 1; i < range/2; i++){
            String firstname = fgen.getLines().get(gen.nextInt(fgen.getLines().size()));
            String lastname = lgen.getLines().get(gen.nextInt(lgen.getLines().size()));

            query = query.concat("SET IDENTITY_INSERT Clients ON; \n");
            query = query.concat("INSERT INTO Clients (ClientID) VALUES (" + i + ") \n");
            query = query.concat("SET IDENTITY_INSERT Clients OFF; \n");
            query = query.concat("SET IDENTITY_INSERT PrivateClients ON; \n");
            query = query.concat("INSERT INTO PrivateClients (PrivateClientID, Firstname, Lastname, Phone)" +
                    " VALUES (" + i + ", '" + firstname + "', '" + lastname + "', '" + generateRandomPhone() + "') \n");
            query = query.concat("SET IDENTITY_INSERT PrivateClients OFF; \n");
        }
    }


    private String generateRandomPhone(){
        String res = "";
        for(int i = 0; i < 9; i++){
            res = res.concat(String.valueOf(gen.nextInt(10)));
        }
        return res;
    }

    public int getPrivateClientsRange() {
        return privateClientsRange;
    }

    public int getCompanyClientsRange() {
        return companyClientsRange;
    }

    public int getRange() {
        return range;
    }
}
