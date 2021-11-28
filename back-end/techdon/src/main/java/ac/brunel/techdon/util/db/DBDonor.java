package ac.brunel.techdon.util.db;


import ac.brunel.techdon.util.db.fields.DBDonorField;

public class DBDonor extends DBUser {

    public DBDonor() {
        super();
        // next developer has to populate all fields, and manually write to db
    }

    // TODO ability to have an array of "donated device" documents, with easy integration

    public void set(DBDonorField field, Object value) {
        set(field.getKey(), value);
    }

}
