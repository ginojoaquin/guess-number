package com.example.scaffolding.services.impl;

import com.example.scaffolding.entities.MatchEntity;
import com.example.scaffolding.entities.UserEntity;
import com.example.scaffolding.models.*;
import com.example.scaffolding.repositories.MatchRepository;
import com.example.scaffolding.services.MatchService;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
public class MatchServiceImpl implements MatchService {
    @Autowired
    private MatchRepository matchRepository;

    @Autowired
    private ModelMapper modelMapper;

    private final Random random = new Random();

    @Override
    public Match createMatch(User user, MatchDifficulty matchDifficulty) {
        MatchEntity matchEntity = new MatchEntity();
        matchEntity.setUserEntity(modelMapper.map(user, UserEntity.class));
        matchEntity.setDifficulty(matchDifficulty);
        switch (matchDifficulty) {
            case HARD -> matchEntity.setRemainingTries(5);
            case MEDIUM -> matchEntity.setRemainingTries(8);
            case EASY -> matchEntity.setRemainingTries(10);
        }
        matchEntity.setNumberToGuess(random.nextInt(100)+1);
        matchEntity.setStatus(MatchStatus.PLAYING);
        matchEntity.setCreatedAt(LocalDateTime.now());
        matchEntity.setUpdatedAt(LocalDateTime.now());
        MatchEntity matchEntitySaved = matchRepository.save(matchEntity);
        return modelMapper.map(matchEntitySaved, Match.class);
    }

    @Override
    public Match getMatchById(Long matchId) {
        Optional<MatchEntity> matchEntityOptional = matchRepository.findById(matchId);
        if(matchEntityOptional.isEmpty()){
            throw new EntityNotFoundException("Match not found");
        }
        else {
//            Match match = modelMapper.map(matchEntityOptional.get(), Match.class);
//            return match;
            return modelMapper.map(matchEntityOptional.get(), Match.class);
        }
    }

    @Override
    public RoundMatch playMatch(Match match, Integer number) {
        RoundMatch roundMatch = new RoundMatch();
        roundMatch.setMatch(match);
        if(match.getStatus().equals(MatchStatus.FINISHED)){
            //TODO error
            return null;
        }
        if(match.getNumberToGuess().equals(number)){
            //TODO calcular score
            //TODO dar respuesta
            match.setStatus(MatchStatus.FINISHED);
            roundMatch.setResponse("GANO");
        }
        else {
            match.setRemainingTries(match.getRemainingTries()-1);
            if(match.getRemainingTries() == 0){
                match.setStatus(MatchStatus.FINISHED);
                roundMatch.setResponse("PERDIO");
            }
            else{
                if(number > match.getNumberToGuess()){
                    //TODO Responder MENOR
                    roundMatch.setResponse("MENOR");
                }
                else{
                    //TODO Responder MAYOR
                    roundMatch.setResponse("MAYOR");
                }
            }

        }
        UserEntity userEntity = modelMapper.map(match.getUser(), UserEntity.class);
        MatchEntity matchEntity = matchRepository.save(modelMapper.map(match, MatchEntity.class));
        matchEntity.setUserEntity(userEntity);
        matchEntity.setUpdatedAt(LocalDateTime.now());

        return roundMatch;
    }
}
