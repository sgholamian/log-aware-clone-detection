//,temp,EngineDataCenterDaoImpl.java,261,276,temp,DataCenterDaoImpl.java,402,417
//,2
public class xxx {
    @Override
    public DataCenterVO findByTokenOrIdOrName(String tokenOrIdOrName) {
        DataCenterVO result = findByToken(tokenOrIdOrName);
        if (result == null) {
            result = findByName(tokenOrIdOrName);
            if (result == null) {
                try {
                    Long dcId = Long.parseLong(tokenOrIdOrName);
                    return findById(dcId);
                } catch (NumberFormatException nfe) {
                    s_logger.debug("Cannot parse " + tokenOrIdOrName + " into long. " + nfe);
                }
            }
        }
        return result;
    }

};