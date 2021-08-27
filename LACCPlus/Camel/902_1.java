//,temp,FtpChangedRootDirReadLockIT.java,61,75,temp,FileChangedReadLockTest.java,63,74
//,3
public class xxx {
    private void writeSlowFile() throws Exception {
        LOG.debug("Writing slow file...");

        createDirectory(service.getFtpRootDir() + "/");
        FileOutputStream fos = new FileOutputStream(ftpFile("slowfile.dat").toFile(), true);
        for (int i = 0; i < 20; i++) {
            fos.write(("Line " + i + LS).getBytes());
            LOG.debug("Writing line " + i);
            Thread.sleep(200);
        }

        fos.flush();
        fos.close();
        LOG.debug("Writing slow file DONE...");
    }

};