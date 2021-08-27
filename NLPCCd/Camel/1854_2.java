//,temp,sample_2383.java,2,9,temp,sample_7247.java,2,9
//,2
public class xxx {
public int processBatch(Queue<Object> exchanges) throws Exception {
int total = exchanges.size();
if (maxMessagesPerPoll > 0 && total > maxMessagesPerPoll) {


log.info("limiting to maximum messages to poll as there were messages in this poll");
}
}

};