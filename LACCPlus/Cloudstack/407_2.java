//,temp,XenServerStorageProcessor.java,1727,1753,temp,XenServerStorageProcessor.java,558,581
//,3
public class xxx {
    @Override
    public Answer deleteVolume(final DeleteCommand cmd) {
        final DataTO volume = cmd.getData();
        final Connection conn = hypervisorResource.getConnection();
        String errorMsg = null;
        try {
            final VDI vdi = VDI.getByUuid(conn, volume.getPath());
            for(VDI svdi : vdi.getSnapshots(conn)) {
                deleteVDI(conn, svdi);
            }
            deleteVDI(conn, vdi);
            return new Answer(null);
        } catch (final BadServerResponse e) {
            s_logger.debug("Failed to delete volume", e);
            errorMsg = e.toString();
        } catch (final XenAPIException e) {
            s_logger.debug("Failed to delete volume", e);
            errorMsg = e.toString();
        } catch (final XmlRpcException e) {
            s_logger.debug("Failed to delete volume", e);
            errorMsg = e.toString();
        }
        return new Answer(null, false, errorMsg);
    }

};