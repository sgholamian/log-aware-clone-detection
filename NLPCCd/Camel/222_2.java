//,temp,sample_3017.java,2,9,temp,sample_4528.java,2,10
//,3
public class xxx {
public int processBatch(Queue<Object> exchanges) throws Exception {
final MyBatisEndpoint endpoint = getEndpoint();
int total = exchanges.size();
if (maxMessagesPerPoll > 0 && total > maxMessagesPerPoll) {


log.info("limiting to maximum messages to poll as there were messages in this poll");
}
}

};