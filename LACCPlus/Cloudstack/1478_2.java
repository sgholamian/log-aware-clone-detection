//,temp,DirectAgentAttache.java,127,140,temp,ConnectedAgentAttache.java,97,110
//,2
public class xxx {
    @Override
    protected void finalize() throws Throwable {
        try {
            assert _link == null : "Duh...Says you....Forgot to call disconnect()!";
            synchronized (this) {
                if (_link != null) {
                    s_logger.warn("Lost attache " + _id + "(" + _name + ")");
                    disconnect(Status.Alert);
                }
            }
        } finally {
            super.finalize();
        }
    }

};