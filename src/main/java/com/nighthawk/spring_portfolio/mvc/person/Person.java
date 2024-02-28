package com.nighthawk.spring_portfolio.mvc.person;

import static jakarta.persistence.FetchType.EAGER;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import com.vladmihalcea.hibernate.type.json.JsonType;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

/*
Person is a POJO, Plain Old Java Object.
First set of annotations add functionality to POJO
--- @Setter @Getter @ToString @NoArgsConstructor @RequiredArgsConstructor
The last annotation connect to database
--- @Entity
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Convert(attributeName ="person", converter = JsonType.class)
public class Person {

    // automatic unique identifier for Person record
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    // email, password, roles are key attributes to login and authentication
    @NotEmpty
    @Size(min=5)
    @Column(unique=true)
    @Email
    private String email;

    @NotEmpty
    private String password;

    // @NonNull, etc placed in params of constructor: "@NonNull @Size(min = 2, max = 30, message = "Name (2 to 30 chars)") String name"
    @NonNull
    @Size(min = 2, max = 30, message = "Name (2 to 30 chars)")
    private String name;


    // To be implemented
    @ManyToMany(fetch = EAGER)
    private Collection<PersonRole> roles = new ArrayList<>();

    /* HashMap is used to store JSON for daily "stats"
    "stats": {
        "2022-11": {
            "income": 8000,
            "spending": 7000
        }
    }
    */
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private Map<String,Map<String, Integer>> stats = new HashMap<>(); 
    

    // Constructor used when building object from an API
    public Person(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
        Map<String, Integer> balance = new HashMap<>();
        balance.put("balance",0);
        this.stats.put("balance", balance);
        Map<String, Integer> catergories = new HashMap<>();
        this.stats.put("catergories", catergories);
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String,Map<String, Integer>> getStats() {
        return this.stats;
    }

    public void addBalance(int amount) {
        int current = this.stats.get("balance").get("balance");
        this.stats.get("balance").put("balance",current+amount);
    }

    public void addCatergory(String name) {
        this.stats.get("catergories").put(name,0);
    }

    public void addMoneyToCatergory(String name, int amount) {
        int current = this.stats.get("balance").get("balance");
        this.stats.get("balance").put("balance",current-amount);
        current = this.stats.get("catergories").get(name);
        this.stats.get("catergories").put(name,current+amount);
    }

    // Initialize static test data 
    public static Person[] init() {

        // basics of class construction
        Person p1 = new Person();
        p1.setName("Thomas Edison");
        p1.setEmail("toby@gmail.com");
        p1.setPassword("123Toby!");

        // Array definition and data initialization
        Person persons[] = {p1};
        return persons;
    }

    public static void main(String[] args) {
        // obtain Person from initializer
        Person persons[] = init();

        // iterate using "enhanced for loop"
        for( Person person : persons) {
            System.out.println(person);  // print object
        }
    }

}