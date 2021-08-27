//,temp,ConsulClusterView.java,263,273,temp,ConsulClusterView.java,111,123
//,3
public class xxx {
        @Override
        public void onFailure(Throwable throwable) {
            LOGGER.debug("", throwable);

            if (sessionId.get() != null) {
                keyValueClient.releaseLock(configuration.getRootPath(), sessionId.get());
            }

            localMember.setMaster(false);
            watch();
        }

};