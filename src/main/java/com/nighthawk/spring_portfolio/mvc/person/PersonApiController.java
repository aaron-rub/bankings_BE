package com.nighthawk.spring_portfolio.mvc.person;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/person")
public class PersonApiController {
    //     @Autowired
    // private JwtTokenUtil jwtGen;
    /*
    #### RESTful API ####
    Resource: https://spring.io/guides/gs/rest-service/
    */

    // Autowired enables Control to connect POJO Object through JPA
    @Autowired
    private PersonJpaRepository repository;

    @Autowired
    private PersonDetailsService personDetailsService;

    /*
    GET List of People
     */
    @GetMapping("/")
    public ResponseEntity<List<Person>> getPeople() {
        return new ResponseEntity<>( repository.findAllByOrderByNameAsc(), HttpStatus.OK);
    }

    /*
    GET individual Person using ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<Person> getPerson(@PathVariable long id) {
        Optional<Person> optional = repository.findById(id);
        if (optional.isPresent()) {  // Good ID
            Person person = optional.get();  // value from findByID
            return new ResponseEntity<>(person, HttpStatus.OK);  // OK HTTP response: status code, headers, and body
        }
        // Bad ID
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);       
    }

    @GetMapping("/stats/{id}")
    public ResponseEntity<Object> getStatsPerson(@PathVariable long id) {
        Optional<Person> optional = repository.findById(id);
        if (optional.isPresent()) {  // Good ID
            Person person = optional.get();  // value from findByID
            return new ResponseEntity<>(person.getStats(), HttpStatus.OK);  // OK HTTP response: status code, headers, and body
        }
        // Bad ID
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);       
    }

    /*
    DELETE individual Person using ID
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Person> deletePerson(@PathVariable long id) {
        Optional<Person> optional = repository.findById(id);
        if (optional.isPresent()) {  // Good ID
            Person person = optional.get();  // value from findByID
            repository.deleteById(id);  // value from findByID
            return new ResponseEntity<>(person, HttpStatus.OK);  // OK HTTP response: status code, headers, and body
        }
        // Bad ID
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST); 
    }

    /*
    POST Aa record by Requesting Parameters from URI
     */
    @PostMapping( "/post")
    public ResponseEntity<Object> postPerson(@RequestParam("email") String email,
                                             @RequestParam("password") String password,
                                             @RequestParam("name") String name) {
        // A person object WITHOUT ID will create a new record with default roles as student
        Person person = new Person(email, password, name);
        personDetailsService.save(person);
        return new ResponseEntity<>(email +" is created successfully", HttpStatus.CREATED);
    }

    /*
    The personSearch API looks across database for partial match to term (k,v) passed by RequestEntity body
     */
    @PostMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> personSearch(@RequestBody final Map<String,String> map) {
        // extract term from RequestEntity
        String term = (String) map.get("term");

        // JPA query to filter on term
        List<Person> list = repository.findByNameContainingIgnoreCaseOrEmailContainingIgnoreCase(term, term);

        // return resulting list and status, error checking should be added
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PostMapping(value = "/transfer/{id1}/{id2}/{amount}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> personSearch(@RequestBody Long id1, @RequestBody Long id2, @RequestBody int amount) {
        // locate the users
        Optional<Person> optional1 = repository.findById(id1);
        Optional<Person> optional2 = repository.findById(id2);
        if (optional1.isPresent() && optional2.isPresent()) {  // Good ID
            Person user1 = optional1.get();  // value from findByID
            Person user2 = optional2.get();
            user1.addBalance(-amount);
            user2.addBalance(amount);
            return new ResponseEntity<>(user2.getStats(), HttpStatus.OK);  // OK HTTP response: status code, headers, and body
        }
        // Bad ID
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST); 
    }

    @PostMapping(value = "/addAmount/{id}/{amount}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> addBalance(@RequestBody Long id, @RequestBody int amount) {
        // locate the users
        Optional<Person> optional = repository.findById(id);
        if (optional.isPresent()) {  // Good ID
            Person user = optional.get();
            user.addBalance(amount);
            return new ResponseEntity<>(user.getStats(), HttpStatus.OK);  // OK HTTP response: status code, headers, and body
        }
        // Bad ID
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST); 
}

    @PostMapping(value = "/addCatergory/{id}/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> addCatergory(@RequestBody Long id, @RequestBody String name) {
        // locate the users
        Optional<Person> optional = repository.findById(id);
        if (optional.isPresent()) {  // Good ID
            Person user = optional.get();
            user.addCatergory(name);
            return new ResponseEntity<>(user.getStats(), HttpStatus.OK);  // OK HTTP response: status code, headers, and body
        }
        // Bad ID
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST); 
} 

    @PostMapping(value = "/addAmountCatergory/{id}/{name}/{amount}", produces = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<Object> addCatergory(@RequestBody Long id, @RequestBody String name, @RequestBody int amount) {
            // locate the users
            Optional<Person> optional = repository.findById(id);
            if (optional.isPresent()) {  // Good ID
                Person user = optional.get();
                user.addMoneyToCatergory(name, amount);
                return new ResponseEntity<>(user.getStats(), HttpStatus.OK);  // OK HTTP response: status code, headers, and body
            }
            // Bad ID
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); 
    }}


