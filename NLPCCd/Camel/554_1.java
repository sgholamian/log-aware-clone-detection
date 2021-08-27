//,temp,sample_4921.java,2,10,temp,sample_11.java,2,9
//,3
public class xxx {
public static ScriptEngine resolveScriptEngine(String scriptEngineName) throws InvalidSyntaxException {
ServiceReference<?>[] refs = context.getServiceReferences(ScriptEngineResolver.class.getName(), null);
if (refs == null) {
return null;
}


log.info("found osgi scriptengineresolver services");
}

};