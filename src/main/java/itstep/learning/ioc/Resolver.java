package itstep.learning.ioc;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class Resolver {
    public Injector getInjector(){
        return Guice.createInjector(
                new ServiceModule()
        );
    }
}
/*
Resolver - класс, задача которого
 */
