//,temp,sample_1798.java,2,19,temp,sample_1797.java,2,17
//,3
public class xxx {
public void dummy_method(){
BundleWiring wiring = bundle.adapt(BundleWiring.class);
if (wiring == null) {
return true;
}
List<BundleWire> requiredWires = wiring.getRequiredWires(EXTENDER_NAMESPACE);
for (BundleWire requiredWire : requiredWires) {
if (CAMEL_EXTENDER.equals(requiredWire.getCapability().getAttributes().get(EXTENDER_NAMESPACE))) {
if (this.bundleId == requiredWire.getProviderWiring().getBundle().getBundleId()) {
return true;
} else {


log.info("not processing bundle as it requires a camel extender but is not wired to the this implementation");
}
}
}
}

};