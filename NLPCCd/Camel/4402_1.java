//,temp,sample_478.java,2,17,temp,sample_477.java,2,16
//,3
public class xxx {
public void dummy_method(){
if (token == null) {
token = OAuthClientUtils.getAccessToken( WebClient.create(configuration.getOauthTokenUrl()), new Consumer( configuration.getOauthClientId(), configuration.getOauthClientSecret()), new ResourceOwnerGrant( configuration.getUserName(), configuration.getPassword()), true );
token.setIssuedAt(System.currentTimeMillis());
token.setExpiresIn(TimeUnit.MILLISECONDS.convert(token.getExpiresIn(), TimeUnit.SECONDS));
authString = token.toString();
if (token.getExpiresIn() > 0) {
expireAt = token.getIssuedAt() + token.getExpiresIn();
}
} else if (expireAt > 0 && System.currentTimeMillis() >= expireAt) {
token = OAuthClientUtils.refreshAccessToken( WebClient.create(configuration.getOauthTokenUrl()), new Consumer( configuration.getOauthClientId(), configuration.getOauthClientSecret()), token, null, false );


log.info("refreshed oauth token expires in s");
}
}

};