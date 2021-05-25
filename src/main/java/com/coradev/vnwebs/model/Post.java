package com.coradev.vnwebs.model;

import lombok.*;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;

@Entity
@Table(name = "POST")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "title", columnDefinition = "TEXT")
    private String title;

    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    @Column(name = "create_date")
    private Date createdDate;

    @Column(name = "cover_image", columnDefinition = "TEXT")
    private String coverImage;

    @Column(name = "published")
    private boolean published;

    @ManyToOne
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToMany(mappedBy = "posts")
    @EqualsAndHashCode.Exclude
    private Collection<Category> categories;

    @ManyToMany(mappedBy = "posts")
    @EqualsAndHashCode.Exclude
    private Collection<Tag> tags;

    public Post(String title, String content, Date createdDate, String coverImage, boolean published, User user, Collection<Category> categories, Collection<Tag> tags) {
        this.title = title;
        this.content = content;
        this.createdDate = createdDate;
        this.coverImage = coverImage;
        this.published = published;
        this.user = user;
        this.categories = categories;
        this.tags = tags;
    }

    public Post(String title, String content, Date createdDate, String coverImage, boolean published, User user) {
        this.title = title;
        this.content = content;
        this.createdDate = createdDate;
        this.coverImage = coverImage;
        this.published = published;
        this.user = user;
    }
}
