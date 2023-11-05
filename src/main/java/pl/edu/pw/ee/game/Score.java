package pl.edu.pw.ee.game;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class Score {

    Integer correctColorAndPlace;
    Integer colorPresentButWrongPlace;

}
