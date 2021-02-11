//,temp,sample_2085.java,2,17,temp,sample_1018.java,2,17
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