//,temp,sample_6226.java,2,11,temp,sample_110.java,2,14
//,3
public class xxx {
protected void setUp() throws Exception {
utf = "ABC\u00e6".getBytes("utf-8");
deleteDirectory("target/charset");
createDirectory("target/charset/input");
for (byte b : utf) {


log.info("utf byte");
}
}

};