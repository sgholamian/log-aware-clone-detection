//,temp,sample_5047.java,2,14,temp,sample_5049.java,2,17
//,3
public class xxx {
public void dummy_method(){
conf.setInt(HConstants.HBASE_CLIENT_RETRIES_NUMBER, 0);
try (Connection connection = ConnectionFactory.createConnection(conf)) {
try (Admin admin = connection.getAdmin()) {
admin.shutdown();
} catch (Throwable t) {
return 1;
}
} catch (MasterNotRunningException e) {
return 1;
} catch (ZooKeeperConnectionException e) {


log.info("zookeeper not available");
}
}

};