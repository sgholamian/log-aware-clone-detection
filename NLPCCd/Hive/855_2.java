//,temp,sample_5528.java,2,16,temp,sample_5527.java,2,16
//,3
public class xxx {
public void dummy_method(){
Partition part_get = client.getPartition(dbName, tblName, part.getValues());
if(isThriftClient) {
adjust(client, part, dbName, tblName);
adjust(client, part2, dbName, tblName);
adjust(client, part3, dbName, tblName);
}
assertTrue("Partitions are not same", part.equals(part_get));
List<String> vals6 = makeVals("2016-02-22 00:00:00", "16");
Partition part6 = makePartitionObject(dbName, tblName, vals6, tbl, "/part5");
part6.getSd().setCols(null);


log.info("creating partition will null field schema");
}

};