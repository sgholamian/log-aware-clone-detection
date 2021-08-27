//,temp,sample_193.java,2,16,temp,sample_194.java,2,17
//,3
public class xxx {
public void dummy_method(){
expectedCompleteCount = 4;
expectedInvalidCount = 1;
setExpectedCounts();
NotifyBuilder notify1 = new NotifyBuilder(context).whenDone(2).create();
NotifyBuilder notify2 = new NotifyBuilder(context).whenDone(5).create();
mllpClient.connect();
mllpClient.setSoTimeout(10000);
String acknowledgement1 = mllpClient.sendMessageAndWaitForAcknowledgement(Hl7TestMessageGenerator.generateMessage(1));
String acknowledgement2 = mllpClient.sendMessageAndWaitForAcknowledgement(Hl7TestMessageGenerator.generateMessage(2));
assertTrue("First two normal exchanges did not complete", notify1.matches(RESPONSE_TIMEOUT, TimeUnit.MILLISECONDS));


log.info("sending test message");
}

};