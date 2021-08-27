//,temp,sample_7583.java,2,14,temp,sample_2609.java,2,16
//,3
public class xxx {
protected MqttConnectOptions resolveMqttConnectOptions() {
if (connectOptions != null) {
return connectOptions;
}
Set<MqttConnectOptions> connectOptions = getCamelContext().getRegistry().findByType(MqttConnectOptions.class);
if (connectOptions.size() == 1) {
return connectOptions.iterator().next();
} else if (connectOptions.size() > 1) {


log.info("found instances of the mqttconnectoptions in the registry none of these will be used by the endpoint please use connectoptions endpoint option to select one");
}
}

};