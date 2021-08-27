//,temp,sample_3295.java,2,17,temp,sample_3296.java,2,17
//,3
public class xxx {
public void dummy_method(){
regular.append("* Components\n");
for (ComponentModel model : models) {
if (!model.getLabel().contains("core")) {
String line = "\t* " + link(model) + "\n";
regular.append(line);
}
}
updated |= updateComponents(file, regular.toString());
if (updated) {
} else {


log.info("no changes to user guide file");
}
}

};