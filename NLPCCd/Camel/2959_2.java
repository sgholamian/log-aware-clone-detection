//,temp,sample_6023.java,2,9,temp,sample_3174.java,2,12
//,3
public class xxx {
public FileConsumer createConsumer(Processor processor) throws Exception {
ObjectHelper.notNull(operations, "operations");
ObjectHelper.notNull(file, "file");
if (!file.exists() && !file.isDirectory()) {
if (isAutoCreate()) {


log.info("creating non existing starting directory");
}
}
}

};