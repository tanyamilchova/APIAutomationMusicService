package com.example.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PlayList {

    private long id;
    private String name;
    private String description;

    private boolean isPublic;
    private long userId;
    private List<Track>tracks;

}
