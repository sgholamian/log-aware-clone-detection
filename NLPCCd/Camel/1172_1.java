//,temp,sample_5126.java,2,17,temp,sample_7149.java,2,17
//,3
public class xxx {
public void dummy_method(){
List<String> result = Arrays.asList(tokens);
result = unquoteTokens(result, separator, quote);
if (result.size() == 0 || result.isEmpty()) {
throw new java.lang.IllegalArgumentException("No records have been defined in the CSV");
} else {
if (LOG.isDebugEnabled()) {
}
factory.bind(getCamelContext(), result, model, count);
factory.link(model);
models.add(model);


log.info("graph of objects created");
}
}

};