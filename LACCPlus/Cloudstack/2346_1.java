//,temp,PrivateIpDaoImpl.java,91,104,temp,DataCenterIpAddressDaoImpl.java,218,231
//,3
public class xxx {
    @Override
    public void releaseIpAddress(String ipAddress, long networkId) {
        if (s_logger.isDebugEnabled()) {
            s_logger.debug("Releasing private ip address: " + ipAddress + " network id " + networkId);
        }
        SearchCriteria<PrivateIpVO> sc = AllFieldsSearch.create();
        sc.setParameters("ip", ipAddress);
        sc.setParameters("networkId", networkId);

        PrivateIpVO vo = createForUpdate();

        vo.setTakenAt(null);
        update(vo, sc);
    }

};