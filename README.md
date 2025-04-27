## Explícación Actividad 1 

##### Explique las salidas de este proceso:

```java
Flux<Integer> numbers = Flux.range(1, 10)
    .map(i -> {
        if (i % 2 == 0) {
            throw new RuntimeException("Número par no permitido");
        }
        return i;
    })
    .onErrorResume(RuntimeException.class, error -> {
        log.error("Error: {}", error.getMessage());
        return Mono.just(0);
    })
    .filter(number -> number > 5)
    .flatMap(number -> {
        // Simular una operación asíncrona (por ejemplo, una llamada a una base de datos)
        return Mono.just(number * 2)
                .delayElements(Duration.ofMillis(100));
    })
    .subscribe(
        number -> System.out.println("Número procesado: " + number),
        error -> log.error("Error general: {}", error),
        () -> System.out.println("Completado")
    );
  ```


##### Explicación:
Vamos a desestructurar el código paso por paso para explicar su funcionamiento:

```java
Flux.range(1, 10)
```
Se inicializa un flujo de números del 1 al 10. Emite los números uno por uno.

```java
.map(i -> {
        if (i % 2 == 0) {
            throw new RuntimeException("Número par no permitido");
        }
        return i;
    })
```
El map se ecnarga de hacer una trasnformación al flujo, aplicandole una fúncion a cada número del flujo verificando
si el número es par. Si lo es, lanza una RuntimeException con el mensaje "Número par no permitido".
Si el número no es par, lo devuelve tal cual, sin cambios

```java
    .onErrorResume(RuntimeException.class, error -> {
        log.error("Error: {}", error.getMessage());
        return Mono.just(0);
    })
```
El método onErrorResume nos permite manejar las excepciones en el flujo, y este se activa si se lanza una excepción, 
como en el map cuando encontramos un número par anteriormente. En caso de que se lance una excepción se regista 
un mensaje de error en los logs, y se devuelve un valor por defecto 0. 


```java
    .filter(number -> number > 5)
```
filtra los números que son mayores a 5. Los números que cumplan la condición del filter serán descartados.

```java
    .flatMap(number -> {
        // Simular una operación asíncrona (por ejemplo, una llamada a una base de datos)
        return Mono.just(number * 2)
                .delayElements(Duration.ofMillis(100));
    })
```
flat map transforma cada número en un Mono (asíncrono). Este paso simula una operación que toma tiempo, como una llamada a una base de datos.
En el return lo que se hace es multiplicar el numero por 2 y generar un retraso de 100 ms entre los elementos retornados para simular el tiempo
que tomaría la operación asíncrona.

```java
    .subscribe(
        number -> System.out.println("Número procesado: " + number),
        error -> log.error("Error general: {}", error),
        () -> System.out.println("Completado")
    );
```

Finalmente, subscribe se suscribe al flujo, donde:
Imprime cada número procesado.
Maneja cualquier error general que pueda ocurrir.
Indica cuando el proceso se ha completado con un mensaje en consola.












