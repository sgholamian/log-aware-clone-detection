//,temp,sample_5459.java,2,16,temp,sample_2111.java,2,16
//,3
public class xxx {
public void dummy_method(){
Put put3 = new Put(Bytes.toBytes("zzz3"));
put3.addColumn(FAMILY, QUALIFIER, VALUE);
ht.put(Arrays.asList(put1, put2, put3));
Scan scan1 = new Scan();
int numRecords = 0;
ResultScanner scanner = ht.getScanner(scan1);
for(Result result : scanner) {
numRecords++;
}
scanner.close();


log.info("test data has records");
}

};