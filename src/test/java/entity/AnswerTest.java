/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import static org.junit.Assert.*;

/**
 *
 * @author agolu
 */
public class AnswerTest {

    static Answer instance;

    public AnswerTest() {
    }

    @org.junit.BeforeClass
    public static void setUpClass() throws Exception {
        instance = new Answer();
        instance.setDescription("short description");
        instance.setId(5);
        instance.setValue(true);
    }

    @org.junit.AfterClass
    public static void tearDownClass() throws Exception {
        instance = null;
    }

    @org.junit.Before
    public void setUp() throws Exception {
    }

    @org.junit.After
    public void tearDown() throws Exception {
    }

    /**
     * Test of hashCode method, of class Answer.
     */
    @org.junit.Test
    public void testHashCode() {
        System.out.println("hashCode");
        int expResult = instance.hashCode();
        int result = instance.hashCode();
        assertEquals(expResult, result);

    }

    /**
     * Test of equals method, of class Answer.
     */
    @org.junit.Test
    public void testEquals() {
        System.out.println("equals");
        Answer instance2 = new Answer();
        instance2.setDescription("short description");
        instance2.setId(5);
        instance2.setValue(true);
        boolean expResult = true;
        boolean result = instance.equals(instance2);
        assertEquals(expResult, result);
    }

    /**
     * Test of getDescription method, of class Answer.
     */
    @org.junit.Test
    public void testGetDescription() {
        System.out.println("getDescription");
        String expResult = "short description";
        String result = instance.getDescription();
        assertEquals(expResult, result);
    }

    /**
     * Test of setDescription method, of class Answer.
     */
    @org.junit.Test
    public void testSetDescription() {
        System.out.println("setDescription");
        String description = "short description";
        instance.setDescription(description);
        String expResult = instance.getDescription();
        assertEquals(expResult, description);
    }

    /**
     * Test of getValue method, of class Answer.
     */
    @org.junit.Test
    public void testGetValue() {
        System.out.println("getValue");
        boolean expResult = true;
        boolean result = instance.getValue();
        assertEquals(expResult, result);

    }

    /**
     * Test of setValue method, of class Answer.
     */
    @org.junit.Test
    public void testSetValue() {
        System.out.println("setValue");
        boolean value = false;
        instance.setValue(value);
boolean expResult = instance.getValue();
assertEquals(expResult, value);
    }

    /**
     * Test of getId method, of class Answer.
     */
    @org.junit.Test
    public void testGetId() {
        System.out.println("getId");
        long expResult = 5L;
        long result = instance.getId();
        assertEquals(expResult, result);
    }

    /**
     * Test of setId method, of class Answer.
     */
    @org.junit.Test
    public void testSetId() {
        System.out.println("setId");
        long id = 4L;
        instance.setId(id);
        long expResult=instance.getId();
assertEquals(expResult, id);
    }

}
