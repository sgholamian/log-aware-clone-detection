//,temp,sample_526.java,2,18,temp,sample_527.java,2,18
//,3
public class xxx {
public void dummy_method(){
clientActivityListener.onLogin(host);
boolean login;
if (username != null) {
if (account != null) {
login = client.login(username, configuration.getPassword(), account);
} else {
login = client.login(username, configuration.getPassword());
}
} else {
if (account != null) {


log.info("attempting to login anonymous using account");
}
}
}

};