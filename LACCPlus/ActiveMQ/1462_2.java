//,temp,LockableServiceSupport.java,157,168,temp,DefaultIOExceptionHandler.java,181,192
//,3
public class xxx {
            @Override
            public void run() {
                try {
                    if( broker.isRestartAllowed() ) {
                        broker.requestRestart();
                    }
                    broker.setSystemExitOnShutdown(isSystemExitOnShutdown());
                    broker.stop();
                } catch (Exception e) {
                    LOG.warn("Failure occurred while stopping broker", e);
                }
            }

};