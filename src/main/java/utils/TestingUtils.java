/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import entity.Answer;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author agolu
 */
public class TestingUtils {

    private TestingUtils() {
    }
    public static int checkAnswer(String[] userAnswers, List<Answer> answers) {
        List<Answer> userAnswerList = new ArrayList<>();
        if(userAnswers == null){
            return 0;
        }else{
        for (String userAnswer : userAnswers) {
            Answer answer = new Answer(userAnswer, true);
            userAnswerList.add(answer);
        }
        answers = answers.stream().filter(answer->answer.getValue()).collect(Collectors.toList());  
        if (userAnswerList.equals(answers)) {
            return 1;
        }
        }
        return 0;
    }
    

}
