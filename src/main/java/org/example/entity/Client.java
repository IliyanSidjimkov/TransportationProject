package org.example.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "client")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Client {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "client_name", nullable = false, length = 100)
  private String clientName;

  @ManyToOne
  @JoinColumn(name = "company_id", nullable = false)
  private Company company;

  @OneToMany(mappedBy = "client")
  @ToString.Exclude
  private List<Shipment> shipments = new ArrayList<>();
}
