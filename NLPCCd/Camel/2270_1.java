//,temp,sample_1689.java,2,10,temp,sample_6513.java,2,10
//,2
public class xxx {
protected void sendMessage(String bodyText) {
try {
template.sendBody(url, bodyText);
} catch (Exception e) {


log.info("error sending");
}
}

};