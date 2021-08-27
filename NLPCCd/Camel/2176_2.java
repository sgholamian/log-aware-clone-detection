//,temp,sample_6301.java,2,16,temp,sample_6302.java,2,16
//,2
public class xxx {
public void dummy_method(){
Thread.sleep(1000);
Order order = new Order() .setOrderId("Order-Java-0001") .setItemId("MILK") .setQuantity(3);
OrderResponse response = producer.requestBody("direct:java", order, OrderResponse.class);
Thread.sleep(1000);
Thread.sleep(1000);
String orderXml = "<order orderId=\"Order-XML-0001\" itemId=\"MIKAN\" quantity=\"365\"/>";
Exchange answerXml = producer.send("direct:xml", ex -> {
((DataTypeAware)ex.getIn()).setBody(orderXml, new DataType("xml:XMLOrder"));
});
Thread.sleep(1000);


log.info("csv log now contains");
}

};