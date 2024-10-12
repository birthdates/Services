# Services
A simple Kotlin library to handle service-based applications.

## Adding to Your Project
To add this library to your project, you need to clone this repository and build the project using Gradle.

```bash
git clone https://github.com/birthdates/Services.git
gradle publishToMavenLocal
```

**NOTE:** *You must shade/shadow this library into your project as it is a standalone library.*

Afterward, you can include it within your project like so:

**Gradle**
```kotlin
repositories {
    mavenLocal()
}

dependencies {
    implementation("com.birthdates:services:1.0.0")
}
```

**Maven**
```xml
<dependency>
    <groupId>com.birthdates</groupId>
    <artifactId>services</artifactId>
    <version>1.0.0</version>
</dependency>
```

## How to Use
Let's use an example of a service that handles player logic.

### Creating a Service
To create a service like this, we first need to create an interface to map out the logic that this service will handle. Furthermore, we can add documentation to this interface without having to do so in its implementation.

**Java**
```java
public interface PlayerService extends Service {
    void movePlayer(int x, int y);
    void attackPlayer(Player target);
    void healPlayer(Player target);
}
```
**Kotlin**
```kotlin
interface PlayerService : Service {
    fun movePlayer(x: Int, y: Int)
    fun attackPlayer(target: Player)
    fun healPlayer(target: Player)
}
```

Note that the `Service` interface is a part of the library and is used to identify services. Furthermore, it comes with its own `load` and `unload` functions.

The `load` function should be used for logic when a service is loaded and constructed. Stray away from putting important logic within a constructor as you may end up calling another service or piece of code that trys to access your service which does not exist yet within the constructor. 

The `unload` function should be used for logic when a service is unloaded and deconstructed. This is useful for cleaning up resources or saving data. In your implementation/project using this library, you must call `Services#unload()` when your program/project is unloading and `Services#load()` when your program/project is loading.

Now that we have our interface, we can create an implementation of this interface.

**Java**
```java
@Register
public class CorePlayerService implements PlayerService {
    @Override
    public void load() {
    }
    
    @Override
    public void unload() {
    }
    
    @Override
    public void movePlayer(int x, int y) {
    }
    
    @Override
    public void attackPlayer(Player target) {
    }
    
    @Override
    public void healPlayer(Player target) {
    }
}
```

**Kotlin**
```kotlin
@Register
class CorePlayerService : PlayerService {
    override fun load() {
    }
    
    override fun unload() {
    }
    
    override fun movePlayer(x: Int, y: Int) {
    }
    
    override fun attackPlayer(target: Player) {
    }
    
    override fun healPlayer(target: Player) {
    }
}
```

The `@Register` annotation is used to register the service with the library. This annotation is required for the library to recognize the service.

### Dependencies
If your service depends on another service, you can simply annotate your service implementation with `@Depends` and pass the class(es) of the service you depend on.

For example:

**Java**
```java
@Register
@Depends(DatabaseService.class)
public class CorePlayerService implements PlayerService {
    // ...
}
```

**Kotlin**
```kotlin
@Register
@Depends(DatabaseService::class)
class CorePlayerService : PlayerService {
    // ...
}
```

### Accessing a Service
To access a service, you can use the `Services` class.
Using the examples above, we can access the `PlayerService` like so:

**Java**
```java
PlayerService playerService = Services.get(PlayerService.class);
```

**Kotlin**
```kotlin
val playerService = Services.get(PlayerService::class)
// or
val playerService: PlayerService = Services.fetch()
```