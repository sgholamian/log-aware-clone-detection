//,temp,sample_752.java,2,11,temp,sample_8266.java,2,10
//,3
public class xxx {
public void testProcessor() throws Exception {
StopWatch watch = new StopWatch();
for (int i = 0; i < size; i++) {
Object out = template.requestBody("direct:a", "" + i);
assertEquals("Bye " + i, out);
}


log.info("processor took ms");
}

};