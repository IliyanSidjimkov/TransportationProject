package org.example.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "vehicle")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Vehicle {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "registration_number", nullable = false, unique = true)
  private String registrationNumber;

  @Column(name = "type", nullable = false)
  private String type;

  @Column(name = "capacity",nullable = false)
  private Double capacity;

  @ManyToOne
  @JoinColumn(name = "company_id", nullable = false)
  private Company company;

  @ManyToOne
  @JoinColumn(name = "driver_id")
  private Employee driver;

  @OneToMany(mappedBy = "vehicle")
  @ToString.Exclude
  private List<Shipment> shipments = new ArrayList<>();
}
