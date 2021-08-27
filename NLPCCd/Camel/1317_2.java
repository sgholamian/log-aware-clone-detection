//,temp,sample_8438.java,2,14,temp,sample_7248.java,2,17
//,3
public class xxx {
public void dummy_method(){
DataHolder holder = ObjectHelper.cast(DataHolder.class, exchanges.poll());
EntityManager entityManager = holder.manager;
Exchange exchange = holder.exchange;
Object result = holder.result;
exchange.setProperty(Exchange.BATCH_INDEX, index);
exchange.setProperty(Exchange.BATCH_SIZE, total);
exchange.setProperty(Exchange.BATCH_COMPLETE, index == total - 1);
pendingExchanges = total - index - 1;
if (lockEntity(result, entityManager)) {
createPreDeleteHandler().deleteObject(entityManager, result, exchange);


log.info("processing exchange");
}
}

};