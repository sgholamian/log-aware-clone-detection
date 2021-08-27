//,temp,sample_7494.java,2,9,temp,sample_4920.java,2,9
//,3
public class xxx {
public static ScriptEngine resolveScriptEngine(String scriptEngineName) throws InvalidSyntaxException {
ServiceReference<?>[] refs = context.getServiceReferences(ScriptEngineResolver.class.getName(), null);
if (refs == null) {


log.info("no osgi script engine resolvers available");
}
}

};