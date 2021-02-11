//,temp,sample_4613.java,2,11,temp,sample_4457.java,2,13
//,3
public class xxx {
protected void doStaticMain(String args[]) {
int ret;
try {
ret = ToolRunner.run(HBaseConfiguration.create(), this, args);
} catch (Exception ex) {


log.info("error running command line tool");
}
}

};