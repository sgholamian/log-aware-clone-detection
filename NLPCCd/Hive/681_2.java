//,temp,sample_5396.java,2,14,temp,sample_3784.java,2,12
//,3
public class xxx {
protected void finalize() throws Throwable {
if (conn != null) {
try {
conn.close();
} catch (Exception e) {


log.info("couldn t close jms connection ignored the error");
}
}
}

};