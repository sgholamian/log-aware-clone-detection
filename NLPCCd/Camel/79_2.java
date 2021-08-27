//,temp,sample_2218.java,2,11,temp,sample_107.java,2,10
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