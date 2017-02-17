package todobackend.model;

import static com.google.common.base.Strings.isNullOrEmpty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter @Setter
@ToString
public class Todo {
  
  @Id @GeneratedValue
  private Long id;
  
  @Column(name = "TITLE", length = 255)
  private String title;
  
  private boolean completed = false;
  
  //Order is a H2's reserved word
  @Column(name = "TASK_ORDER")
  private int order;
  
  @Transient
  private String url =  "";
  
  public Todo update(Todo todo) {
    this.title = isNullOrEmpty(todo.getTitle()) ? title : todo.getTitle();
    this.completed = (completed == todo.isCompleted()) ? completed : todo.isCompleted();
    this.order = (order == todo.getOrder()) ? order : todo.getOrder();
    return this;
  }

  public String createUrl(String server) {
    this.url = String.format("%s/%s/%s", server, "todos" , id);
    return url;
  }
  
}