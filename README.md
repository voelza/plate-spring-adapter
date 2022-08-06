# plate-spring-adapter

Spring adapter for PLATE templating framework.

## Setup:

- At least Java 17
- Enable Spring build-info goal in your pom.xml

```xml

<plugin>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-maven-plugin</artifactId>
    <executions>
        <execution>
            <id>build-info</id>
            <goals>
                <goal>build-info</goal>
            </goals>
        </execution>
    </executions>
</plugin>
```

## Usage:

In your `@Controller` classes just return the file name of your PLATE view within your `templates` directory as a
string.

```java

@Controller
public class HomeController {

    @GetMapping("/home")
    public String home(final Model model) {
        model.addAttribute("title", "Website Title");
        return "Test.html";
    }
}
```

If you want to stream your content to the client (e.g. if your website is very big), you can add `stream:` as a prefix
before your view name to enable HTML streaming.

```java

@Controller
public class HomeController {

    @GetMapping("/home")
    public String home(final Model model) {
        model.addAttribute("title", "Website Title");
        return "stream:Test.html";
    }
}
```