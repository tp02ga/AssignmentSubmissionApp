import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class AssignmentSubmissionConverterTest {

    @Test
    public void converter() {
        String input = "Scott Garcia\t9768\tCompleted (Marianne) Nov 8: https://github.com/ScottyP666/ScottGarcia-Week0Assignment1\tCompleted (Marianne) Nov 9: https://github.com/ScottyP666/ScottGarcia-Assignment2.git\tCompleted (Marianne) Nov 20: https://github.com/ScottyP666/ScottGarcia-Assignment3.git\tCompleted (Roche) Jan 6: https://github.com/ScottyP666/ScottGarcia-Assignment4.git\tCompleted  (Roche) Jan 18: https://github.com/ScottyP666/ScottGarcia-Assignment5.git\tCompleted (Roche) Feb 1: https://github.com/ScottyP666/ScottGarcia-Assignment6.git\tCompleted (Roche) Feb 8: https://github.com/ScottyP666/ScottGarcia-Assignment7.git\tCompleted (Roche) Feb 18: https://github.com/ScottyP666/ScottGarcia-Assignment8.git\tCompleted (Roche) Feb 25: https://github.com/ScottyP666/ScottGarcia-Assignment9.git\tCompleted (Roche) Mar 8: https://github.com/ScottyP666/ScottGarcia-Assignment10.git\tCompleted (Roche) Apr 1: https://github.com/ScottyP666/ScottGarcia-Assignment11.git\tCompleted(Marianne) Apr 20: https://github.com/ScottyP666/ScottGarcia-Assignment12.git\t\t\t\n" +
                "Joseph Schlosser\t9843\tCompleted (Marianne) Nov 19: https://github.com/JosephSch1/Assignment1.git\tCompleted (Marianne) Nov 28: https://github.com/JosephSch1/Joseph-assignment-2.git\tCompleted (Marianne) Dec 7: https://github.com/JosephSch1/Joseph-assignment-3.git\tCompleted (Marianne) Dec 22: https://github.com/JosephSch1/Joseph-Assignment-4.git\tCompleted (Roche) Jan 2: https://github.com/JosephSch1/Assignment-5.git\tCompleted (Roche) Jan 11: https://github.com/JosephSch1/Joseph-Assignment-6.git\tCompleted (Roche) Jan 18: https://github.com/JosephSch1/Joseph-Assignment-7.git\tCompleted (Roche) Jan 27: https://github.com/JosephSch1/Assignment-8.git\tCompleted (Roche) Feb 9: https://github.com/JosephSch1/Assignment9/tree/master\tCompleted (Roche)  Feb 20: https://github.com/JosephSch1/Assignment-10.git\tCompleted  (Roche) Mar 8: https://github.com/JosephSch1/Assignment-11.git\tCompleted(Marianne) Apr 2: https://github.com/JosephSch1/Assignment-12.git\tCompleted(Marianne) Apr 9: https://github.com/JosephSch1/Assignment-13.git\tCompleted(Marianne) Apr 28: https://github.com/JosephSch1/Assignment14.git\t\n" +
                "Jeff Dimanche\t9937\tCompleted (Marianne) Nov 7: https://github.com/Djehle/Jeff-assignment\tCompleted (Roche) Dec 28: https://github.com/Djehle/Jeff-java-bootcamp/tree/master/Jeff-assignment-2/src/com/codersbootcamp/objects\tCompleted (Roche) Dec 28: https://github.com/Djehle/Jeff-java-bootcamp/tree/master/Jeff-assignment-3\tCompleted (Roche) Jan 6: https://github.com/Djehle/Jeff-java-bootcamp/tree/master/Assignment%204_Jeff\tCompleted (Roche) Jan 16: https://github.com/Djehle/Jeff-java-bootcamp/tree/master/Jeff-Assignment-5/src/com/coderscampus/arraylist\tCompleted (Roche) Jan 31: https://github.com/Djehle/Jeff-java-bootcamp/tree/master/Jeff-Assignment_6\tCompleted (Roche) Feb 8:https://github.com/Djehle/Assignment_7_Jeff\tCompleted (Roche) Feb 8: https://github.com/Djehle/Assignment_8_Jeff\tCompleted (Roche) Feb 27: https://github.com/Djehle/Assignment_9_Jeff\tCompleted (Roche) Feb 27: https://github.com/Djehle/Assignment_10_Jeff\t\t\t\t\t\n" +
                "Melbae Abernathy\t10068\tCompleted (Marianne) Nov 7: https://github.com/m131601/melbae-assignment-1\t\t\t\t\t\t\t\t\t\t\t\t\t\t\n" +
                "Joan Alogavi\t7863\tCompleted Nov 8: https://github.com/jalogavi92/HTML-Joan-Alogavi-Assignment-2\t\t\t\t\t\t\t\t\t\t\t\t\t\t\n" +
                "Ercan Tekoglen\t10239\tCompleted (Marianne) Nov 14: https://github.com/ercantekoglan/ercan-week1-assignment-1\tCompleted (Marianne) Nov 25: https://github.com/ercantekoglan/CodersCampusBootcamp/tree/week2-assignment/assignent2\tCompleted (Marianne) Dec 1: https://github.com/ercantekoglan/CodersCampusBootcamp/tree/week5-Assignment3\tCompleted (Marianne)Dec 7: https://github.com/ercantekoglan/CodersCampusBootcamp/tree/main/Assignment4\tCompleted (Roche) Dec 23: https://github.com/ercantekoglan/CodersCampusBootcamp/tree/main/Assignment_5\tCompleted (Roche) Dec 27: https://github.com/ercantekoglan/CodersCampusBootcamp/tree/main/Assignment_6\tCompleted (Roche) Jan 6: https://github.com/ercantekoglan/CodersCampusBootcamp/tree/main/Assignment_7\tCompleted (Roche) Jan 16: https://github.com/ercantekoglan/CodersCampusBootcamp/tree/main/Assignment8\tCompleted (Roche) Jan 23: https://github.com/ercantekoglan/CodersCampusBootcamp/tree/main/assignment9\tCompleted (Roche) Feb 6: https://github.com/ercantekoglan/Assignment-10\tCompleted (Roche) Feb 8: https://github.com/ercantekoglan/Assignment-11\tCompleted (Roche) Feb 25: https://github.com/ercantekoglan/Pizza_Order_Database/blob/main/PizzaDatabase.sql\tCompleted(Marianne) Mar 18: https://github.com/ercantekoglan/Bank_Application\tCompleted(Marianne) Apr 13: https://github.com/ercantekoglan/Chat_Application\tCompleted(Marianne) May 1: https://github.com/ercantekoglan/USA-Farmers\n" +
                "Rimpy Arora\t3823\tCompleted (Marianne) Dec 1: https://github.com/rimpy106/Assignment1\tCompleted (Roche) Jan 2: https://github.com/rimpy106/rimpy-assignment-2.git\tCompleted  (Roche) Jan 20: https://github.com/rimpy106/rimpy-assignment-3.git\tCompleted (Roche) Feb 1: https://github.com/rimpy106/rimpy-assignment-4.git\tCompleted (Roche) Feb 7: https://github.com/rimpy106/rimpy-assignment-5\tCompleted (Roche) Feb 21: https://github.com/rimpy106/rimpy-assignment-6.git\tStarted (Roche) Mar 1: https://github.com/rimpy106/rimpy-assignment-7.git\tCompleted (Roche) Mar 9: https://github.com/rimpy106/rimpy-assignment-8.git\tCompleted (Roche) Apr 13: https://github.com/rimpy106/rimpy-assignment9/tree/main/rimpy-assignment9\tStarted (Roche) Apr 27: https://github.com/rimpy106/rimpy-assignment10.git\t\t\t\t\t\n" +
                "Inga Khadanovich\t10303\tCompleted (Marianne) Dec 25: https://github.com/Tyapaevair/inga-assignment1.git\tCompleted (Roche) Dec 23: https://github.com/Tyapaevair/java-bootcamp/blob/main/Assignment2/src/com/codercampus/assignment2/GuessGame.java\tCompleted (Roche) Jan 8: https://github.com/Tyapaevair/inga-assignment3\tCompleted (Roche) Feb 1: https://github.com/Tyapaevair/inga-assignment4.git\tCompleted (Roche) Mar 15: https://github.com/Tyapaevair/Assignment5.git\tCompleted (Roche) Mar 28: https://github.com/Tyapaevair/Assignment6.git\t\t\tCompleted (Roche) May 9: https://github.com/Tyapaevair/inga-assignment9.git\t\t\t\t\t\t\n" +
                "Moe Alquraishi\t10325\t\t\t\t\t\t\t\t\tCompleted (Roche) Mar 17: https://github.com/MohJumper/RestApi-consuming-CSVRecord-File\tStarted (Roche) Mar 28: https://github.com/MohJumper/meals-planner-api\tStarted (Roche) Mar 28: https://github.com/MohJumper/display-data-prac11/tree/ma/showdata-pract11\t\t\t\t\n" +
                "Rasmus (Rini) Pfeiffer\t9782\tCompleted(Marianne) Jan 16: https://github.com/RasmusPfeiffer/Week-1-Assignment-2\tCompleted (Roche) Jan 17: https://github.com/RasmusPfeiffer/Week-3-4-Assignment-2\tCompleted (Roche) Jan 21: https://github.com/RasmusPfeiffer/Assignment-3-Week-5\tCompleted (Roche) Mar 12: https://github.com/RasmusPfeiffer/Week-7Assignment-4\tCompleted (Roche) Mar 20: https://github.com/RasmusPfeiffer/Week-9-Assignment-5\tCompleted (Roche) Mar 27: https://github.com/RasmusPfeiffer/Week-10-Assignment-6\tCompleted (Roche) Mar 31: https://github.com/RasmusPfeiffer/Week-11-Assignment-7/tree/master/assignment7\tCompleted (Roche) Apr 18: https://github.com/RasmusPfeiffer/Week-12-Assignment-8\t\t\t\t\t\t\t\n" +
                "Stefan Hollander\t10399\tCompleted(Marianne) Jan 31: https://github.com/SteveDutch/stefan-assignment-1.git\tCompleted (Roche) Feb 21: https://github.com/SteveDutch/stefan-assignment-2.git\tCompleted (Roche) Mar 13: https://github.com/SteveDutch/stefan-assignment-3.git\tCompleted (Roche) Apr 5: https://github.com/SteveDutch/stefan-assignment-4.git\tCompleted (Roche) Apr 11: https://github.com/SteveDutch/stefan-assignment-5.git\t\t\t\t\t\t\t\t\t\t\n" +
                "Marcus Miceli\t10421\tCompleted(Marianne) Feb 7: https://github.com/marcusmiceli/marcus-assignment-1\tCompleted (Roche) Feb 18: https://github.com/marcusmiceli/marcus-assignment-2.git\t\t\t\t\t\t\t\t\t\t\t\t\t\n" +
                "Tristan Dan Le\t279\tCompleted(Marianne) Mar 1: https://github.com/tristandanle/tristan-assigment-1\tCompleted (Roche) Mar 16: https://github.com/tristandanle/tristan-assigment-2\tCompleted (Roche) March 16: https://github.com/tristandanle/tristan-assignment-3\tCompleted (Roche) Mar: 16 https://github.com/tristandanle/tristan-assignment-4\tCompleted (Roche) Apr 1: https://github.com/tristandanle/tristan-assignement-5\tCompleted (Roche) Apr 1: https://github.com/tristandanle/tristan-assignment-6\tCompleted (Roche) Apr 21: https://github.com/tristandanle/tristan-assignment-7\tCompleted(Roche) Apr 21: https://github.com/tristandanle/tristan-assignment-8\tCompleted (Roche) Apr 21: https://github.com/tristandanle/tristan-assignment-9\tCompleted (Roche) Apr 21: https://github.com/tristandanle/tristan-assignment-10\tCompleted (Roche) Apr 21: https://github.com/tristandanle/tristan-assignment11\tCompleted (Roche) Apr 30: https://github.com/tristandanle/tristan-assignment-12\t\t\t\n" +
                "Joseph Kirkish\t9843\tCompleted(Marianne) Mar 7: https://github.com/jkirkish/Joseph-assignment-1.git\tCompleted (Roche) Mar 28: https://github.com/jkirkish/Assignment-2.git\tCompleted (Roche) Apr 5: https://github.com/jkirkish/Assignment-3.git\tCompleted (Roche) May 1: https://github.com/jkirkish/Assignment-4.git\tCompleted (Roche) May 8: https://github.com/jkirkish/Assignment-5.git\tCompleted (Roche) May 13: https://github.com/jkirkish/Assignment-6.git\t\t\t\t\t\t\t\t\t\n" +
                "Oksana Moshnaia\t10520\tCompleted(Marianne) Mar 21: https://github.com/Buba4ka/Oksana_Assignment_1\tCompleted (Roche) Mar 27: https://github.com/Buba4ka/Oksana_Assignment_2.git\tCompleted (Roche) Apr 4: https://github.com/Buba4ka/Oksana_Assignment_3.git\tCompleted (Roche) Apr 25: https://github.com/Buba4ka/Oksana_Assignment_4\t\t\t\t\t\t\t\t\t\t\t\n" +
                "Dawnena Key\t10522\tStarted Redo Required (Marianne) Mar 30: https://github.com/dawnenakey/htmlassignment1\tCompleted (Roche) Apr 15: https://github.com/dawnenakey/key_assignment_2\tCompleted (Roche) May 3: https://github.com/dawnenakey/assignment3\t\t\t\t\t\t\t\t\t\t\t\t\n" +
                "Brandon Riley\t10465\tCompleted(Marianne) Apr 4: https://github.com/bronin11/Brandon.Riley-Assignment-1.git\tCompleted (Roche) May 7: https://github.com/bronin11/Brandon-Assignment/blob/e325ee52a54c65ea13438d3bd8a497a69a6b3357/src/com/coderscampus/Assignment2Application.java\t\t\t\t\t\t\t\t\t\t\t\t\t";

        String[] rawStudentsData = input.split("\n");
        Arrays.stream(rawStudentsData).forEach(rawStudentData -> {
            String[] assignmentData = rawStudentData.split("\t");
            String name = assignmentData[0];
            Integer userId = Integer.parseInt(assignmentData[1]);
            for (int i = 2; i < assignmentData.length; i++) {
                int assignmentNum = i - 1;
                String data = assignmentData[i].toLowerCase();
                String status;
                String codeReviewVideoUrl = "";
                if (data.contains("completed")) {
                    status = "Completed";
                    codeReviewVideoUrl = "https://memegenerator.net/img/instances/65963067/wow-much-look-nothing-to-see-here-go-work.jpg";
                } else {
                    status = "Needs Update";
                }
                String[] gitHubData = data.split("github.com");
                if (gitHubData.length < 2) continue;
                String github = "https://github.com" + gitHubData[1];

                StringBuffer sql = new StringBuffer();
                sql.append("insert into assignment (code_review_video_url, github_url, number, status, code_reviewer_id, user_id) values (");
                sql.append("'" + codeReviewVideoUrl + "', '" + github + "', " + assignmentNum + ", '" + status + "', 7426, " + userId);
                sql.append(");");
                System.out.println(sql);
            }
        });

    }

    @Test
    public void name_match() {
        Map<String, Integer> nameToUserIdMap = new HashMap<>();

        populate(nameToUserIdMap);

        String names = "Scott Garcia\n" +
                "Joseph Schlosser\n" +
                "Jeff Dimanche\n" +
                "Melbae Abernathy\n" +
                "Joan Alogavi\n" +
                "Ercan Tekoglen\n" +
                "Rimpy Arora\n" +
                "Inga Khadanovich\n" +
                "Mohammed Alquraishi\n" +
                "Rasmus (Rini) Pfeiffer\n" +
                "Stefan Hollander\n" +
                "Marcus Miceli\n" +
                "Tristan Dan Le\n" +
                "Joseph Kirkish\n" +
                "Oksana Moshnaia\n" +
                "Dawnena Key\n" +
                "Brandon Riley";

        String[] data = names.split("\n");
        Arrays.stream(data)
                .forEach(name -> {
//                    System.out.println(name.split(" ")[0] + " -> " + nameToUserIdMap.get(name.split(" ")[0].toLowerCase()));
                    System.out.println(nameToUserIdMap.get(name.split(" ")[0].toLowerCase()));
                });

    }

    private void populate(Map<String, Integer> nameToUserIdMap) {
        String data = "7394\tdavid\n" +
                "6866\tFenix Xia\n" +
                "7316\tAdam Holden\n" +
                "3754\tTimothy Smith\n" +
                "3600\tLorenzo\n" +
                "7182\tDaniel Berkness\n" +
                "7556\tLeslie Fleming\n" +
                "7184\tJonny\n" +
                "4875\tSebastian\n" +
                "7603\tOtto Nema\n" +
                "6244\tViktoryia\n" +
                "6756\tMina K. Fahmy\n" +
                "8116\tIvan Flores\n" +
                "9040\tNiles Dobbs\n" +
                "8315\tDustin\n" +
                "7963\tIsaac Ndjepel\n" +
                "9227\tAlvin Revilas\n" +
                "8621\tMurtaza Kanorwala\n" +
                "9595\tSam Rondot\n" +
                "9338\tIoan Gherendi\n" +
                "9633\tRicardo Alatorre\n" +
                "5337\tVince\n" +
                "4266\tDina\n" +
                "9459\tWilliam\n" +
                "4137\tJoshua\n" +
                "5582\tEthan\n" +
                "9713\tRamon Rosario\n" +
                "9768\tScott\n" +
                "9843\tJoseph Schlosser\n" +
                "9937\tJeff E Dimanche\n" +
                "10042\tCrystal Valentine\n" +
                "7863\tJoan Alogavi\n" +
                "10068\tMelbae Abernathy\n" +
                "10239\tErcan Tekoglan\n" +
                "10190\tEddy Brown II \n" +
                "3823\tRimpy\n" +
                "10303\tInga Khadanovich \n" +
                "10325\tMohammed Al\n" +
                "10049\tZahraa Mohamad\n" +
                "9782\tRasmus\n" +
                "10374\tJulie Chu\n" +
                "10399\tStefan\n" +
                "10420\tYana\n" +
                "10421\tMarcus Miceli\n" +
                "147\tJoe\n" +
                "279\tTristan Dan le\n" +
                "10453\tPeter Nguyen\n" +
                "176\tPat\n" +
                "10465\tBrandon Riley\n" +
                "10520\tOksana\n" +
                "10522\tDawnena Key\n" +
                "217\tMartin O'Grady\n" +
                "10662\tLisa Bollozos\n" +
                "9679\takshay\n" +
                "10830\tMarvel Devera\n" +
                "10829\tTyler";

        String[] studentsData = data.split("\n");
        Arrays.stream(studentsData).forEach(s -> {
            String[] studentData = s.split("\t");
            String userId = studentData[0];
            String fullName = studentData[1];
            if (nameToUserIdMap.containsKey(fullName.split(" ")[0])) {
                System.out.println("duplicate name: " + fullName.split(" ")[0]);
            }
            nameToUserIdMap.put(fullName.split(" ")[0].toLowerCase(), Integer.parseInt(userId));
        });


    }
}
