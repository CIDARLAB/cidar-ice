package org.jbei.ice.lib.dto.folder;

<<<<<<< HEAD
import java.util.HashSet;
import java.util.Set;

import org.jbei.ice.lib.access.Authorization;
import org.jbei.ice.lib.access.PermissionsController;
import org.jbei.ice.lib.account.AccountType;
=======
import org.jbei.ice.lib.access.Authorization;
import org.jbei.ice.lib.access.PermissionsController;
>>>>>>> 3a93b296cacb68f217094cf7df86236a73cd323c
import org.jbei.ice.lib.account.model.Account;
import org.jbei.ice.lib.dao.DAOFactory;
import org.jbei.ice.lib.folder.Folder;

<<<<<<< HEAD
/**
=======
import java.util.HashSet;
import java.util.Set;

/**
 * Authorization specific to folder objects
 *
>>>>>>> 3a93b296cacb68f217094cf7df86236a73cd323c
 * @author Hector Plahar
 */
public class FolderAuthorization extends Authorization<Folder> {

<<<<<<< HEAD
=======
    private final PermissionsController controller = new PermissionsController();

>>>>>>> 3a93b296cacb68f217094cf7df86236a73cd323c
    public FolderAuthorization() {
        super(DAOFactory.getFolderDAO());
    }

    @Override
    public String getOwner(Folder folder) {
        return folder.getOwnerEmail();
    }

    public boolean canRead(String userId, Folder folder) {
<<<<<<< HEAD
        PermissionsController controller = new PermissionsController();

=======
>>>>>>> 3a93b296cacb68f217094cf7df86236a73cd323c
        if (controller.isPublicVisible(folder))
            return true;

        Account account = getAccount(userId);
        if (account == null)
            return false;

        if (folder.getType() == FolderType.PUBLIC)
            return true;

<<<<<<< HEAD
        if (account.getType() == AccountType.ADMIN)
            return true;

        if (userId.equals(folder.getOwnerEmail()))
=======
        if (super.canRead(userId, folder))
>>>>>>> 3a93b296cacb68f217094cf7df86236a73cd323c
            return true;

        // now check actual permissions
        Set<Folder> folders = new HashSet<>();
        folders.add(folder);
        if (controller.groupHasReadPermission(account.getGroups(), folders)
                || controller.groupHasWritePermission(account.getGroups(), folders))
            return true;

        return controller.accountHasReadPermission(account, folders)
                || controller.accountHasWritePermission(account, folders);
    }
<<<<<<< HEAD
=======

    public boolean canWrite(String userId, Folder folder) {
        Account account = getAccount(userId);
        if (account == null)
            return false;

        if (super.canWrite(userId, folder))
            return true;

        // now check actual permissions
        Set<Folder> folders = new HashSet<>();
        folders.add(folder);
        return controller.groupHasWritePermission(account.getGroups(), folders)
                || controller.accountHasWritePermission(account, folders);
    }
>>>>>>> 3a93b296cacb68f217094cf7df86236a73cd323c
}
