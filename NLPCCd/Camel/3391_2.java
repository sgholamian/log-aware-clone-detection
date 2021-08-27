//,temp,sample_377.java,2,10,temp,sample_4901.java,2,17
//,3
public class xxx {
public void dummy_method(){
if (oldExchange == null) {
return newExchange;
}
String body = oldExchange.getIn().getBody(String.class);
if (body != null) {
Message newIn = newExchange.getIn();
String newBody = newIn.getBody(String.class);
if (newBody != null) {
body += " " + newBody;
}


log.info("invoked my strategy with result");
}
}

};