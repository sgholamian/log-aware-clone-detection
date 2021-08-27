//,temp,sample_4623.java,2,10,temp,sample_2855.java,2,10
//,3
public class xxx {
protected void sendMessage(String bodyText) {
try {
template.sendBody(url, bodyText);
} catch (Exception e) {


log.info("error sending");
}
}

};