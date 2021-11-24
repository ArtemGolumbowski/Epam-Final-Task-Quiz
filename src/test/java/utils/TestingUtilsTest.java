/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import entity.Answer;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author agolu
 */
public class TestingUtilsTest {

    public TestingUtilsTest() {
    }

    @BeforeClass
    public static void setUpClass() {

    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of checkAnswer method, of class TestingUtils.
     */
    @Test
    public void testCheckAnswer() {
        System.out.println("checkAnswer");
        String[] userAnswers = {"hi!", "hello!"};
        List<Answer> answers = new ArrayList<>();
        Answer answer = new Answer();
        answer.setDescription("hi!");
        answer.setValue(true);
        Answer answer1 = new Answer();
        answer1.setDescription("bye");
        answer1.setValue(false);
        Answer answer2 = new Answer();
        answer2.setDescription("hello!");
        answer2.setValue(true);
        Answer answer3 = new Answer();
        answer3.setDescription("good");
        answer3.setValue(false);
        answers.add(answer);
        answers.add(answer1);
        answers.add(answer2);
        answers.add(answer3);
        int expResult = 1;
        int result = TestingUtils.checkAnswer(userAnswers, answers);
        assertEquals(expResult, result);   
    }

}
