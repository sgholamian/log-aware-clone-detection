//,temp,sample_6300.java,2,16,temp,sample_6297.java,2,16
//,3
public class xxx {
public void dummy_method(){
File csvLogFile = new File(CSV_PATH);
if (csvLogFile.exists()) {
csvLogFile.delete();
}
ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("META-INF/spring/camel-context.xml");
context.start();
CamelContext camelContext = context.getBean("order", CamelContext.class);
ProducerTemplate producer = camelContext.createProducerTemplate();
Thread.sleep(1000);
Order order = new Order() .setOrderId("Order-Java-0001") .setItemId("MILK") .setQuantity(3);


log.info("sending to direct java");
}

};