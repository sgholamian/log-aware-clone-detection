//,temp,sample_6470.java,2,14,temp,sample_6471.java,2,15
//,3
public class xxx {
public GetSpaceUsed build() throws IOException {
GetSpaceUsed getSpaceUsed = null;
try {
Constructor<? extends GetSpaceUsed> cons = getKlass().getConstructor(Builder.class);
getSpaceUsed = cons.newInstance(this);
} catch (InstantiationException e) {
} catch (IllegalAccessException e) {
} catch (InvocationTargetException e) {


log.info("error trying to create");
}
}

};