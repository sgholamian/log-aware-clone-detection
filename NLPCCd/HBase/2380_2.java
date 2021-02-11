//,temp,sample_5579.java,2,18,temp,sample_2881.java,2,12
//,3
public class xxx {
public void stop() {
if (connection != null) {
try {
connection.close();
} catch (IOException e) {


log.info("attempt to close connection to master failed");
}
}
}

};