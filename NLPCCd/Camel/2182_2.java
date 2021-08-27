//,temp,sample_6829.java,2,18,temp,sample_6830.java,2,18
//,2
public class xxx {
public void dummy_method(){
ScriptEngineManager manager = new ScriptEngineManager(classLoader);
ScriptEngine engine = null;
String[] names = getScriptNames(language);
for (String name : names) {
try {
engine = manager.getEngineByName(name);
if (engine != null) {
break;
}
} catch (NoClassDefFoundError ex) {


log.info("cannot load scriptengine for please ensure correct jars is provided on classpath");
}
}
}

};