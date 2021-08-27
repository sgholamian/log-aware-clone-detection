//,temp,sample_4487.java,2,17,temp,sample_4486.java,2,16
//,3
public class xxx {
public static void main(String[] args) throws Exception {
LOG.info("LLAP service driver invoked with arguments={}", args);
int ret = 0;
try {
ret = new LlapServiceDriver().run(args);
} catch (Throwable t) {
System.err.println("Failed: " + t.getMessage());
t.printStackTrace();
ret = 3;
} finally {


log.info("llap service driver finished");
}
}

};