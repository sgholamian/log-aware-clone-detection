//,temp,sample_2163.java,2,15,temp,sample_2162.java,2,13
//,3
public class xxx {
public StructObjectInspector initialize(ObjectInspector[] arguments) throws UDFArgumentException {
if (SessionState.get() == null || SessionState.get().getConf() == null) {
throw new IllegalStateException("Cannot run get splits outside HS2");
}
if (arguments.length != 2) {
throw new UDFArgumentLengthException( "The function GET_SPLITS accepts 2 arguments.");
} else if (!(arguments[0] instanceof StringObjectInspector)) {
throw new UDFArgumentTypeException(0, "\"" + "string\" is expected at function GET_SPLITS, " + "but \"" + arguments[0].getTypeName() + "\" is found");
} else if (!(arguments[1] instanceof IntObjectInspector)) {


log.info("got instead of int");
}
}

};