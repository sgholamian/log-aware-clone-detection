//,temp,sample_8292.java,2,13,temp,sample_8291.java,2,12
//,3
public class xxx {
public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
this.applicationContext = applicationContext;
ClassLoader cl;
if (applicationContext != null && applicationContext.getClassLoader() != null) {
cl = applicationContext.getClassLoader();
} else {


log.info("cannot find the class loader from application context using the thread context class loader instead");
}
}

};