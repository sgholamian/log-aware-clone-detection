//,temp,sample_3706.java,2,12,temp,sample_1282.java,2,10
//,3
public class xxx {
public WALCoprocessor checkAndGetInstance(Class<?> implClass) throws InstantiationException, IllegalAccessException {
if (WALCoprocessor.class.isAssignableFrom(implClass)) {
return (WALCoprocessor)implClass.newInstance();
} else {


log.info("is not of type walcoprocessor check the configuration");
}
}

};