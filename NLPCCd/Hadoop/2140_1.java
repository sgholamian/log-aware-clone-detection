//,temp,sample_4942.java,2,21,temp,sample_3996.java,2,19
//,3
public class xxx {
public void dummy_method(){
synchronized (creds) {
for (Iterator<Object> iter = creds.iterator(); iter.hasNext();) {
Object cred = iter.next();
if (cred instanceof KerberosTicket) {
KerberosTicket ticket = (KerberosTicket) cred;
if (!ticket.getServer().getName().startsWith("krbtgt")) {
iter.remove();
try {
ticket.destroy();
} catch (DestroyFailedException e) {


log.info("destroy ticket failed");
}
}
}
}
}
}

};