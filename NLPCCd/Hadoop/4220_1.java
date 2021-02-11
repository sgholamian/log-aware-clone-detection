//,temp,sample_2818.java,2,13,temp,sample_1240.java,2,12
//,3
public class xxx {
private void closeConnection() {
if (socket == null) {
return;
}
try {
socket.close();
} catch (IOException e) {


log.info("not able to close a socket");
}
}

};