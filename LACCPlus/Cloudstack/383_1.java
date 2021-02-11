//,temp,TemplateServiceImpl.java,1077,1100,temp,CloudStackImageStoreDriverImpl.java,92,116
//,3
public class xxx {
    private String generateCopyUrl(String ipAddress, String dir, String path) {
        String hostname = ipAddress;
        String scheme = "http";
        boolean _sslCopy = false;
        String sslCfg = _configDao.getValue(Config.SecStorageEncryptCopy.toString());
        String _ssvmUrlDomain = _configDao.getValue("secstorage.ssl.cert.domain");
        if (sslCfg != null) {
            _sslCopy = Boolean.parseBoolean(sslCfg);
        }
        if(_sslCopy && (_ssvmUrlDomain == null || _ssvmUrlDomain.isEmpty())){
            s_logger.warn("Empty secondary storage url domain, ignoring SSL");
            _sslCopy = false;
        }
        if (_sslCopy) {
            if(_ssvmUrlDomain.startsWith("*")) {
                hostname = ipAddress.replace(".", "-");
                hostname = hostname + _ssvmUrlDomain.substring(1);
            } else {
                hostname = _ssvmUrlDomain;
            }
            scheme = "https";
        }
        return scheme + "://" + hostname + "/copy/SecStorage/" + dir + "/" + path;
    }

};