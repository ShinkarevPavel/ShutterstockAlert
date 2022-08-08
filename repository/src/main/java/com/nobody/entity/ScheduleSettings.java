package com.nobody.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "schedule_settings")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ScheduleSettings {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String value;
    @Column(name = "is_current")
    private Boolean isCurrent;
}
