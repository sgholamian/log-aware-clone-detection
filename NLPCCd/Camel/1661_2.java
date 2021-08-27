//,temp,sample_6305.java,2,16,temp,sample_6303.java,2,16
//,3
public class xxx {
public void dummy_method(){
OrderResponse response = producer.requestBody("direct:java", order, OrderResponse.class);
Thread.sleep(1000);
Thread.sleep(1000);
String orderXml = "<order orderId=\"Order-XML-0001\" itemId=\"MIKAN\" quantity=\"365\"/>";
Exchange answerXml = producer.send("direct:xml", ex -> {
((DataTypeAware)ex.getIn()).setBody(orderXml, new DataType("xml:XMLOrder"));
});
Thread.sleep(1000);
Thread.sleep(1000);
String orderJson = "{\"orderId\":\"Order-JSON-0001\", \"itemId\":\"MIZUYO-KAN\", \"quantity\":\"16350\"}";


log.info("sending to direct json");
}

};