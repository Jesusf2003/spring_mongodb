package com.crud.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "guias")
public class Guia {

    @Id
    private String id;
    private String title;
    private String description;
    private boolean published;

    public Guia(String title, String description, boolean b) {
    }
}