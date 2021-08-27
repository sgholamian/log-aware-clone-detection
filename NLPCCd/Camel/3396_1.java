//,temp,sample_587.java,2,17,temp,sample_586.java,2,17
//,3
public class xxx {
public void dummy_method(){
if (!hasOptions) {
model.getComponentOptions().clear();
}
String options = templateComponentOptions(model);
updated |= updateComponentOptions(file, options);
options = templateEndpointOptions(model);
updated |= updateEndpointOptions(file, options);
if (updated) {
} else if (exists) {
} else {


log.info("no component doc file");
}
}

};