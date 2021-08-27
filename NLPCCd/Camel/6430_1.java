//,temp,sample_196.java,2,16,temp,sample_195.java,2,16
//,3
public class xxx {
public void dummy_method(){
try {
mllpClient.sendMessageAndWaitForAcknowledgement(Hl7TestMessageGenerator.generateMessage(3));
} catch (MllpJUnitResourceException resourceEx) {
}
mllpClient.disconnect();
Thread.sleep(1000);
mllpClient.connect();
mllpClient.setSendEndOfBlock(true);
mllpClient.setSendEndOfData(true);
String acknowledgement4 = mllpClient.sendMessageAndWaitForAcknowledgement(Hl7TestMessageGenerator.generateMessage(4));


log.info("sending test message");
}

};