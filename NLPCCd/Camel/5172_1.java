//,temp,sample_5666.java,2,10,temp,sample_2446.java,2,10
//,3
public class xxx {
public int processBatch(Queue<Object> exchanges) throws Exception {
final IBatisEndpoint endpoint = getEndpoint();
int total = exchanges.size();
if (maxMessagesPerPoll > 0 && total > maxMessagesPerPoll) {


log.info("limiting to maximum messages to poll as there were messages in this poll");
}
}

};