//,temp,MarkerFileExclusiveReadLockStrategyTest.java,79,92,temp,FileChangedReadLockMinAgeTest.java,57,69
//,3
public class xxx {
    private void writeFiles() throws Exception {
        LOG.debug("Writing files...");

        try (OutputStream fos = Files.newOutputStream(testFile("in/file1.dat"));
             OutputStream fos2 = Files.newOutputStream(testFile("in/file2.dat"))) {
            for (int i = 0; i < 20; i++) {
                fos.write(("Line " + i + LS).getBytes());
                fos2.write(("Line " + i + LS).getBytes());
                LOG.debug("Writing line " + i);
            }
            fos.flush();
            fos2.flush();
        }
    }

};