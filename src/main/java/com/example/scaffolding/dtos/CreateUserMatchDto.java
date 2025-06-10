package com.example.scaffolding.dtos;

import com.example.scaffolding.models.MatchDifficulty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CreateUserMatchDto {
    private MatchDifficulty difficulty;
}
