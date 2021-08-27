//,temp,sample_4712.java,2,11,temp,sample_4711.java,2,9
//,3
public class xxx {
public void setUp() throws Exception {
super.setUp();
byte[] iso = payload.getBytes("iso-8859-1");
byte[] utf = payload.getBytes("utf-8");
for (byte b : iso) {


log.info("iso byte");
}
}

};