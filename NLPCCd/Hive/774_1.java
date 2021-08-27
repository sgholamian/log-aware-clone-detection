//,temp,sample_2069.java,2,17,temp,sample_2068.java,2,17
//,3
public class xxx {
public void dummy_method(){
LOG.debug("Using Column Family=" + toString());
scan.fetchColumnFamily(cf);
for (Map.Entry<Key, Value> entry : scan) {
rowIds.add(new Range(entry.getKey().getColumnQualifier()));
if (rowIds.size() > maxRowIds) {
return null;
}
}
if (rowIds.isEmpty()) {
} else {


log.info("found index matches");
}
}

};