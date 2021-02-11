//,temp,sample_641.java,2,12,temp,sample_1893.java,2,12
//,2
public class xxx {
public void close() {
if (connection != null) {
try {
connection.close();
} catch (Exception ex) {


log.info("could not close the connection");
}
}
}

};