//,temp,sample_4457.java,2,13,temp,sample_1737.java,2,13
//,3
public class xxx {
public void doMain(String args[]) {
try {
int ret = ToolRunner.run(HBaseConfiguration.create(), this, args);
if (ret != 0) {
System.exit(ret);
}
} catch (Exception e) {


log.info("failed to run");
}
}

};