package SniffStep.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BoardType {

    PET_SITTER("petSitter"),
    PET_OWNER("petOwner");

    private final String value;
}
