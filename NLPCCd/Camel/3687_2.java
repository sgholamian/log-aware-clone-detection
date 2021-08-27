//,temp,sample_4958.java,2,14,temp,sample_4959.java,2,17
//,3
public class xxx {
public Facebook getFacebook() throws FacebookException {
if (facebook == null) {
final Configuration configuration = getConfiguration();
FacebookFactory factory = new FacebookFactory(configuration);
if (this.oAuthAccessToken == null) {
facebook = factory.getInstance(new OAuthAuthorization(configuration));
facebook.getOAuthAppAccessToken();
} else {
facebook = factory.getInstance();
facebook.getOAuthAccessToken();


log.info("login with app id secret and token all apis accessible");
}
}
}

};