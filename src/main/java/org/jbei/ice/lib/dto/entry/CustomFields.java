package org.jbei.ice.lib.dto.entry;

import org.jbei.ice.lib.dao.DAOFactory;
import org.jbei.ice.lib.dao.hibernate.EntryDAO;
import org.jbei.ice.lib.dao.hibernate.ParameterDAO;
import org.jbei.ice.lib.entry.EntryAuthorization;
import org.jbei.ice.lib.entry.model.Entry;
import org.jbei.ice.lib.entry.model.Parameter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Hector Plahar
 */
public class CustomFields {

    private EntryAuthorization authorization;
    private final ParameterDAO dao;

    public CustomFields() {
        this.authorization = new EntryAuthorization();
        this.dao = DAOFactory.getParameterDAO();
    }

    public long createField(String userId, long partId, CustomField field) {
        EntryDAO entryDAO = DAOFactory.getEntryDAO();

        Entry entry = entryDAO.get(partId);
        authorization.expectWrite(userId, entry);

        Parameter parameter = new Parameter();
        parameter.setEntry(entry);
        parameter.setKey(field.getName());
        parameter.setValue(field.getValue());
        entry.getParameters().add(parameter);
        parameter = this.dao.create(parameter);
        return parameter.getId();
    }

    public CustomField updateField(String userId, long id, CustomField customField) {
        Parameter parameter = dao.get(id);
        if (parameter == null)
            return null;

        Entry entry = parameter.getEntry();
        authorization.expectWrite(userId, entry);

        parameter.setValue(customField.getValue());
        parameter.setKey(customField.getName());
        return dao.update(parameter).toDataTransferObject();
    }

    public CustomField getField(String userId, long id) {
        Parameter parameter = dao.get(id);
        if (parameter == null)
            return null;

        Entry entry = parameter.getEntry();
        authorization.expectRead(userId, entry);
        return parameter.toDataTransferObject();
    }

    public List<CustomField> getFieldsForPart(String userId, long partId) {
        EntryDAO entryDAO = DAOFactory.getEntryDAO();
        Entry entry = entryDAO.get(partId);
        authorization.expectRead(userId, entry);

        List<CustomField> result = new ArrayList<>();
        if (entry.getParameters() == null)
            return result;

        for (Parameter parameter : entry.getParameters()) {
            result.add(parameter.toDataTransferObject());
        }
        return result;
    }

    /**
     * Deletes the custom field specified by the unique identifier in the parameter.
     * The user must have write privileges on field associated with entry
     *
     * @param userId account identifier for user performing the action
     * @param id     unique identifier for custom field
     * @return true if field is found and deleted successfully, false otherwise (including when the field is not found)
     */
    public boolean deleteField(String userId, long id) {
        Parameter parameter = dao.get(id);
        if (parameter == null)
            return false;

        Entry entry = parameter.getEntry();
        authorization.expectWrite(userId, entry);
        entry.getParameters().remove(parameter);
        dao.delete(parameter);
        return true;
    }
}
