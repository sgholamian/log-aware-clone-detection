//,temp,SftpEmbeddedService.java,101,118,temp,FtpEmbeddedService.java,124,145
//,3
public class xxx {
    public void tearDownServer() {
        try {
            // stop asap as we may hang forever
            if (sshd != null) {
                sshd.stop(true);
            }
        } catch (Exception e) {
            // ignore while shutting down as we could be polling during
            // shutdown
            // and get errors when the ftp server is stopping. This is only
            // an issue
            // since we host the ftp server embedded in the same jvm for
            // unit testing
            LOG.trace("Exception while shutting down: {}", e.getMessage(), e);
        } finally {
            sshd = null;
        }
    }

};