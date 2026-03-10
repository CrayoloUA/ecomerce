package co.edu.usbcali.ecommerceusb.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(
        name = "orders",
        schema = "public",
        indexes = {
                @Index(name = "idx_orders_user_created_at",   columnList = "user_id ASC, created_at DESC"),
                @Index(name = "idx_orders_status_created_at", columnList = "status ASC, created_at DESC")
        }
)
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_orders_user"),
            referencedColumnName = "id"
    )
    private Integer userId;

    @Column(name = "status", nullable = false, columnDefinition = "TEXT")
    private String status;

    @Column(name = "total_amount", nullable = false, precision = 12, scale = 2,
            columnDefinition = "NUMERIC(12,2) DEFAULT 0")
    private BigDecimal totalAmount;

    @Column(name = "currency", nullable = false, columnDefinition = "TEXT DEFAULT 'COP'")
    private String currency;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false,
            columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private OffsetDateTime createdAt;

    @Column(name = "paid_at", columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private OffsetDateTime paidAt;

    @Column(name = "cancelled_at", columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private OffsetDateTime cancelledAt;
}
