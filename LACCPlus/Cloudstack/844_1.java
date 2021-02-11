//,temp,DataCenterIpAddressDaoImpl.java,189,203,temp,DataCenterIpAddressDaoImpl.java,171,187
//,3
public class xxx {
    @Override
    public void releaseIpAddress(long nicId, String reservationId) {
        if (s_logger.isDebugEnabled()) {
            s_logger.debug("Releasing ip address for reservationId=" + reservationId + ", instance=" + nicId);
        }
        SearchCriteria<DataCenterIpAddressVO> sc = AllFieldsSearch.create();
        sc.setParameters("instance", nicId);
        sc.setParameters("reservation", reservationId);

        DataCenterIpAddressVO vo = createForUpdate();
        vo.setTakenAt(null);
        vo.setInstanceId(null);
        vo.setReservationId(null);
        update(vo, sc);
    }

};