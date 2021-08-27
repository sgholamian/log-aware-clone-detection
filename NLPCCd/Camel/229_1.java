//,temp,sample_4803.java,2,10,temp,sample_4805.java,2,12
//,3
public class xxx {
protected void setUp() throws Exception {
utf = "ABC\u00e6".getBytes("utf-8");
iso = "ABC\u00e6".getBytes("iso-8859-1");
deleteDirectory("target/charset");
createDirectory("target/charset/input");


log.info("utf utf");
}

};