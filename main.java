// Project 3
//demetri bichara
//Nov 17, 23
// Herd Immunity Program

// setting classes up
import java.util.*;
import doodlepad.*; 
import javax.swing.SwingUtilities;

public class Project3 {
    public static void main(String[] args) {
        Herd herd = new Herd(2500, 25);
        herd.reset(0.8); // Increase this value closer to 1.0 to find the minimum
        // population fraction required to achieve herd immunity.
        SwingUtilities.invokeLater(new Runnable() {
    public void run() {
        herd.redraw(); // Ensure interface is redrawn only after setup is complete.
    }
    });
}
}
class Person extends Oval {  //  model of a single individual in a population.
    private Herd herd; 
    private int status; 
    public Person(double x, double y, Herd herd) {  // Person Object
        super(x,y, 10, 10); // Oval dimensions / location 
        this.herd = herd; 
        this.status = 0; 

    }
    public void reset() {
        this.status = 0;     // Reset Person status. Set status to susceptible (e.g. 0) and fill color (e.g. white).
        this.setFillColor(255, 255, 255);

    }
    public void protect() {  // Protect Person. Set status to protected (e.g. 2) and fill color (e.g. green).
        status = 2;
        this.setFillColor(0, 255, 0);
    }
    public boolean expose(){  // Expose a Person to the disease and infect only if susceptible.
// If susceptible, set new status to infected, set fill color (e.g. red), and return true
// Otherwise (if not susceptible), return false.
    
        if (this.status == 0) {
            status = 1;
            this.setFillColor(255, 0, 0);
            return true;
        }
        else {
            return false;
        }
    }
    public double distance(Person other) { // Compute and return distance between this Person and the Person other (in pixels).
        return Math.sqrt((Math.pow(this.x - other.x, 2) + Math.pow(this.y - other.y, 2)));

   }
    public boolean isInfected(){ // // Return true if this person is infected. Return false if not infected.
        return status == 1;
    
    }
    public void onMousePressed(double x, double y, int button) { // // Override Oval event handler method and start Herd simulation with this Person.
        herd.simulate(this); // // herd variable references the saved Herd object
}
}
//  Herd object containing npeople Person objects with
// disease transmission occurring when people are within cutoff distance.
class Herd extends Pad{
    private Random random;
    private int cutoff;
    private double x, y;
    private Person[] people;
    public Herd(int npeople, int cutoff){
        super("Herd Immunity", 600, 600);
        this.cutoff = cutoff;
        this.random = new Random();
        people = new Person[npeople];
        
    
        for (int i = 0; i < npeople; i++) {  // New person(s) across window
            double  x = random.nextDouble() * 600;
            double y = random.nextDouble() * 600;
            people[i] = new Person(x, y, this);
        }
    }
    public void reset(double prob) { // // Reset and protect Persons with probability prob, where prob is in [0.0, 1.0)
        for (Person i: people){  
            i.reset();
            double  x = random.nextDouble();
            if (x < prob ) {
                i.protect();
            }
        }
    }
    public void simulate(Person zero) {
        List<Person> infected = new ArrayList<>(); // Maintain a List of infected spreaders
        if ( zero.expose() ) { // If person zero becomes newly infected,
            infected.add(zero); // add the person to the infected list.
        }
        while( infected.size() > 0) { // While there remains any infected people,
            Person p = infected.remove(0); // remove the first person from the list.
        for (Person q : people) { // Iterate over all people in the population.
            double dist = p.distance(q); // Compute distance between the two people.
        if (dist < this.cutoff) { // If that distance is within the cutoff,
        // expose that person to the disease.
            if ( q.expose() ) { // If that person becomes newly infected,
                infected.add(q); // add that person to the infected list.
}
}
}
}

}
}