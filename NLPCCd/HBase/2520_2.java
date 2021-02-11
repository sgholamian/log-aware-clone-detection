//,temp,sample_1052.java,2,16,temp,sample_1050.java,2,16
//,3
public class xxx {
public void dummy_method(){
byte[] b1 = Bytes.toBytes("testCancelOfMultiGet" + 0);
Put p = new Put(b1);
p.addColumn(f, b1, b1);
puts.add(p);
byte[] b2 = Bytes.toBytes("testCancelOfMultiGet" + 1);
p = new Put(b2);
p.addColumn(f, b2, b2);
puts.add(p);
table.put(puts);
flushRegion(hriPrimary);


log.info("flush done");
}

};