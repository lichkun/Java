package itstep.learning.oop;

import itstep.learning.oop.annotations.Product;
import itstep.learning.oop.annotations.Required;
import netscape.javascript.JSObject;

import java.util.Locale;

@Product
public class Bus
        extends Vehicle
        implements LargeSized {
    public Bus(String name, int capacity) {
        super.setName(name);
        this.setCapacity(capacity);
    }

    @Override
    public String getInfo() {
        return String.format(
                Locale.ROOT,
                "Bus name: %s, capacity: %d",
                super.getName(),
                this.getCapacity()
        );
    }

    public Bus() {
    }

    @Required("seats")
    private int capacity;

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }


}
