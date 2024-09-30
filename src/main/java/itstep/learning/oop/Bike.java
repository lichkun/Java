package itstep.learning.oop;

import itstep.learning.oop.annotations.Product;
import itstep.learning.oop.annotations.Required;

import java.util.Locale;

@Product
public class Bike extends Vehicle implements Trailer {
    @Required
    String type;

    public Bike(String name, String type) {
        this.setType(type);
        super.setName(name);
    }

    public Bike() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setName(String name) {

    }

    @Override
    public String getInfo() {
        return String.format(Locale.ROOT,
                "Bike name: '%s', type '%s'",
                this.getName(),
                this.getType());
    }

    @Override
    public String trailerInfo() {
        return "Cradle trailer";
    }
}
