package com.nahorniak.inventorymanagementservice.persistance;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "shops")
@Accessors(chain = true)
public class ShopEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name") private String name;
    @Column(name = "address") private String address;
    @Column(name = "contact_number") private String contactNumber;
    @Column(name = "description") private String description;

    @OneToOne(fetch = FetchType.LAZY)
    private UserEntity manager;
}
