package com.udacity.jokes;

import jdk.nashorn.internal.scripts.JO;

public class JokeMaker {

    private static final String JOKE_1 = "A giraffe walks into a whore house.\n" +
            "Says the giraffe, “Give me two cowboy hats please.”\n" +
            "Says the guy behind the counter, “We don't serve at this hour.”\n" +
            "Says the giraffe, “Oh, that’s fine, I’m working tomorrow.”";


    private static final String JOKE_2 = "A green elephant walks into a bar.\n" +
            "Says the green elephant, “Give me two pencils please.”\n" +
            "Says the guy behind the counter, “I've heard that one before...”\n" +
            "Says the green elephant, “Oh, that’s fine, I’m Italian.”";

    private static final String JOKE_3 = "A snobby princess walks into a store.\n" +
            "Says the snobby princess, “Give me two computers please.”\n" +
            "Says the guy behind the counter, “Can you pay for it, too?”\n" +
            "Says the snobby princess, “Oh, that’s fine, I’m 16 years old.”";

    private static final String[] JOKES = { JOKE_1, JOKE_2, JOKE_3};

    private static int CURRENT_JOKE_INDEX = 0;

    public static String getJoke() {
        String nextJoke = JOKES[CURRENT_JOKE_INDEX];

        CURRENT_JOKE_INDEX = (CURRENT_JOKE_INDEX + 1) % JOKES.length;

        return nextJoke;
    }

}
