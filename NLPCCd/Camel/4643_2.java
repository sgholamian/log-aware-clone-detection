//,temp,sample_7592.java,2,10,temp,sample_7593.java,2,11
//,2
public class xxx {
private void handleNickInUse() {
IRCConnection connection = component.getIRCConnection(configuration);
String nick = connection.getNick() + "-";
if (nick.endsWith("----")) {
} else {


log.info("unable to set nick retrying with");
}
}

};