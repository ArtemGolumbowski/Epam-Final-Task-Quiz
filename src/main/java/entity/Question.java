
package entity;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author agolu
 */
public class Question implements Serializable{
    private long id;
    private String description;
    private List <Answer> answers;

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
     * @return the answers
     */
    public List <Answer> getAnswers() {
        return answers;
    }

    /**
     * @param answers the answers to set
     */
    public void setAnswers(List <Answer> answers) {
        this.answers = answers;
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
}
