//,temp,sample_259.java,2,18,temp,sample_257.java,2,18
//,2
public class xxx {
public void dummy_method(){
scanner1.close();
assertEquals(1, res1.length);
assertEquals(3, res1[0].getColumnCells(famName, qualifierName).size());
for (int i = 0; i < NB_RETRIES; i++) {
scan = new Scan();
scan.readVersions(100);
scanner1 = htable2.getScanner(scan);
res1 = scanner1.next(1);
scanner1.close();
if (res1.length != 1) {


log.info("only got rows");
}
}
}

};