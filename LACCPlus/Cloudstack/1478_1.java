//,temp,DirectAgentAttache.java,127,140,temp,ConnectedAgentAttache.java,97,110
//,2
public class xxx {
    @Override
    protected void finalize() throws Throwable {
        try {
            assert _resource == null : "Come on now....If you're going to dabble in agent code, you better know how to close out our resources. Ever considered why there's a method called disconnect()?";
            synchronized (this) {
                if (_resource != null) {
                    s_logger.warn("Lost attache for " + _id + "(" + _name + ")");
                    disconnect(Status.Alert);
                }
            }
        } finally {
            super.finalize();
        }
    }

};