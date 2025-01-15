package com.amalvadkar.lms.common.entities;

import com.amalvadkar.lms.common.enums.HeaderCategoryEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "header_config")
public class HeaderConfigEntity extends BaseEntity {

    @Column(name = "header_name")
    private String headerName;

    @Column(name = "header_type")
    private String headerType;

    @Column(name = "mapping_table")
    private String mappingTable;

    @Column(name = "mapping_column")
    private String mappingColumn;

    @Enumerated(EnumType.STRING)
    @Column(name = "category")
    private HeaderCategoryEnum category;

    @Column(name = "display_order")
    private BigDecimal displayOrder;

}