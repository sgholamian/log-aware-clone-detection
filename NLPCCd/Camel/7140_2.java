//,temp,sample_2142.java,2,13,temp,sample_2141.java,2,12
//,3
public class xxx {
public boolean isActive() {
if (resolvesTo.isEmpty()) {
try {
resolvesTo.addAll(getLocalIps());
} catch (Exception e) {


log.info("failed to get local ips and resolvesto not specified identifying as inactive");
}
}
}

};