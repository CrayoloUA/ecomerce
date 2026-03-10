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
        name = "inventory_movements",
        schema = "public",
        indexes = {
                @Index(name = "idx_inventory_movements_product_created_at", columnList = "product_id ASC, created_at DESC"),
                @Index(name = "idx_inventory_movements_order",              columnList = "order_id")
        }
)
public class InventoryMovement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_inv_mov_product"),
            referencedColumnName = "id"
    )
    private Integer productId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_inv_mov_order"),
            referencedColumnName = "id"
    )
    private Integer orderId;

    @Column(name = "type", nullable = false, columnDefinition = "TEXT")
    private String type;

    @Column(name = "qty", nullable = false)
    private Integer qty;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false,
            columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private OffsetDateTime createdAt;
}
