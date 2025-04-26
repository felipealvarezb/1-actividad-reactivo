## Explícación Actividad 1 

##### Explique las salidas de este proceso:

```
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
El flujo comienza inicializando una variable de tipo Flux<Integer> donde se le asigna el valor de un Flux que contiene una lista de números del 1 al 10. Luego se utiliza el método map
para ejecutar una función sobre cada numero de la lista. El map lo que hace es verificar si el numero de la lista es divisible por 2, en caso no ser así lanza un RuntimeException, en caso
de si ser divisible por 2 retorna el número. Si se lanzó la excepción




