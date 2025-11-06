package com.theatermgnt.theatermgnt.combo.entity;

import com.theatermgnt.theatermgnt.common.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "comboItems")
@SQLDelete(sql = "UPDATE comboItems SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
public class ComboItem extends BaseEntity {
    String name;
    Integer quantity;
}
