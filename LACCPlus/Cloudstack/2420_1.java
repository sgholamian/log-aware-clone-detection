//,temp,DataCenterLinkLocalIpAddressDaoImpl.java,106,122,temp,PrivateIpDaoImpl.java,91,104
//,3
public class xxx {
    @Override
    public void releaseIpAddress(String ipAddress, long dcId, long instanceId) {
        if (s_logger.isDebugEnabled()) {
            s_logger.debug("Releasing ip address: " + ipAddress + " data center " + dcId);
        }
        SearchCriteria<DataCenterLinkLocalIpAddressVO> sc = AllFieldsSearch.create();
        sc.setParameters("ip", ipAddress);
        sc.setParameters("dc", dcId);
        sc.setParameters("instance", instanceId);

        DataCenterLinkLocalIpAddressVO vo = createForUpdate();

        vo.setTakenAt(null);
        vo.setInstanceId(null);
        vo.setReservationId(null);
        update(vo, sc);
    }

};