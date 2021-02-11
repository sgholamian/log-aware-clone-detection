//,temp,sample_3533.java,2,14,temp,sample_8543.java,2,14
//,2
public class xxx {
public void delete(ModelController controller) throws IOException {
ApiConnector api = controller.getApiAccessor();
for (ModelObject successor : successors()) {
successor.delete(controller);
}
try {
api.delete(FloatingIp.class, _uuid);
} catch (IOException ex) {


log.info("floating ip delete");
}
}

};