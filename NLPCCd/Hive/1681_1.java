//,temp,sample_4487.java,2,17,temp,sample_4486.java,2,16
//,3
public class xxx {
public void dummy_method(){
int ret = 0;
try {
ret = new LlapServiceDriver().run(args);
} catch (Throwable t) {
System.err.println("Failed: " + t.getMessage());
t.printStackTrace();
ret = 3;
} finally {
}
if (LOG.isDebugEnabled()) {


log.info("completed processing exiting with");
}
}

};