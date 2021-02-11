//,temp,sample_4208.java,2,16,temp,sample_1506.java,2,16
//,3
public class xxx {
private String generateCopyUrl(String ipAddress, String uuid) {
String hostname = ipAddress;
String scheme = "http";
boolean _sslCopy = false;
String sslCfg = _configDao.getValue(Config.SecStorageEncryptCopy.toString());
String _ssvmUrlDomain = _configDao.getValue("secstorage.ssl.cert.domain");
if (sslCfg != null) {
_sslCopy = Boolean.parseBoolean(sslCfg);
}
if(_sslCopy && (_ssvmUrlDomain == null || _ssvmUrlDomain.isEmpty())){


log.info("empty secondary storage url domain ignoring ssl");
}
}

};