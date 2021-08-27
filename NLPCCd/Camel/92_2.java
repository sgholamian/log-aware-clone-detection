//,temp,sample_4260.java,2,10,temp,sample_4402.java,2,9
//,3
public class xxx {
public int processBatch(Queue<Object> exchanges) throws Exception {
int total = exchanges.size();
if (maxMessagesPerPoll > 0 && total > maxMessagesPerPoll) {


log.info("limiting to maximum messages to poll as there were messages in this poll");
}
}

};