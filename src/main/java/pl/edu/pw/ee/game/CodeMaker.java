package pl.edu.pw.ee.game;

import lombok.Value;

@Value
public class CodeMaker {

    Code secretCode;

    public CodeMaker(GameVariant variant) {
        secretCode = new Code(variant);
        System.out.printf("SECRET CODE %s\n", secretCode);
    }

    public Guess evaluateGuess(Code guessCode) {
        var score = secretCode.compareTo(guessCode);
        return new Guess(guessCode, score);
    }

}
