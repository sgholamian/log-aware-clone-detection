//,temp,sample_5126.java,2,17,temp,sample_7149.java,2,17
//,3
public class xxx {
public void dummy_method(){
count++;
model = factory.factory();
List<String> result = Arrays.asList(line.split(separator));
if (result.size() == 0 || result.isEmpty()) {
throw new java.lang.IllegalArgumentException("No records have been defined in the KVP");
}
if (result.size() > 0) {
factory.bind(getCamelContext(), result, model, count, lists);
factory.link(model);
models.add(model);


log.info("graph of objects created");
}
}

};