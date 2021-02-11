//,temp,sample_4912.java,2,17,temp,sample_4913.java,2,17
//,2
public class xxx {
public void dummy_method(){
Map<String, Object> params = new HashMap<String, Object>();
params.putAll(details);
params.put("zone", Long.toString(dcId));
if (podId != null) {
params.put("pod", podId.toString());
}
params.put("guid", uri.toString());
try {
storage.configure("Storage", params);
} catch (ConfigurationException e) {


log.info("unable to configure the storage");
}
}

};