package com.covidselfcare.cosecv3;

public class QuestionLibrary
{
    public static String[] questions = {
            "Are you sick?",
            "Have you come in contact with any Covid-19 patient in the last 14 days?",
            "Are you experiencing any of the following symptoms?- Fever or feeling feverish\n-severe difficulty breathing\n-severe chest pain\n -None of the above\n"
    };
    public static String[]  answers = {"Yes","Yes","Yes"};
    public String getQuestion(int a){
        return questions[a];
    }
}
