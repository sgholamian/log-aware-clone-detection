//,temp,sample_8269.java,2,17,temp,sample_1812.java,2,17
//,2
public class xxx {
public void dummy_method(){
boolean success = false;
boolean isAuthenticated = conn.authenticateWithPassword("Administrator", "password");
if (isAuthenticated == false) {
return "Authentication failed";
} else {
}
try {
SCPClient scp = new SCPClient(conn);
scp.put("wget.exe", "wget.exe", "C:\\Users\\Administrator", "0777");
} catch (Exception ex) {


log.info("unable to put wget exe");
}
}

};