package cn.zsd.model;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

/**
 * Created by Da on 2017/2/19.
 */
@Entity
@Table(name = "comment", schema = "blog", catalog = "")
public class CommentEntity {
    private int id;
    private String content;
    private Date pubDate;
    private BlogEntity blogByBlogId;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "content", nullable = true, length = 255)
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Basic
    @Column(name = "pub_date", nullable = false)
    public Date getPubDate() {
        return pubDate;
    }

    public void setPubDate(Date pubDate) {
        this.pubDate = pubDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CommentEntity that = (CommentEntity) o;

        if (id != that.id) return false;
        if (content != null ? !content.equals(that.content) : that.content != null) return false;
        if (pubDate != null ? !pubDate.equals(that.pubDate) : that.pubDate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + (pubDate != null ? pubDate.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "blog_id", referencedColumnName = "id", nullable = false)
    public BlogEntity getBlogByBlogId() {
        return blogByBlogId;
    }

    public void setBlogByBlogId(BlogEntity blogByBlogId) {
        this.blogByBlogId = blogByBlogId;
    }
}
