//,temp,sample_5300.java,2,12,temp,sample_2947.java,2,9
//,3
public class xxx {
protected void fail(Object message) {
if (LOG.isDebugEnabled()) {
List<Exchange> list = getReceivedExchanges();
int index = 0;
for (Exchange exchange : list) {


log.info("failed and received");
}
}
}

};