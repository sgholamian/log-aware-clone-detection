//,temp,AtomixClusterService.java,142,150,temp,AtomixClusterClientService.java,111,119
//,2
public class xxx {
    @Override
    protected void doStop() throws Exception {
        super.doStop();

        if (atomix != null) {
            LOGGER.debug("Shutdown atomix client {}", atomix);
            atomix.close().join();
        }
    }

};