//,temp,sample_3265.java,2,20,temp,sample_3267.java,2,19
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
Object existing = outputs.get(index);
outputs.add(index + 1, after);
after.setParent(output.getParent());


log.info("advicewith after");
}
}
}
}

};