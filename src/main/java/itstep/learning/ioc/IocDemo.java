package itstep.learning.ioc;

import com.google.inject.Inject;
import itstep.learning.services.hash.HashService;

public class IocDemo {

    private final HashService hashService;

    @Inject     //инжекторный конструктор надо пометить аннотацией
    public IocDemo(HashService hashService) {
        this.hashService = hashService;
    }

    public void run() {
        System.out.println("ioc demo");
        System.out.println(hashService.hash("123"));
    }

}
