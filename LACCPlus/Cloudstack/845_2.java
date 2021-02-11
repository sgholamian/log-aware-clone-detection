//,temp,DataCenterIpAddressDaoImpl.java,218,231,temp,DataCenterIpAddressDaoImpl.java,205,216
//,3
public class xxx {
    @Override
    public void releasePodIpAddress(long id) {
        if (s_logger.isDebugEnabled()) {
            s_logger.debug("Releasing ip address for ID=" + id);
        }

        DataCenterIpAddressVO vo = this.findById(id);
        vo.setTakenAt(null);
        vo.setInstanceId(null);
        vo.setReservationId(null);
        persist(vo);
    }

};