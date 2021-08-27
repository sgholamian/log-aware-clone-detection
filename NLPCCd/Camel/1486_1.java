//,temp,sample_597.java,2,17,temp,sample_598.java,2,17
//,3
public class xxx {
public void dummy_method(){
boolean deprecated = model.isDeprecated();
if (deprecated) {
docTitle += " (deprecated)";
}
boolean exists = file.exists();
boolean updated;
updated = updateTitles(file, docTitle);
String options = templateEipOptions(model);
updated |= updateEipOptions(file, options);
if (updated) {


log.info("updated doc file");
}
}

};