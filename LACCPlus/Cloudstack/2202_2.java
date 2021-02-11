//,temp,VirtualRouterElement.java,799,811,temp,VirtualRouterElement.java,785,797
//,3
public class xxx {
    @Override
    public VirtualRouterProvider configure(final ConfigureVirtualRouterElementCmd cmd) {
        final VirtualRouterProviderVO element = _vrProviderDao.findById(cmd.getId());
        if (element == null || !(element.getType() == Type.VirtualRouter || element.getType() == Type.VPCVirtualRouter)) {
            s_logger.debug("Can't find Virtual Router element with network service provider id " + cmd.getId());
            return null;
        }

        element.setEnabled(cmd.getEnabled());
        _vrProviderDao.persist(element);

        return element;
    }

};