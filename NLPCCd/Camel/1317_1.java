//,temp,sample_8438.java,2,14,temp,sample_7248.java,2,17
//,3
public class xxx {
public int processBatch(Queue<Object> exchanges) throws Exception {
int total = exchanges.size();
for (int index = 0; index < total && isBatchAllowed(); index++) {
Exchange exchange = ObjectHelper.cast(Exchange.class, exchanges.poll());
exchange.setProperty(Exchange.BATCH_INDEX, index);
exchange.setProperty(Exchange.BATCH_SIZE, total);
exchange.setProperty(Exchange.BATCH_COMPLETE, index == total - 1);
pendingExchanges = total - index - 1;


log.info("processing exchange");
}
}

};