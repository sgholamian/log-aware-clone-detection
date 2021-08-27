//,temp,sample_2412.java,2,12,temp,sample_2792.java,2,12
//,3
public class xxx {
public void start(long period, TimeUnit unit) {
if (!metricsDir.toFile().exists()) {
try {
Files.createDirectories(metricsDir, DIR_ATTRS);
} catch (IOException e) {


log.info("failed to initialize json reporter failed to create directory");
}
}
}

};