//,temp,sample_5047.java,2,14,temp,sample_5049.java,2,17
//,3
public class xxx {
private int stopMaster() {
Configuration conf = getConf();
conf.setInt(HConstants.HBASE_CLIENT_RETRIES_NUMBER, 0);
try (Connection connection = ConnectionFactory.createConnection(conf)) {
try (Admin admin = connection.getAdmin()) {
admin.shutdown();
} catch (Throwable t) {


log.info("failed to stop master");
}
}
}

};