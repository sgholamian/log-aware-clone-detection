//,temp,TestEditLogRace.java,468,481,temp,TestEditLogRace.java,372,385
//,2
public class xxx {
        @Override
        public void run() {
          try {
            LOG.info("Starting mkdirs");
            namesystem.mkdirs("/test",
                new PermissionStatus("test","test", new FsPermission((short)00755)),
                true);
            LOG.info("mkdirs complete");
          } catch (Throwable ioe) {
            LOG.fatal("Got exception", ioe);
            deferredException.set(ioe);
            waitToEnterSync.countDown();
          }
        }

};