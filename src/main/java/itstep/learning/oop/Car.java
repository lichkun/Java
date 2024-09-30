package itstep.learning.oop;

import itstep.learning.oop.annotations.Product;
import itstep.learning.oop.annotations.Required;

import java.util.Locale;

@Product
public class Car
        extends Vehicle
        implements Speed {
    @Required("maxSpeed")
    public int maxSpeed;
    @Required
    public String carBody;

    public String getCarBody() {
        return carBody;
    }

    public void setCarBody(String carBody) {
        this.carBody = carBody;
    }

    public void setMaxSpeed(int maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public Car() {
    }

    public Car(String name, int maxSpeed, String carBody) {
        super(name);
        this.setMaxSpeed(maxSpeed);
        this.setCarBody(carBody);
    }

    @Override
    public String getInfo() {
        return String.format(
                Locale.ROOT,
                "Car name: '%s', car speed: '%d'",
                super.getName(),
                this.getMaxSpeed()
        );
    }

    @Override
    public int getMaxSpeed() {
        return maxSpeed;
    }
}
