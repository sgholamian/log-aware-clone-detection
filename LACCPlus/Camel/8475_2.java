//,temp,MarkerFileExclusiveReadLockStrategyTest.java,79,92,temp,FileChangedReadLockMinAgeTest.java,57,69
//,3
public class xxx {
    private void writeSlowFile() throws Exception {
        LOG.debug("Writing slow file...");

        try (OutputStream fos = Files.newOutputStream(testFile("in/slowfile.dat"))) {
            for (int i = 0; i < 20; i++) {
                fos.write(("Line " + i + LS).getBytes());
                LOG.debug("Writing line " + i);
                Thread.sleep(50);
            }
            fos.flush();
        }
        LOG.debug("Writing slow file DONE...");
    }

};