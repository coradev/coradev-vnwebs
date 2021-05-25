package com.coradev.vnwebs.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;

@Entity
@Table(name = "POST")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "create_date")
    private Date createdDate;

    @Column(name = "cover_image")
    private String coverImage;

    @Column(name = "published")
    private boolean published;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToMany(mappedBy = "posts")
    @EqualsAndHashCode.Exclude
    private Collection<Category> categories;

    @ManyToMany(mappedBy = "posts")
    @EqualsAndHashCode.Exclude
    private Collection<Tag> tags;
}
