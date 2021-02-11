//,temp,ClusteredAgentManagerImpl.java,402,405,temp,ClusteredAgentManagerImpl.java,397,400
//,2
public class xxx {
    protected static void logI(final byte[] bytes, final String msg) {
        s_logger.info("Seq " + Request.getAgentId(bytes) + "-" + Request.getSequence(bytes) + ": MgmtId " + Request.getManagementServerId(bytes) + ": "
                + (Request.isRequest(bytes) ? "Req: " : "Resp: ") + msg);
    }

};