//,temp,sample_2885.java,2,16,temp,sample_2884.java,2,16
//,2
public class xxx {
public boolean verifyServicesCombination(Set<Service> services) {
Set<Service> netscalerServices = new HashSet<Service>();
netscalerServices.add(Service.Lb);
netscalerServices.add(Service.StaticNat);
if (services != null && !services.isEmpty() && !netscalerServices.containsAll(services)) {
StringBuffer buff = new StringBuffer();
for (Service service : services) {
buff.append(service.getName());
buff.append(" ");
}


log.info("netscaler network element can only support lb and static nat services and service combination is not supported");
}
}

};