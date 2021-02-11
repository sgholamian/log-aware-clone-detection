//,temp,ClusteredAgentManagerImpl.java,402,405,temp,ClusteredAgentManagerImpl.java,392,395
//,2
public class xxx {
    protected static void logT(final byte[] bytes, final String msg) {
        s_logger.trace("Seq " + Request.getAgentId(bytes) + "-" + Request.getSequence(bytes) + ": MgmtId " + Request.getManagementServerId(bytes) + ": "
                + (Request.isRequest(bytes) ? "Req: " : "Resp: ") + msg);
    }

};