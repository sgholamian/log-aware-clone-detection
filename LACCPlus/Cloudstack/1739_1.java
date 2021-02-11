//,temp,FloatingIpPoolModel.java,78,92,temp,VirtualMachineModel.java,159,175
//,3
public class xxx {
    @Override
    public void delete(ModelController controller) throws IOException {
        ApiConnector api = controller.getApiAccessor();
        for (ModelObject successor : successors()) {
            successor.delete(controller);
        }
        try {
            if (_fipPool != null) {
                api.delete(_fipPool);
            }
            _fipPool = null;
        } catch (IOException ex) {
            s_logger.warn("floating ip pool delete", ex);
        }
    }

};