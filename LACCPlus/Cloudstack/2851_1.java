//,temp,ClusteredAgentManagerImpl.java,397,400,temp,ClusteredAgentManagerImpl.java,392,395
//,2
public class xxx {
    protected static void logD(final byte[] bytes, final String msg) {
        s_logger.debug("Seq " + Request.getAgentId(bytes) + "-" + Request.getSequence(bytes) + ": MgmtId " + Request.getManagementServerId(bytes) + ": "
                + (Request.isRequest(bytes) ? "Req: " : "Resp: ") + msg);
    }

};