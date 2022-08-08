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
string. Beware to not start the file name with a slash `/`.

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

# Accessing Spring Beans

To access spring BEANS you have to annotate your BEANs with the `@PlateBean` annotation.

```java
@Bean
@PlateBean
public SeasonService seasonService(){
        return new SeasonService();
        }
```

You can use them by using their name. For example for a text it would look like this:

```html
$${seasonService.getTheSeason()}
```

# Internationalization

For internationalization just create `message_LOCALE.properties` resource bundle within your `resources` directory.
For example:

```
src/main/resource/message_en.properties
src/main/resources/message_de.properties
```

# PLATE Syntax

For plate syntax look here: [PLATE documentation](https://github.com/voelza/plate).