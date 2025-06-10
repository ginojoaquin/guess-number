package com.example.scaffolding.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Match {
    private Long id;
    private User user;
    private MatchDifficulty difficulty;
    private MatchStatus status;
    private Integer numberToGuess;
    private Integer remainingTries;
}
