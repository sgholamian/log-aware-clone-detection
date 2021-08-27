//,temp,sample_8154.java,2,11,temp,sample_4688.java,2,11
//,3
public class xxx {
public void drop() throws Exception {
try {
execute("drop table orders");
} catch (Throwable e) {
}
connection.close();


log.info("database tables dropped");
}

};