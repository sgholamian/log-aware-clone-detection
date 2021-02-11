//,temp,sample_2582.java,2,18,temp,sample_2589.java,2,18
//,2
public class xxx {
public void dummy_method(){
for (int i = 0; i < numberOfTests + 1; i++) {
Collection<KeyValue> kvSet;
Scan scan = new Scan();
scan.setMaxVersions();
if (i < numberOfTests) {
if (columnLists[i].isEmpty()) continue;
kvSet = kvMaps[i].values();
for (String column : columnLists[i]) {
scan.addColumn(familyBytes, Bytes.toBytes(column));
}


log.info("columns keys");
}
}
}

};