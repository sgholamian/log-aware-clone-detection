//,temp,sample_4806.java,2,14,temp,sample_389.java,2,14
//,2
public class xxx {
protected void setUp() throws Exception {
utf = "ABC\u00e6".getBytes("utf-8");
iso = "ABC\u00e6".getBytes("iso-8859-1");
deleteDirectory("target/charset");
createDirectory("target/charset/input");
for (byte b : utf) {
}
for (byte b : iso) {


log.info("iso byte");
}
}

};