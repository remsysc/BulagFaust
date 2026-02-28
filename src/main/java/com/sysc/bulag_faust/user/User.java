package com.sysc.bulag_faust.user;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

import com.sysc.bulag_faust.post.entity.Post;
import com.sysc.bulag_faust.role.Role;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Entity
@Builder(toBuilder = true)
@Accessors(chain = true)
@Getter
@Setter(AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(nullable = false)
  private UUID id;

  @Column(nullable = false, unique = true)
  private String username;

  private String password;

  @Column(nullable = false, unique = true)
  private String email;

  // look at post class at look for "author"
  @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, // whatever happens to this author, all related posts must
                                                             // be affected as well.
      orphanRemoval = true // mainly for collections, if they are considerd disassociates then will be
                           // deleted
  )
  @Builder.Default
  private List<Post> post = new ArrayList<>();

  @Column(updatable = false)
  private LocalDateTime createdAt;

  // TODO: add roles
  //
  @ManyToMany(fetch = FetchType.EAGER, cascade = {
      CascadeType.DETACH,
      CascadeType.MERGE,
      CascadeType.PERSIST,
      CascadeType.REFRESH
  })
  @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
  @Builder.Default
  private Collection<Role> roles = new HashSet<>();

  @PrePersist
  protected void onCreate() {
    this.createdAt = LocalDateTime.now();

  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    User other = (User) obj;
    if (id == null) {
      if (other.id != null)
        return false;
    } else if (!id.equals(other.id))
      return false;
    return true;
  }

}
