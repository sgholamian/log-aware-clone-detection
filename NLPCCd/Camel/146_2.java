//,temp,sample_5587.java,2,17,temp,sample_4665.java,2,18
//,3
public class xxx {
public void dummy_method(){
if (begin) {
retryCounter++;
polledMessages = poll();
if (polledMessages == 0 && isSendEmptyMessageWhenIdle()) {
processEmptyMessage();
}
pollStrategy.commit(this, getEndpoint(), polledMessages);
if (polledMessages > 0 && isGreedy()) {
done = false;
retryCounter = -1;


log.info("greedy polling after processing messages");
}
}
}

};