//,temp,DataCenterIpAddressDaoImpl.java,218,231,temp,DataCenterIpAddressDaoImpl.java,171,187
//,3
public class xxx {
    @Override
    public void releaseIpAddress(String ipAddress, long dcId, Long instanceId) {
        if (s_logger.isDebugEnabled()) {
            s_logger.debug("Releasing ip address: " + ipAddress + " data center " + dcId);
        }
        SearchCriteria<DataCenterIpAddressVO> sc = AllFieldsSearch.create();
        sc.setParameters("ip", ipAddress);
        sc.setParameters("dc", dcId);
        sc.setParameters("instance", instanceId);

        DataCenterIpAddressVO vo = createForUpdate();

        vo.setTakenAt(null);
        vo.setInstanceId(null);
        vo.setReservationId(null);
        update(vo, sc);
    }

};