//,temp,PortProfileManagerImpl.java,59,86,temp,PortProfileManagerImpl.java,38,57
//,3
public class xxx {
    @DB
    public PortProfileVO addPortProfile(String portProfName, long vsmId, int lowVlanId, int highVlanId, PortType pType, BindingType bType) {

        // In this function, we create a port profile record in the port_profile table.

        // First, check if a port profile with the given name already exists. If it does, throw an exception.
        PortProfileVO portProfileObj;

        portProfileObj = _portProfileDao.findByName(portProfName);

        if (portProfileObj != null) {
            s_logger.info("Port Profile with specified name: " + portProfName + " already exists");
            throw new InvalidParameterValueException("Port Profile with specified name: " + portProfName + " already exists");
        }

        // Next, check if there is any existing port profile that uses a VLAN ID range that clashes with the
        // range passed to this function. If so, throw an exception.

        if (_portProfileDao.doesVlanRangeClash(lowVlanId, highVlanId) == true) {
            s_logger.info("Port Profile's vlanId range clashes with an existing Port Profile's");
            throw new InvalidParameterValueException("Port Profile's vlanId range clashes with an existing Port Profile's");
        }

        // Else, go ahead and create the port profile.
        portProfileObj = new PortProfileVO(portProfName, vsmId, lowVlanId, highVlanId, pType, bType);

        return _portProfileDao.persist(portProfileObj);
    }

};