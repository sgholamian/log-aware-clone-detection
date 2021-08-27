//,temp,SftpChangedReadLockIT.java,61,74,temp,FtpChangedReadLockIT.java,60,74
//,2
public class xxx {
    private void writeSlowFile() throws Exception {
        LOG.debug("Writing slow file...");

        createDirectory(ftpFile("changed"));
        FileOutputStream fos = new FileOutputStream(ftpFile("changed/slowfile.dat").toFile(), true);
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