package fr.java.spring.ioc.version2.webapp;

import fr.java.spring.ioc.common.model.DataGenerator;
import fr.java.spring.ioc.common.model.Person;
import fr.java.spring.ioc.version2.summer.context.ApplicationContext;
import fr.java.spring.ioc.version2.webapp.service.PersonService;
import fr.java.spring.ioc.version2.webapp.service.PersonServiceImpl;

import java.util.UUID;

public class App2 {

    public void exec() {

        Class<?> appToConsider = getClass();
        final ApplicationContext applicationContext = new ApplicationContext(appToConsider);
        final PersonService personService = new PersonServiceImpl();
        // PersonServiceImpl bean = applicationContext.getBean(PersonServiceImpl.class);

        Person person = DataGenerator.createPerson();
        UUID saved = personService.save(person);
        personService.findById(saved);
    }

    public static void main(String[] args) {
        App2 app = new App2();
        app.exec();
    }
}
