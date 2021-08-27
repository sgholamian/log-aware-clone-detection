//,temp,sample_5587.java,2,17,temp,sample_4665.java,2,18
//,3
public class xxx {
public void dummy_method(){
if (begin) {
retryCounter++;
polledMessages = getConsumer().poll();
if (polledMessages == 0 && sendEmptyMessageWhenIdle) {
processEmptyMessage();
} else if (polledMessages == 0 && timeout > 0) {
done = false;
}
pollStrategy.commit(getConsumer(), getEndpoint(), polledMessages);
} else {


log.info("cannot begin polling as pollstrategy returned false");
}
}

};