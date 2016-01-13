package org.jbei.ice.lib.dto.entry;

import org.jbei.ice.lib.dao.IDataTransferModel;

/**
 * User created fields for entry
 *
 * @author Hector Plahar
 */
public class CustomField implements IDataTransferModel {

    private long id;
    private long partId;
    private String name;
    private String value;

    public CustomField() {
    }

    public CustomField(long id, long partId, String name, String value) {
        this.id = id;
        this.name = name;
        this.value = value;
        this.partId = partId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public long getPartId() {
        return partId;
    }

    public void setPartId(long partId) {
        this.partId = partId;
    }
}
