package org.jbei.ice.lib.dto.web;

import org.jbei.ice.lib.dao.IDataTransferModel;

/**
 * Status for remote partners
 *
 * @author Hector Plahar
 */
public enum RemotePartnerStatus implements IDataTransferModel {

<<<<<<< HEAD
=======
    // information about the remote instance has been saved but contact did not succeed
    NOT_CONTACTED,

>>>>>>> 3a93b296cacb68f217094cf7df86236a73cd323c
    // remote partner has been blocked from sending and receiving results from this registry
    BLOCKED,

    // partner approved to send and receive results from this registry
    APPROVED,

    // request to partner has been sent and awaiting response
    PENDING,

    // request received; pending admin approval
    PENDING_APPROVAL
}
