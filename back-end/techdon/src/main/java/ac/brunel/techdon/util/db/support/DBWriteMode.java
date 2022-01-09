package ac.brunel.techdon.util.db.support;

/**
 * This enumeration which is expected by all
 * instances of DBInstances, and decide whether
 * the database is to be written to after every change
 * to a field, or whether it should be updates manually
 */
public enum DBWriteMode {

    AUTOMATIC,
    MANUAL

}
