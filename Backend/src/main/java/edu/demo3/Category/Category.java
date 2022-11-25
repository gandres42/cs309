package edu.demo3.Category;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import edu.demo3.Club.*;




@Data
@Entity
@EqualsAndHashCode(exclude = "clubs")
public class Category {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "category_clubs") 
    Set<Club> clubs = new HashSet<Club>();

    @Column(unique=true)
    private String name;

    public Category(String name)
    {
        this.name = name;
    }

    public Category(){}
}