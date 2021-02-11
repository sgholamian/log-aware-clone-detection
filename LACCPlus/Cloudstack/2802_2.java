//,temp,ContrailElementImpl.java,145,206,temp,ContrailGuru.java,215,291
//,3
public class xxx {
    @Override
    public void reserve(NicProfile nic, Network network, VirtualMachineProfile vm, DeployDestination dest, ReservationContext context)
            throws InsufficientVirtualNetworkCapacityException, InsufficientAddressCapacityException, ConcurrentOperationException {
        s_logger.debug("reserve NicProfile on network id: " + network.getId() + " " + network.getName());
        s_logger.debug("deviceId: " + nic.getDeviceId());

        NicVO nicVO = _nicDao.findById(nic.getId());
        assert nicVO != null;

        VirtualNetworkModel vnModel = _manager.getDatabase().lookupVirtualNetwork(network.getUuid(), _manager.getCanonicalName(network), network.getTrafficType());
        /* Network must have been implemented */
        assert vnModel != null;

        VirtualMachineModel vmModel = _manager.getDatabase().lookupVirtualMachine(vm.getUuid());
        if (vmModel == null) {
            VMInstanceVO vmVo = (VMInstanceVO)vm.getVirtualMachine();
            vmModel = new VirtualMachineModel(vmVo, vm.getUuid());
            vmModel.setProperties(_manager.getModelController(), vmVo);
        }

        VMInterfaceModel vmiModel = vmModel.getVMInterface(nicVO.getUuid());
        if (vmiModel == null) {
            vmiModel = new VMInterfaceModel(nicVO.getUuid());
            vmiModel.addToVirtualMachine(vmModel);
            vmiModel.addToVirtualNetwork(vnModel);
        }
        try {
            vmiModel.build(_manager.getModelController(), (VMInstanceVO)vm.getVirtualMachine(), nicVO);
            vmiModel.setActive();
        } catch (IOException ex) {
            s_logger.error("virtual-machine-interface set", ex);
            return;
        }

        InstanceIpModel ipModel = vmiModel.getInstanceIp();
        if (ipModel == null) {
            ipModel = new InstanceIpModel(vm.getInstanceName(), nic.getDeviceId());
            ipModel.addToVMInterface(vmiModel);
        } else {
            s_logger.debug("Reuse existing instance-ip object on " + ipModel.getName());
        }
        if (nic.getIPv4Address() != null) {
            s_logger.debug("Nic using existing IP address " + nic.getIPv4Address());
            ipModel.setAddress(nic.getIPv4Address());
        }

        try {
            vmModel.update(_manager.getModelController());
        } catch (Exception ex) {
            s_logger.warn("virtual-machine update", ex);
            return;
        }

        _manager.getDatabase().getVirtualMachines().add(vmModel);

        VirtualMachineInterface vmi = vmiModel.getVMInterface();
        // allocate mac address
        if (nic.getMacAddress() == null) {
            MacAddressesType macs = vmi.getMacAddresses();
            if (macs == null) {
                s_logger.debug("no mac address is allocated for Nic " + nicVO.getUuid());
            } else {
                s_logger.info("VMI " + _manager.getVifNameByVmUuid(vm.getUuid(), nicVO.getDeviceId()) + " got mac address: " + macs.getMacAddress().get(0));
                nic.setMacAddress(macs.getMacAddress().get(0));
            }
        }

        if (nic.getIPv4Address() == null) {
            s_logger.debug("Allocated IP address " + ipModel.getAddress());
            nic.setIPv4Address(ipModel.getAddress());
            if (network.getCidr() != null) {
                nic.setIPv4Netmask(NetUtils.cidr2Netmask(network.getCidr()));
            }
            nic.setIPv4Gateway(network.getGateway());
            nic.setFormat(AddressFormat.Ip4);
        }
    }

};