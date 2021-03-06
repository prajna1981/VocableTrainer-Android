package vocabletrainer.heinecke.aron.vocabletrainer.lib.Storage;

import java.io.Serializable;

import static vocabletrainer.heinecke.aron.vocabletrainer.lib.Database.MIN_ID_TRESHOLD;

/**
 * DB Vocable List entry
 */
public class VList implements Serializable {
    private String nameA;
    private String nameB;
    private String name;
    private int totalVocs;
    private int unfinishedVocs;
    private int id;

    /**
     * Creates a new VList data object
     *
     * @param id    ID
     * @param nameA Name for A Column
     * @param nameB Name for B Column
     */
    public VList(final int id, final String nameA, final String nameB, final String name) {
        this.id = id;
        this.nameA = nameA;
        this.nameB = nameB;
        this.name = name;
        this.totalVocs = MIN_ID_TRESHOLD - 1;
        this.unfinishedVocs = MIN_ID_TRESHOLD - 1;
    }

    /**
     * Check whether this entry is existing, according to it's ID<br>
     *     <b>Note:</b> this is not a check whether this entity exists in the Database
     * @return true if the ID is valid
     */
    public boolean isExisting(){
        return isIDValid(id);
    }

    /**
     * Checks whether a given ID is valid, according to MIN_ID_TRESHOLD
     * @param id ID to check
     * @return true if ID is valid
     */
    public static boolean isIDValid(final int id){
        return id >= MIN_ID_TRESHOLD;
    }

    /**
     * Create a new VList with none-ID -1
     *
     * @param nameA Name for A Column
     * @param nameB Name for B Column
     */
    public VList(final String nameA, final String nameB, final String name) {
        this(MIN_ID_TRESHOLD - 1, nameA, nameB, name);
    }

    /**
     * Creates a new table data object<br>
     * All fields except ID will be empty thens
     *
     * @param id
     */
    public VList(final int id) {
        this(id, null, null, null);
    }

    @Override
    public String toString() {
        return getId() + " " + getName() + " " + getNameA() + " " + getNameB() + " " + getTotalVocs() + " " + getUnfinishedVocs();
    }


    /**
     * Tests for equality based on list ID
     *
     * @param list
     * @return true when list IDs are equal
     */
    public boolean equals(VList list) {
        if (this == list) {
            return true;
        }
        return this.id == list.getId();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof VList) {
            return equals((VList) obj);
        }
        return super.equals(obj);
    }

    /**
     * Returns the amount of vocables this table has<br>
     * The value can be -1 when not set!
     *
     * @return
     */
    public int getTotalVocs() {
        return totalVocs;
    }

    public void setTotalVocs(int total) {
        this.totalVocs = total;
    }

    /**
     * Returns the amount of unfinished vocables<br>
     * The value can be -1 when not set!
     *
     * @return
     */
    public int getUnfinishedVocs() {
        return unfinishedVocs;
    }

    public void setUnfinishedVocs(int unfinished) {
        this.unfinishedVocs = unfinished;
    }

    public String getNameA() {

        return nameA;
    }

    public void setNameA(String nameA) {
        this.nameA = nameA;
    }

    public String getNameB() {
        return nameB;
    }

    public void setNameB(String nameB) {
        this.nameB = nameB;
    }

    public int getId() {
        return id;
    }

    /**
     * Set a new ID
     *
     * @param id new ID
     * @throws IllegalAccessException if a valid ID is already set
     */
    public void setId(int id) {
        if (this.id < MIN_ID_TRESHOLD)
            this.id = id;
        else
            throw new IllegalAccessError("Can't override existing VList ID");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
