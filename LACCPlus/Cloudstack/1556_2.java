//,temp,ClusteredAgentManagerImpl.java,789,797,temp,Agent.java,1018,1028
//,3
public class xxx {
        @Override
        public synchronized boolean cancel() {
            // TimerTask.cancel may fail depends on the calling context
            if (!cancelled) {
                cancelled = true;
                _startupWait = _startupWaitDefault;
                s_logger.debug("Startup task cancelled");
                return super.cancel();
            }
            return true;
        }

};