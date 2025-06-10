package com.example.scaffolding.services.impl;

import com.example.scaffolding.entities.UserEntity;
import com.example.scaffolding.models.Match;
import com.example.scaffolding.models.MatchDifficulty;
import com.example.scaffolding.models.RoundMatch;
import com.example.scaffolding.models.User;
import com.example.scaffolding.repositories.UserRepository;
import com.example.scaffolding.services.MatchService;
import com.example.scaffolding.services.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private MatchService matchService;

    @Override
    public User createUser(String userName, String email) {
        Optional<UserEntity> userEntityOptional = userRepository.findByEmail(email);
        if (userEntityOptional.isPresent()) {
            //TODO Enviar error al user.
            return null;
        }
        else {
            UserEntity userEntity = new UserEntity();
            userEntity.setUserName(userName);
            userEntity.setEmail(email);
            UserEntity userEntitySaved = userRepository.save(userEntity);
            return modelMapper.map(userEntitySaved, User.class);
        }
    }

    @Override
    public Match createUserMatch(Long userId, MatchDifficulty matchDifficulty){
        Optional<UserEntity> userEntity = userRepository.findById(userId);
        if(userEntity.isEmpty()){
            throw new EntityNotFoundException("User not found");
        }
        else {
            User user = modelMapper.map(userEntity.get(), User.class);
            return matchService.createMatch(user, matchDifficulty);
        }

    }

    @Override
    public RoundMatch playUserMatch(Long userId, Long matchId, Integer numberToPlay) {
        Match match = matchService.getMatchById(matchId);
        if(!match.getUser().getId().equals(userId)){
            //TODO error
            return null;
        }
        else {
            return matchService.playMatch(match, numberToPlay);
        }
    }
}
