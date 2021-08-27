//,temp,sample_6248.java,2,15,temp,sample_2998.java,2,19
//,3
public class xxx {
public void dummy_method(){
StringBuilder buffer = new StringBuilder();
Map<Integer, List<String>> results = new HashMap<Integer, List<String>>();
ObjectHelper.notNull(this.separator, "The separator has not been instantiated or property not defined in the @CsvRecord annotation");
char separator = ConverterUtils.getCharDelimiter(this.getSeparator());
if (LOG.isDebugEnabled()) {
}
for (Class<?> clazz : models) {
if (model.containsKey(clazz.getName())) {
Object obj = model.get(clazz.getName());
if (LOG.isDebugEnabled()) {


log.info("model object class");
}
}
}
}

};