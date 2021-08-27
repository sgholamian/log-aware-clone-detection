//,temp,sample_4753.java,2,19,temp,sample_2471.java,2,17
//,3
public class xxx {
public void dummy_method(){
long remainingSize = totalAvailableMemory / 2;
Iterator<MapJoinOperator> it = sortedMapJoins.iterator();
long totalLargeJoins = 0;
while (it.hasNext()) {
MapJoinOperator mj = it.next();
long size = sizes.get(mj);
if (LOG.isDebugEnabled()) {
}
if (size < remainingSize) {
if (LOG.isInfoEnabled()) {


log.info("setting bytes needed for in mem");
}
}
}
}

};