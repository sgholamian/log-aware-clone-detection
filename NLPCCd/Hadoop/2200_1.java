//,temp,sample_2085.java,2,17,temp,sample_3105.java,2,18
//,3
public class xxx {
public void dummy_method(){
if (LOG.isDebugEnabled()) {
LOG.debug("Portmap GETPORT key=" + key + " " + mapping);
}
PortmapMapping value = map.get(key);
int res = 0;
if (value != null) {
res = value.getPort();
if (LOG.isDebugEnabled()) {
}
} else {


log.info("warning no mapping for key");
}
}

};