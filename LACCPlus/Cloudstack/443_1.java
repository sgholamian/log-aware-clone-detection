//,temp,FloatingIpPoolModel.java,120,159,temp,NetworkPolicyModel.java,234,282
//,3
public class xxx {
    @Override
    public void update(ModelController controller) throws InternalErrorException, IOException {

        assert _vnModel != null : "vn model is not set";

        ApiConnector api = controller.getApiAccessor();
        ContrailManager manager = controller.getManager();
        FloatingIpPool fipPool = _fipPool;

        if (fipPool == null) {
            String fipPoolName = manager.getDefaultPublicNetworkFQN() + ":PublicIpPool";
            _fipPool = fipPool = (FloatingIpPool)controller.getApiAccessor().findByFQN(FloatingIpPool.class, fipPoolName);
            if (fipPool == null) {
                fipPool = new FloatingIpPool();
                fipPool.setName(_name);
                fipPool.setParent(_vnModel.getVirtualNetwork());
            }
        }

        if (_fipPool == null) {
            try {
                api.create(fipPool);
            } catch (Exception ex) {
                s_logger.debug("floating ip pool create", ex);
                throw new CloudRuntimeException("Failed to create floating ip pool", ex);
            }
            _fipPool = fipPool;
        } else {
            try {
                api.update(fipPool);
            } catch (IOException ex) {
                s_logger.warn("floating ip pool update", ex);
                throw new CloudRuntimeException("Unable to update floating ip ppol object", ex);
            }
        }

        for (ModelObject successor : successors()) {
            successor.update(controller);
        }
    }

};