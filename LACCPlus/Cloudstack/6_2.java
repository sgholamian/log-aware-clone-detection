//,temp,FloatingIpModel.java,88,100,temp,VirtualMachineModel.java,159,175
//,3
public class xxx {
    @Override
    public void delete(ModelController controller) throws IOException {
        ApiConnector api = controller.getApiAccessor();
        for (ModelObject successor : successors()) {
            successor.delete(controller);
        }

        try {
            api.delete(VirtualMachine.class, _uuid);
        } catch (IOException ex) {
            s_logger.warn("virtual-machine delete", ex);
        }

        if (_serviceModel != null) {
            _serviceModel.delete(controller);
        }
    }

};