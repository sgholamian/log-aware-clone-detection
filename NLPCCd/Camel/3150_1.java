//,temp,sample_4987.java,2,10,temp,sample_4986.java,2,9
//,3
public class xxx {
public void onMessage(AMQP.BasicProperties properties, byte[] message) {
String correlationID = properties.getCorrelationId();
if (correlationID == null) {
return;
}


log.info("received reply message with correlationid");
}

};