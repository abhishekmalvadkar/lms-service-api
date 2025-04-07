package com.amalvadkar.lms.links.entities;

import com.amalvadkar.lms.common.entities.BaseEntity;
import com.amalvadkar.lms.tags.entities.TagEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name="links")
@Getter
@Setter
public class LinkEntity extends BaseEntity {

    @Column(name="title",nullable = false)
    private String title;

    @Column(name="url",nullable = false)
    private String url;

    @Column(name="view_count")
    private Long viewCount;

    @ManyToMany(fetch  = FetchType.LAZY)
    @JoinTable(name = "link_tags",
            joinColumns = @JoinColumn(name = "link_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    List<TagEntity> tags;


    @PrePersist
    public void preLinkPersist() {
     this.viewCount = 0L;
    }
}
