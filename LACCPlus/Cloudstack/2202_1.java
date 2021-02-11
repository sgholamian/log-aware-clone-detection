//,temp,VirtualRouterElement.java,799,811,temp,VirtualRouterElement.java,785,797
//,3
public class xxx {
    @Override
    public OvsProvider configure(final ConfigureOvsElementCmd cmd) {
        final OvsProviderVO element = _ovsProviderDao.findById(cmd.getId());
        if (element == null) {
            s_logger.debug("Can't find Ovs element with network service provider id " + cmd.getId());
            return null;
        }

        element.setEnabled(cmd.getEnabled());
        _ovsProviderDao.persist(element);

        return element;
    }

};