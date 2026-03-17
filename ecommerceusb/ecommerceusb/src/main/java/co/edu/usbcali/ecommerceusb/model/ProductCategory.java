package co.edu.usbcali.ecommerceusb.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(
        name = "product_categories",
        schema = "public",
        uniqueConstraints = {
                @UniqueConstraint(name = "uq_product_category", columnNames = {"product_id", "category_id"})
        },
        indexes = {
                @Index(name = "idx_product_categories_category", columnList = "category_id, product_id")
        }
)
public class ProductCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_pc_product"),
            referencedColumnName = "id"
    )
    private Integer productId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_pc_category"),
            referencedColumnName = "id"
    )
    private Integer categoryId;
}
