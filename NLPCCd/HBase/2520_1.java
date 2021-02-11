//,temp,sample_1052.java,2,16,temp,sample_1050.java,2,16
//,3
public class xxx {
public void dummy_method(){
for (int i = 0; i < NUMROWS; i++) {
byte[] b1 = Bytes.toBytes("testUseRegionWithReplica" + i);
Put p = new Put(b1);
p.addColumn(f, b1, b1);
table.put(p);
}
int caching = 20;
byte[] start;
start = Bytes.toBytes("testUseRegionWithReplica" + 0);
flushRegion(hriPrimary);


log.info("flush done");
}

};