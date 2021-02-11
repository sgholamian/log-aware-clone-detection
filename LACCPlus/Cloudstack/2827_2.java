//,temp,LibvirtBackupSnapshotCommandWrapper.java,59,209,temp,LibvirtManageSnapshotCommandWrapper.java,54,168
//,3
public class xxx {
    @Override
    public Answer execute(final ManageSnapshotCommand command, final LibvirtComputingResource libvirtComputingResource) {
        final String snapshotName = command.getSnapshotName();
        final String snapshotPath = command.getSnapshotPath();
        final String vmName = command.getVmName();
        try {
            final LibvirtUtilitiesHelper libvirtUtilitiesHelper = libvirtComputingResource.getLibvirtUtilitiesHelper();
            final Connect conn = libvirtUtilitiesHelper.getConnectionByVmName(vmName);
            DomainState state = null;
            Domain vm = null;
            if (vmName != null) {
                try {
                    vm = libvirtComputingResource.getDomain(conn, command.getVmName());
                    state = vm.getInfo().state;
                } catch (final LibvirtException e) {
                    s_logger.trace("Ignoring libvirt error.", e);
                }
            }

            final KVMStoragePoolManager storagePoolMgr = libvirtComputingResource.getStoragePoolMgr();
            final StorageFilerTO pool = command.getPool();
            final KVMStoragePool primaryPool = storagePoolMgr.getStoragePool(pool.getType(), pool.getUuid());

            final KVMPhysicalDisk disk = primaryPool.getPhysicalDisk(command.getVolumePath());
            if (state == DomainState.VIR_DOMAIN_RUNNING && !primaryPool.isExternalSnapshot()) {

                final MessageFormat snapshotXML = new MessageFormat("   <domainsnapshot>" + "       <name>{0}</name>" + "          <domain>"
                        + "            <uuid>{1}</uuid>" + "        </domain>" + "    </domainsnapshot>");

                final String vmUuid = vm.getUUIDString();
                final Object[] args = new Object[] {snapshotName, vmUuid};
                final String snapshot = snapshotXML.format(args);
                s_logger.debug(snapshot);
                if (command.getCommandSwitch().equalsIgnoreCase(ManageSnapshotCommand.CREATE_SNAPSHOT)) {
                    vm.snapshotCreateXML(snapshot);
                } else {
                    final DomainSnapshot snap = vm.snapshotLookupByName(snapshotName);
                    snap.delete(0);
                }

                /*
                 * libvirt on RHEL6 doesn't handle resume event emitted from
                 * qemu
                 */
                vm = libvirtComputingResource.getDomain(conn, command.getVmName());
                state = vm.getInfo().state;
                if (state == DomainState.VIR_DOMAIN_PAUSED) {
                    vm.resume();
                }
            } else {
                /**
                 * For RBD we can't use libvirt to do our snapshotting or any Bash scripts.
                 * libvirt also wants to store the memory contents of the Virtual Machine,
                 * but that's not possible with RBD since there is no way to store the memory
                 * contents in RBD.
                 *
                 * So we rely on the Java bindings for RBD to create our snapshot
                 *
                 * This snapshot might not be 100% consistent due to writes still being in the
                 * memory of the Virtual Machine, but if the VM runs a kernel which supports
                 * barriers properly (>2.6.32) this won't be any different then pulling the power
                 * cord out of a running machine.
                 */
                if (primaryPool.getType() == StoragePoolType.RBD) {
                    try {
                        final Rados r = new Rados(primaryPool.getAuthUserName());
                        r.confSet("mon_host", primaryPool.getSourceHost() + ":" + primaryPool.getSourcePort());
                        r.confSet("key", primaryPool.getAuthSecret());
                        r.confSet("client_mount_timeout", "30");
                        r.connect();
                        s_logger.debug("Succesfully connected to Ceph cluster at " + r.confGet("mon_host"));

                        final IoCTX io = r.ioCtxCreate(primaryPool.getSourceDir());
                        final Rbd rbd = new Rbd(io);
                        final RbdImage image = rbd.open(disk.getName());

                        if (command.getCommandSwitch().equalsIgnoreCase(ManageSnapshotCommand.CREATE_SNAPSHOT)) {
                            s_logger.debug("Attempting to create RBD snapshot " + disk.getName() + "@" + snapshotName);
                            image.snapCreate(snapshotName);
                        } else {
                            s_logger.debug("Attempting to remove RBD snapshot " + disk.getName() + "@" + snapshotName);
                            image.snapRemove(snapshotName);
                        }

                        rbd.close(image);
                        r.ioCtxDestroy(io);
                    } catch (final Exception e) {
                        s_logger.error("A RBD snapshot operation on " + disk.getName() + " failed. The error was: " + e.getMessage());
                    }
                } else {
                    /* VM is not running, create a snapshot by ourself */
                    final int cmdsTimeout = libvirtComputingResource.getCmdsTimeout();
                    final String manageSnapshotPath = libvirtComputingResource.manageSnapshotPath();

                    final Script scriptCommand = new Script(manageSnapshotPath, cmdsTimeout, s_logger);
                    if (command.getCommandSwitch().equalsIgnoreCase(ManageSnapshotCommand.CREATE_SNAPSHOT)) {
                        scriptCommand.add("-c", disk.getPath());
                    } else {
                        scriptCommand.add("-d", snapshotPath);
                    }

                    scriptCommand.add("-n", snapshotName);
                    final String result = scriptCommand.execute();
                    if (result != null) {
                        s_logger.debug("Failed to manage snapshot: " + result);
                        return new ManageSnapshotAnswer(command, false, "Failed to manage snapshot: " + result);
                    }
                }
            }
            return new ManageSnapshotAnswer(command, command.getSnapshotId(), disk.getPath() + File.separator + snapshotName, true, null);
        } catch (final LibvirtException e) {
            s_logger.debug("Failed to manage snapshot: " + e.toString());
            return new ManageSnapshotAnswer(command, false, "Failed to manage snapshot: " + e.toString());
        }
    }

};