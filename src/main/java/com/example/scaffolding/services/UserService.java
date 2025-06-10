package com.example.scaffolding.services;

import com.example.scaffolding.models.Match;
import com.example.scaffolding.models.MatchDifficulty;
import com.example.scaffolding.models.RoundMatch;
import com.example.scaffolding.models.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    User createUser(String userName, String email);

    Match createUserMatch(Long userId, MatchDifficulty matchDifficulty);

    RoundMatch playUserMatch(Long userId, Long matchId, Integer numberToPlay);

}
