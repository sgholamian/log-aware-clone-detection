//,temp,sample_8225.java,2,16,temp,sample_1799.java,2,16
//,2
public class xxx {
public void dummy_method(){
String encodedApiKey = URLEncoder.encode(s_apiKey.get(), "UTF-8");
String requestToSign = "apikey=" + encodedApiKey + "&command=listVirtualMachines";
requestToSign = requestToSign.toLowerCase();
String signature = signRequest(requestToSign, s_secretKey.get());
String encodedSignature = URLEncoder.encode(signature, "UTF-8");
url = developerServer + "?command=listVirtualMachines&apikey=" + encodedApiKey + "&signature=" + encodedSignature;
String[] vmIds = null;
client = new HttpClient();
method = new GetMethod(url);
responseCode = client.executeMethod(method);


log.info("list virtual machines response code");
}

};