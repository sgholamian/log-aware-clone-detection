//,temp,PortProfileManagerImpl.java,59,86,temp,PortProfileManagerImpl.java,38,57
//,3
public class xxx {
    @DB
    public PortProfileVO addPortProfile(String portProfName, long vsmId, int vlanId, PortType pType, BindingType bType) {

        // In this function, we create a port profile record in the port_profile table.
        // First, check if a port profile with the given name already exists. If it does, throw an exception.

        if (_portProfileDao.findByName(portProfName) != null) {
            s_logger.info("Port Profile with specified name: " + portProfName + " already exists");
            throw new InvalidParameterValueException("Port Profile with specified name: " + portProfName + " already exists");
        }
        // Check if the VSM id is a valid one.

        // TODO: Should we also check whether a port profile for the specified vlanId already exists, and if so,
        // fail this function? Do we want to enforce such a 1:1 mapping b/w port profile and vlanId?

        // Else, go ahead and create the port profile.
        PortProfileVO portProfileObj = new PortProfileVO(portProfName, vsmId, vlanId, pType, bType);

        return _portProfileDao.persist(portProfileObj);
    }

};