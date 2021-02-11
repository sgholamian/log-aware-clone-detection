//,temp,sample_8127.java,2,16,temp,sample_8128.java,2,16
//,2
public class xxx {
public void dummy_method(){
encodedVmId = URLEncoder.encode(s_windowsVmId.get(), "UTF-8");
encodedPublicIpId = URLEncoder.encode(s_publicIpId.get(), "UTF-8");
requestToSign = "apikey=" + encodedApiKey + "&command=enableStaticNat" + "&ipaddressid=" + encodedPublicIpId + "&virtualMachineId=" + encodedVmId;
requestToSign = requestToSign.toLowerCase();
signature = signRequest(requestToSign, s_secretKey.get());
encodedSignature = URLEncoder.encode(signature, "UTF-8");
url = developerServer + "?command=enableStaticNat&apikey=" + encodedApiKey + "&ipaddressid=" + encodedPublicIpId + "&signature=" + encodedSignature + "&virtualMachineId=" + encodedVmId;
client = new HttpClient();
method = new GetMethod(url);
responseCode = client.executeMethod(method);


log.info("url is");
}

};