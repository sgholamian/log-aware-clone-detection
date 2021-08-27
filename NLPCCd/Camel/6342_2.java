//,temp,sample_1798.java,2,19,temp,sample_1797.java,2,17
//,3
public class xxx {
private boolean extenderCapabilityWired(Bundle bundle) {
BundleWiring wiring = bundle.adapt(BundleWiring.class);
if (wiring == null) {
return true;
}
List<BundleWire> requiredWires = wiring.getRequiredWires(EXTENDER_NAMESPACE);
for (BundleWire requiredWire : requiredWires) {
if (CAMEL_EXTENDER.equals(requiredWire.getCapability().getAttributes().get(EXTENDER_NAMESPACE))) {
if (this.bundleId == requiredWire.getProviderWiring().getBundle().getBundleId()) {


log.info("camel extender requirement of bundle correctly wired to this implementation");
}
}
}
}

};