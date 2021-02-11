//,temp,sample_9191.java,2,8,temp,sample_9187.java,2,8
//,3
public class xxx {
public void testJobFail() throws IOException, InterruptedException, ClassNotFoundException {
if (!(new File(MiniMRYarnCluster.APPJAR)).exists()) {


log.info("mrappjar not found not running test");
}
}

};