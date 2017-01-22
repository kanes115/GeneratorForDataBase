package gen.baz;

import java.util.Random;

/**
 * Created by Kanes on 21.01.2017.
 */
public class Participants implements QueryMaker {
    private String query = "";
    private Random gen = new Random();
    private FileParser fgen = new FileParser("src/Firstnames");
    private FileParser lgen = new FileParser("src/Lastnames");
    private int counter = 1;
    private int range;

    private int privateClStart;
    private int privateClEnd;
    private int companyClStart;
    private int companyClEnd;


    public Participants(int privateClStart, int privateClEnd, int companyClStart, int companyClEnd){
        this.privateClStart = privateClStart;
        this.privateClEnd = privateClEnd;
        this.companyClStart = companyClStart;
        this.companyClEnd = companyClEnd;
    }



    public void prepareQuery(){
        preaprePrivateParticipants();
        prepareCompanyParticipants();
        this.range = this.counter;


    }


    private void preaprePrivateParticipants(){

        for(int i = privateClStart; i <= privateClEnd; i++){
            int amountOfPart = gen.nextInt(5) + 1;

            for(int j = 0; j < amountOfPart; j ++) {
                String firstname = fgen.getLines().get(gen.nextInt(fgen.getLines().size()));
                String lastname = lgen.getLines().get(gen.nextInt(lgen.getLines().size()));
                Boolean isStudent = gen.nextBoolean();

                query = query.concat("SET IDENTITY_INSERT Participants ON; \n");
                query = query.concat("INSERT INTO Participants (ParticipantID, Firstname, Lastname, Phone, ClientID) " +
                        "VALUES (" + counter + ", '" + firstname + "', '" + lastname + "', '" + generateRandomPhone() + "', " + i + ") \n");
                query = query.concat("SET IDENTITY_INSERT Participants OFF; \n");
                if(isStudent)
                    makeStudent(counter);
                counter ++;
            }
        }
    }

    private void prepareCompanyParticipants(){

        for(int i = companyClStart; i <= companyClEnd; i++){
            String firstname = fgen.getLines().get(gen.nextInt(fgen.getLines().size()));
            String lastname = lgen.getLines().get(gen.nextInt(lgen.getLines().size()));
            Boolean isStudent = gen.nextBoolean();

            query = query.concat("SET IDENTITY_INSERT Participants ON; \n");
            query = query.concat("INSERT INTO Participants (ParticipantID, Firstname, Lastname, Phone, ClientID) " +
                    "VALUES (" + counter + ", '" + firstname + "', '" + lastname + "', '" + generateRandomPhone() + "', " + i + ") \n");
            query = query.concat("SET IDENTITY_INSERT Participants OFF; \n");
            if(isStudent)
                makeStudent(counter);
            counter ++;
        }

    }


    private void makeStudent(int participantID){
        query = query.concat("SET IDENTITY_INSERT Students ON; \n");
        query = query.concat("INSERT INTO Students (StudentID, StudentsIDCardNo) VALUES (" + participantID + ", " + generateRandomPhone() + ") \n");
        query = query.concat("SET IDENTITY_INSERT Students OFF; \n");
    }





    public String getQuery(){
        return query;
    }





    public int getRange() {
        return range;
    }



    private String generateRandomPhone(){
        String res = "";
        for(int i = 0; i < 9; i++){
            res = res.concat(String.valueOf(gen.nextInt(10)));
        }
        return res;
    }

}
