//,temp,sample_3596.java,2,8,temp,sample_7735.java,2,8
//,2
public class xxx {
public void test() throws Exception {
String result = template.requestBody("direct:out", "hello", String.class);
Assert.assertEquals("hello", result);


log.info("sleeping for seconds and no netty exception should occur");
}

};