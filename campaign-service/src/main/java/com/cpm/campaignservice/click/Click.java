package com.cpm.campaignservice.click;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "clicks")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Click {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    Long campaignId;

    Long productId;

    BigDecimal cost;

    LocalDateTime createdAt;

}
