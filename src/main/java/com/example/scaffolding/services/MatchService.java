package com.example.scaffolding.services;

import com.example.scaffolding.models.Match;
import com.example.scaffolding.models.MatchDifficulty;
import com.example.scaffolding.models.RoundMatch;
import com.example.scaffolding.models.User;
import org.springframework.stereotype.Service;

@Service
public interface MatchService {
    Match createMatch(User user, MatchDifficulty matchDifficulty);

    Match getMatchById(Long matchId);

    RoundMatch playMatch(Match match, Integer number);
}
