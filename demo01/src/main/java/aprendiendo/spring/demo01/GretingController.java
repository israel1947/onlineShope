package aprendiendo.spring.demo01;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="greting")

public class GretingController {
    
    private final AtomicLong counter = new AtomicLong();
    private static final String template = "Hello %s";

    @GetMapping
    public Greting greting(@RequestParam(value="name", defaultValue="World" ) String name) {
        return new Greting(counter.incrementAndGet(), String.format(template, name));
    }
}
