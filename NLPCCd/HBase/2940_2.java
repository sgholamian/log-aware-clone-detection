//,temp,sample_4457.java,2,13,temp,sample_1737.java,2,13
//,3
public class xxx {
public void run() {
try {
int ret = ToolRunner.run(HBaseConfiguration.create(), new LoadTestTool(), workerArgs);
if (ret != 0) {
throw new RuntimeException("LoadTestTool exit with non-zero return code.");
}
} catch (Exception ex) {


log.info("error in worker thread");
}
}

};