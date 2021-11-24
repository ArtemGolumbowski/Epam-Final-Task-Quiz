
package entity;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author agolu
 */
public class Answer implements Serializable {

    public Answer() {
    }

    public Answer(String description, boolean value) {
        this.description = description;
        this.value = value;
    }
    private String description;
    private boolean value;
    private long id;

    @Override
    public int hashCode() {

        int hash = 7;
        hash = 61 * hash + Objects.hashCode(this.description);
        hash = 61 * hash + (this.value ? 1 : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Answer other = (Answer) obj;
        if (this.value != other.value) {
            return false;
        }
        if (!Objects.equals(this.description, other.description)) {
            return false;
        }
        return true;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the value
     */
    public boolean getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(boolean value) {
        this.value = value;
    }

    /**
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Answer{" + "description=" + description + ", value=" + value + ", id=" + id + '}';
    }
    
}
