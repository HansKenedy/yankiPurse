package com.proyecto.yankiPurse.domain;

import lombok.*;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@ToString
@EqualsAndHashCode(of = {"cardNumber"})
@AllArgsConstructor
@NoArgsConstructor
@Document(value = "purse")
public class Purse {
    @Id
    private String id;

    private Boolean associatedCard;

    @Indexed(unique = true)
    private String cellPhone;

    private Double amount;
}
