//,temp,sample_3156.java,2,11,temp,sample_3155.java,2,10
//,2
public class xxx {
public boolean processAnswers(long agentId, long seq, Answer[] answers) {
for (Answer answer : answers) {
if (answer.getResult() == false) {


log.info("unable to execute sync command");
}
}
}

};