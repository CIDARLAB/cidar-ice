package org.jbei.ice.lib.experiment;

import org.jbei.ice.lib.dao.IDataTransferModel;

/**
 * Data transfer object for experiments
 *
 * @author Hector Plahar
 */
public class Study implements IDataTransferModel {

    private long id;
    private String partId;
    private String label;
    private String url;
    private long created;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPartId() {
        return partId;
    }

    public void setPartId(String partId) {
        this.partId = partId;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public long getCreated() {
        return created;
    }

    public void setCreated(long created) {
        this.created = created;
    }
}
