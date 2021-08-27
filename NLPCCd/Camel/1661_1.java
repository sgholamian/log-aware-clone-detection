//,temp,sample_6305.java,2,16,temp,sample_6303.java,2,16
//,3
public class xxx {
public void dummy_method(){
Exchange answerXml = producer.send("direct:xml", ex -> {
((DataTypeAware)ex.getIn()).setBody(orderXml, new DataType("xml:XMLOrder"));
});
Thread.sleep(1000);
Thread.sleep(1000);
String orderJson = "{\"orderId\":\"Order-JSON-0001\", \"itemId\":\"MIZUYO-KAN\", \"quantity\":\"16350\"}";
Exchange answerJson = producer.send("direct:json", ex -> {
((DataTypeAware)ex.getIn()).setBody(orderJson, new DataType("json"));
});
Thread.sleep(1000);


log.info("csv log now contains");
}

};