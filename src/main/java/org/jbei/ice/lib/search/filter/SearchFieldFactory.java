package org.jbei.ice.lib.search.filter;

import java.util.HashSet;

import org.jbei.ice.lib.entry.model.ArabidopsisSeed;
import org.jbei.ice.lib.entry.model.Entry;
import org.jbei.ice.lib.entry.model.Part;
import org.jbei.ice.lib.entry.model.Plasmid;
import org.jbei.ice.lib.entry.model.Strain;
import org.jbei.ice.lib.shared.dto.entry.EntryType;

/**
 * @author Hector Plahar
 */
public class SearchFieldFactory {

    private static HashSet<String> commonFields = new HashSet<>();
    private static HashSet<String> strainFields = new HashSet<>();
    private static HashSet<String> plasmidFields = new HashSet<>();
    private static HashSet<String> seedFields = new HashSet<>();

    static {
        commonFields.add("owner");
        commonFields.add("ownerEmail");
        commonFields.add("creator");
        commonFields.add("names.name");
        commonFields.add("alias");
        commonFields.add("creatorEmail");
        commonFields.add("keywords");
        commonFields.add("shortDescription");
        commonFields.add("longDescription");
        commonFields.add("intellectualProperty");
        commonFields.add("references");
        commonFields.add("partNumbers.partNumber");
        commonFields.add("links.link");
        commonFields.add("links.url");
        commonFields.add("selectionMarkers.name");
        commonFields.add("entryFundingSources.fundingSource.fundingSource");
        commonFields.add("entryFundingSources.fundingSource.principalInvestigator");

        // strain fields
        strainFields.add("plasmids");
        strainFields.add("genotypePhenotype");
        strainFields.add("host");

        // plasmid fields
        plasmidFields.add("backbone");
        plasmidFields.add("promoters");
        plasmidFields.add("replicatesIn");
        plasmidFields.add("originOfReplication");

        // seed fields
        seedFields.add("ecotype");
        seedFields.add("generation");
        seedFields.add("parents");
        seedFields.add("plantType");
    }

    public static HashSet<String> getCommonFields() {
        return commonFields;
    }

    public static HashSet<String> entryFields(EntryType type) {
        switch (type) {
            case STRAIN:
                return strainFields;

            case PLASMID:
                return plasmidFields;

            case ARABIDOPSIS:
                return seedFields;

            default:
                return commonFields;
        }
    }

    public static Class<?> entryClass(EntryType type) {
        switch (type) {
            case STRAIN:
                return Strain.class;

            case PLASMID:
                return Plasmid.class;

            case ARABIDOPSIS:
                return ArabidopsisSeed.class;

            case PART:
                return Part.class;

            default:
                return Entry.class;
        }
    }
}
