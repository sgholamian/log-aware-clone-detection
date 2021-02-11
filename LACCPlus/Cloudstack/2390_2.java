//,temp,VirtualNetworkModel.java,130,146,temp,FloatingIpModel.java,88,100
//,3
public class xxx {
    @Override
    public void delete(ModelController controller) throws IOException {
        ApiConnector api = controller.getApiAccessor();
        for (ModelObject successor : successors()) {
            successor.delete(controller);
        }

        try {
            api.delete(FloatingIp.class, _uuid);
        } catch (IOException ex) {
            s_logger.warn("floating ip delete", ex);
        }
    }

};