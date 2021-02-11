//,temp,CitrixResourceBase.java,3196,3210,temp,XenServerStorageProcessor.java,629,637
//,3
public class xxx {
    public VDI getVDIbyUuid(final Connection conn, final String uuid, final boolean throwExceptionIfNotFound) {
        try {
            return VDI.getByUuid(conn, uuid);
        } catch (final Exception e) {
            if (throwExceptionIfNotFound) {
                final String msg = "Catch Exception " + e.getClass().getName() + " :VDI getByUuid for uuid: " + uuid + " failed due to " + e.toString();

                s_logger.debug(msg);

                throw new CloudRuntimeException(msg, e);
            }

            return null;
        }
    }

};