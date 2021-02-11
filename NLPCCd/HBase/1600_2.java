//,temp,sample_5459.java,2,16,temp,sample_2111.java,2,16
//,3
public class xxx {
public void dummy_method(){
HTable t1 = (HTable) conn.getTable(table1);
Put p1;
for (int i = 0; i < NB_ROWS_IN_BATCH; i++) {
p1 = new Put(Bytes.toBytes("row-t1" + i));
p1.addColumn(famName, qualName, Bytes.toBytes("val" + i));
t1.put(p1);
}
Assert.assertEquals(TEST_UTIL.countRows(t1), NB_ROWS_IN_BATCH * 2);
t1.close();
int NB_ROWS2 = 20;


log.info("bulk loading into");
}

};