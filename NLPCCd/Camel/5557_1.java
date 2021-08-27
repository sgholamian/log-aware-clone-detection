//,temp,sample_8117.java,2,9,temp,sample_8118.java,2,12
//,3
public class xxx {
public void process(Exchange exchange) throws Exception {
List<Exchange> entryList = prepareExchangeList(exchange);
if (entryList == null || entryList.size() == 0) {


log.info("the incoming message is either null or empty triggered by an aggregation timeout");
}
}

};