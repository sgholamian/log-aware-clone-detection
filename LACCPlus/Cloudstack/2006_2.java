//,temp,CitrixResourceBase.java,3239,3262,temp,CitrixResourceBase.java,3132,3157
//,3
public class xxx {
    public SR getStorageRepository(final Connection conn, final String srNameLabel) {
        Set<SR> srs;
        try {
            srs = SR.getByNameLabel(conn, srNameLabel);
        } catch (final XenAPIException e) {
            throw new CloudRuntimeException("Unable to get SR " + srNameLabel + " due to " + e.toString(), e);
        } catch (final Exception e) {
            throw new CloudRuntimeException("Unable to get SR " + srNameLabel + " due to " + e.getMessage(), e);
        }

        if (srs.size() > 1) {
            throw new CloudRuntimeException("More than one storage repository was found for pool with uuid: " + srNameLabel);
        } else if (srs.size() == 1) {
            final SR sr = srs.iterator().next();
            if (s_logger.isDebugEnabled()) {
                s_logger.debug("SR retrieved for " + srNameLabel);
            }

            if (checkSR(conn, sr)) {
                return sr;
            }
            throw new CloudRuntimeException("SR check failed for storage pool: " + srNameLabel + "on host:" + _host.getUuid());
        } else {
            throw new CloudRuntimeException("Can not see storage pool: " + srNameLabel + " from on host:" + _host.getUuid());
        }
    }

};