//,temp,sample_1261.java,2,18,temp,sample_2332.java,2,19
//,3
public class xxx {
public void dummy_method(){
if (context.linkOpWithWorkMap.containsKey(mj)) {
Map<BaseWork, SparkEdgeProperty> linkWorkMap = context.linkOpWithWorkMap.get(mj);
if (linkWorkMap != null) {
if (context.linkChildOpWithDummyOp.containsKey(mj)) {
for (Operator<?> dummy: context.linkChildOpWithDummyOp.get(mj)) {
work.addDummyOp((HashTableDummyOperator) dummy);
}
}
for (Entry<BaseWork, SparkEdgeProperty> parentWorkMap : linkWorkMap.entrySet()) {
BaseWork parentWork = parentWorkMap.getKey();


log.info("connecting with");
}
}
}
}

};