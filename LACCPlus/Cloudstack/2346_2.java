//,temp,PrivateIpDaoImpl.java,91,104,temp,DataCenterIpAddressDaoImpl.java,218,231
//,3
public class xxx {
    @Override
    public void releaseIpAddress(long nicId) {
        if (s_logger.isDebugEnabled()) {
            s_logger.debug("Releasing ip address for instance=" + nicId);
        }
        SearchCriteria<DataCenterIpAddressVO> sc = AllFieldsSearch.create();
        sc.setParameters("instance", nicId);

        DataCenterIpAddressVO vo = createForUpdate();
        vo.setTakenAt(null);
        vo.setInstanceId(null);
        vo.setReservationId(null);
        update(vo, sc);
    }

};