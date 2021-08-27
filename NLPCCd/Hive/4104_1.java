//,temp,sample_5579.java,2,17,temp,sample_5578.java,2,16
//,3
public class xxx {
public void dummy_method(){
if (response != null) {
hasAuthResponse = true;
ctx.channel().writeAndFlush(response).sync();
}
if (!isComplete()) {
return;
}
ctx.channel().pipeline().remove(this);
String qop = getNegotiatedProperty(Sasl.QOP);
if (Rpc.SASL_AUTH_CONF.equals(qop)) {


log.info("sasl confidentiality enabled");
}
}

};