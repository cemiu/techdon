package ac.brunel.techdon.util.db.fields;

/**
 * Defines the fields which are specific to a remote
 * {@link ac.brunel.techdon.util.db.DBDonor} object
 */
public enum DBDonorField implements DBField {

	;
	
    private final String key;

    DBDonorField(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

}
