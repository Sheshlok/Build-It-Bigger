package com.example;

import java.util.Random;

public class JokeSmith {

    private static String[] jokes;

    static {
        jokes = new String[10];

        jokes[0] = "Q. What is an algorithm \n" +
                "A. Word used by programmers who when..\n they do not want to explain what they did";

        jokes[1] = "Q. What is the object-oriented way to become wealthy? \n" +
                "A. Inheritance";

        jokes[2] = "Q. What is a programmer's favorite hang-out place? \n" +
                "A. Foo bar";

        jokes[3] = "Q. What do computers and air-conditioners have in common? \n" +
                "A. They both become useless when you open windows :)";

        jokes[4] = "Q. How do you tell HTML from HTML5? \n" +
                "A. Try it out in Internet Explorer. Did it work? No?...It's HTML5";

        jokes[5] = "The word \"algorithm\" was coined to recognise Al Gore's contribution to computer science";

        jokes[6] = "A programmer had a problem. He decided to use Java. Now he has a ProblemFactory";

        jokes[7] = "I'd like to make the world a better place. But they don't give me the source code";

        jokes[8] = "I don't see women as objects. I consider each to be in a \"class\" of her own";

        jokes[9] = "Unix is user-friendly....It's just very particular about who its friends are";

    }
    public String getJokeOfTheDay() {
        return jokes[new Random().nextInt(jokes.length)];
    }
}
