//,temp,sample_3265.java,2,20,temp,sample_3267.java,2,19
//,3
public class xxx {
public void dummy_method(){
Iterator<ProcessorDefinition<?>> it = AdviceWithTasks.createMatchByIterator(route, matchBy, selectFirst, selectLast, selectFrom, selectTo, maxDeep);
while (it.hasNext()) {
ProcessorDefinition<?> output = it.next();
if (matchBy.match(output)) {
List<ProcessorDefinition<?>> outputs = getOutputs(output);
if (outputs != null) {
int index = outputs.indexOf(output);
if (index != -1) {
match = true;
Object old = outputs.remove(index);


log.info("advicewith remove");
}
}
}
}
}

};