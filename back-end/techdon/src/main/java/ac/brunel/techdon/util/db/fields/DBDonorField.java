package ac.brunel.techdon.util.db.fields;

public enum DBDonorField implements DBFields {

    ; // TODO add fields ?

    private String key;

    DBDonorField(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

}
