//,temp,sample_2962.java,2,10,temp,sample_2570.java,2,10
//,2
public class xxx {
public boolean reconnect(final long hostId) {
HostVO host;
host = _hostDao.findById(hostId);
if (host == null || host.getRemoved() != null) {


log.info("unable to find host");
}
}

};