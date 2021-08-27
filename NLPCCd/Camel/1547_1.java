//,temp,sample_3264.java,2,19,temp,sample_3266.java,2,19
//,3
public class xxx {
public void dummy_method(){
ProcessorDefinition<?> output = it.next();
if (matchBy.match(output)) {
List<ProcessorDefinition<?>> outputs = getOutputs(output);
if (outputs != null) {
int index = outputs.indexOf(output);
if (index != -1) {
match = true;
outputs.add(index + 1, replace);
Object old = outputs.remove(index);
replace.setParent(output.getParent());


log.info("advicewith replace");
}
}
}
}

};