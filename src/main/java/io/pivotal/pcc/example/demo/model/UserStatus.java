package io.pivotal.pcc.example.demo.model;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.gemfire.mapping.annotation.Region;

import java.util.ArrayList;
import java.util.List;

@Region("UserStatus")
@EqualsAndHashCode(of = "id")
@ToString()
public class UserStatus {

  @Id
  private final Integer id;

  private List<String> docsRead;
  private int stars;

  public int getId(){
  return this.id;
  }

  public List<String> getDocsRead(){
  return this.docsRead;
  }
  public int getStars(){
  return this.stars;
  }
  public int getCountDocsRead(){
  return this.docsRead.size();
  }

  public UserStatus(int id){
  this.id=id;
  this.docsRead=new ArrayList<String>();
  this.stars=0;
  }

  public void addNewDoc(String newDocRead){
  this.docsRead.add(newDocRead);
  }
  public void addStar(){
  this.stars=this.stars+1;
  }
}
