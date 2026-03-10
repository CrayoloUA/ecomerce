package co.edu.usbcali.ecommerceusb.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.OffsetDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(
        name = "payments",
        schema = "public",
        uniqueConstraints = {
                @UniqueConstraint(name = "uq_payments_idempotency_key", columnNames = "idempotency_key")
        },
        indexes = {
                @Index(name = "idx_payments_order", columnList = "order_id")
        }
)
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_payments_order"),
            referencedColumnName = "id"
    )
    private Integer orderId;

    @Column(name = "status", nullable = false, columnDefinition = "TEXT")
    private String status;

    @Column(name = "provider_ref", columnDefinition = "TEXT")
    private String providerRef;

    @Column(name = "idempotency_key", nullable = false, columnDefinition = "TEXT")
    private String idempotencyKey;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false,
            columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private OffsetDateTime createdAt;
}
