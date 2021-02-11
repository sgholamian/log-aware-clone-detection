//,temp,sample_4708.java,2,13,temp,sample_4897.java,2,12
//,3
public class xxx {
public void stop() throws Exception {
MultiException exception = null;
for (Connector c : listeners) {
try {
c.close();
} catch (Exception e) {


log.info("error while stopping listener for webapp");
}
}
}

};