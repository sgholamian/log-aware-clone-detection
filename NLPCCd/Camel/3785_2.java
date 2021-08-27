//,temp,sample_1741.java,2,10,temp,sample_1743.java,2,10
//,3
public class xxx {
public void testNoGroupMessage() throws Exception {
String message = "8=FIX 4.19=2034=135=049=INVMGR56=BRKR" + "1=BE.CHM.00111=CHM0001-0158=this is a camel - bindy test" + "10=220" + "777=22-06-2013 12:21:11";
List<String> data = Arrays.asList(message.split("\\u0001"));
CamelContext camelContext = new DefaultCamelContext();
factory.bind(camelContext, data, model, counter);


log.info("model");
}

};