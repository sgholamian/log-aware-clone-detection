//,temp,VirtualNetworkModel.java,130,146,temp,VirtualMachineModel.java,159,175
//,3
public class xxx {
    @Override
    public void delete(ModelController controller) throws IOException {
        ApiConnector api = controller.getApiAccessor();
        for (ModelObject successor : successors()) {
            successor.delete(controller);
        }

        if (_policyModel != null) {
            _policyModel.removeSuccessor(this);
        }

        try {
            api.delete(VirtualNetwork.class, _uuid);
        } catch (IOException ex) {
            s_logger.warn("virtual-network delete", ex);
        }
    }

};