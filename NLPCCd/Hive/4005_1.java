//,temp,sample_4669.java,2,11,temp,sample_4681.java,2,11
//,3
public class xxx {
public static void addEstimator(String className, HashMap<Class<?>, ObjectEstimator> sizeEstimators) {
Class<?> clazz = null;
try {
clazz = Class.forName(className);
} catch (ClassNotFoundException e) {


log.info("cannot find");
}
}

};