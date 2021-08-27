//,temp,sample_62.java,2,13,temp,sample_4010.java,2,14
//,3
public class xxx {
public void tearDown() {
try {
if (!isClosed) {
super.close();
}
isClosed = true;
} catch (Exception e) {


log.info("error closing hive metastore client ignored");
}
}

};