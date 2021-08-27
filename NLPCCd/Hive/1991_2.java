//,temp,sample_5436.java,2,12,temp,sample_5435.java,2,12
//,2
public class xxx {
private int printMutation(Text table, Mutation m) {
if(LOG.isTraceEnabled()) {
Iterator itr = m.getUpdates().iterator();
while(itr.hasNext()) {
ColumnUpdate cu = (ColumnUpdate)itr.next();


log.info("table column");
}
}
}

};