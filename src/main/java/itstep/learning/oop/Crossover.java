package itstep.learning.oop;

import itstep.learning.oop.annotations.Product;
import itstep.learning.oop.annotations.Required;

import java.util.Locale;

@Product
public class Crossover
        extends Vehicle
        implements TypeOfDrive {
    public Crossover(String name, String typeOfDrive) {
        super(name);
        this.setTypeOfDrive(typeOfDrive);
    }

    public Crossover() {
    }

    @Required("crossoverTypeOfDrive")
    public String typeOfDrive;

    public String getTypeOfDrive() {
        return typeOfDrive;
    }

    public void setTypeOfDrive(String typeOfDrive) {
        this.typeOfDrive = typeOfDrive;
    }

    @Override
    public String getInfo() {
        return String.format(
                Locale.ROOT,
                "Crossover name: %s, type of drive: %s",
                super.getName(),
                this.typeOfDrive()
        );
    }

    @Override
    public String typeOfDrive() {
        return "4 wheel drive";
    }
}
