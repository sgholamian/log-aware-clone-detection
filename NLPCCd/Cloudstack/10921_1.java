//,temp,sample_8623.java,2,16,temp,sample_2890.java,2,16
//,3
public class xxx {
private boolean canHandleLbRules(final List<LoadBalancingRule> rules) {
final Map<Capability, String> lbCaps = getCapabilities().get(Service.Lb);
if (!lbCaps.isEmpty()) {
final String schemeCaps = lbCaps.get(Capability.LbSchemes);
if (schemeCaps != null) {
for (final LoadBalancingRule rule : rules) {
if (!schemeCaps.contains(rule.getScheme().toString())) {


log.info("scheme is not supported by the provider");
}
}
}
}
}

};