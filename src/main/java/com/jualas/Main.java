package com.jualas;

import dao.EntrenadorImpl;
import dao.PokemonImpl;
import entidades.Entrenador;
import entidades.Pokemon;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String... param) {
        System.out.println("Starting application...");

        Scanner scanner = new Scanner(System.in);
        int option = 0;

        do {
            System.out.println("\nMenu:");
            System.out.println("1. Mostrar Pokemon de un tipo específico");
            System.out.println("2. Actualizar entrenadores mediante fichero");
            System.out.println("3. Descargar datos en fichero");
            System.out.println("4. Borrar Pokemon mediante fichero");
            System.out.println("5. Borrar Pokemon");
            System.out.println("6. Salir");
            System.out.print("Elige una opción: ");

            try {
                option = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Por favor, introduce un número válido.");
                scanner.next(); // descarta la entrada incorrecta
                continue;
            }

            switch (option) {
                case 1:
                    try {
                        // Llamamos al método printPokemonTypes() para imprimir los tipos de Pokemon
                        PokemonImpl.printPokemonTypes();
                        // Solicitamos al usuario que introduzca el ID del tipo de Pokemon que quiere buscar
                        System.out.print("Introduce el ID del tipo de Pokemon que quieres buscar: ");
                        // Leemos el ID introducido por el usuario
                        int id = scanner.nextInt();
                        // Creamos un objeto PokemonImpl para interactuar con la base de datos
                        PokemonImpl pokemonImpl = new PokemonImpl();
                        // Obtenemos una lista de todos los tipos únicos de Pokemon en la base de datos
                        List<String> types = pokemonImpl.getUniqueTypes();
                        // Verificamos que el ID introducido por el usuario es válido
                        while (id < 1 || id > types.size()) {
                            // Si el ID no es válido, solicitamos al usuario que introduzca un ID válido
                            System.out.println("ID no válido. Por favor, introduce un ID válido: ");
                            id = scanner.nextInt();
                        }
                        // Obtenemos el tipo de Pokemon correspondiente al ID introducido por el usuario
                        String type = types.get(id - 1);
                        // Obtenemos una lista de todos los Pokemon de ese tipo en la base de datos
                        List<Pokemon> pokemons = pokemonImpl.getByType(type);
                        // Verificamos si la lista de Pokemon está vacía
                        if (pokemons.isEmpty()) {
                            // Si la lista está vacía, informamos al usuario de que no se encontraron Pokemon de ese tipo
                            System.out.println("No se encontraron Pokemon de tipo " + type);
                        } else {
                            // Si la lista no está vacía, imprimimos los Pokemon de ese tipo
                            System.out.println("Pokemon de tipo " + type + ":");
                            System.out.println("---------------------------------------------------------------------------------");
                            // Recorremos la lista de Pokemon y los imprimimos uno por uno
                            for (Pokemon pokemon : pokemons) {
                                System.out.println(pokemon);
                                System.out.println("---------------------------------------------------------------------------------");
                            }
                        }
                    } catch (SQLException e) {
                        // Si ocurre un error al interactuar con la base de datos, imprimimos el mensaje de error
                        System.out.println("Error al interactuar con la base de datos: " + e.getMessage());
                    } catch (InputMismatchException e) {
                        // Si ocurre un error al leer la entrada del usuario, imprimimos un mensaje de error y volvemos al menú
                        System.out.println("Por favor, introduce un número válido.");
                        scanner.next(); // descarta la entrada incorrecta
                    }
                    break;
                case 2:
                    // Solicitamos al usuario que introduzca la ruta del archivo
                    System.out.print("Introduce la ruta del archivo que contiene los datos de los entrenadores a actualizar: ");
                    try {
                        // Leemos la ruta del archivo introducida por el usuario
                        String filePath = scanner.next();
                        // Creamos un objeto Path a partir de la ruta del archivo
                        Path path = Paths.get(filePath);
                        // Verificamos si el archivo existe
                        if (!Files.exists(path)) {
                            // Si el archivo no existe, informamos al usuario y volvemos al menú
                            System.out.println("La ruta del archivo no es válida. Por favor, introduce una ruta válida.");
                            break;
                        }
                        // Si el archivo existe, leemos todas las líneas del archivo
                        List<String> lines = Files.readAllLines(path);
                        // Creamos un objeto EntrenadorImpl para actualizar los datos de los entrenadores
                        EntrenadorImpl entrenadorImpl = new EntrenadorImpl();
                        // Recorremos cada línea del archivo
                        for (String line : lines) {
                            // Dividimos la línea en partes utilizando el punto y coma como separador
                            String[] parts = line.split(";");
                            // Verificamos que la línea tiene al menos 3 partes (ID, nombre y ciudad del entrenador)
                            if (parts.length >= 3) {
                                // Convertimos el ID del entrenador a un número entero
                                int entrenadorId = Integer.parseInt(parts[0]);
                                // Eliminamos las comillas simples del nombre del entrenador
                                String nombre = parts[1].replace("'", "");
                                // Eliminamos las comillas simples y los saltos de línea de la ciudad del entrenador
                                String ciudad = parts[2].replace("'", "").replace("\n", "");
                                // Creamos un nuevo objeto Entrenador con los datos obtenidos
                                Entrenador entrenador = new Entrenador(entrenadorId, nombre, ciudad);
                                // Actualizamos los datos del entrenador en la base de datos
                                entrenadorImpl.update(entrenador, new String[]{nombre, ciudad});
                            }
                        }
                        // Informamos al usuario de que los datos de los entrenadores se han actualizado correctamente
                        System.out.println("Los datos de los entrenadores se han actualizado correctamente.");
                    } catch (IOException e) {
                        // Si ocurre un error al leer el archivo, informamos al usuario
                        System.out.println("No se pudo leer el archivo: " + e.getMessage());
                    } catch (NumberFormatException e) {
                        // Si ocurre un error al convertir el ID del entrenador a un número entero, informamos al usuario
                        System.out.println("El ID del entrenador debe ser un número.");
                    } catch (InputMismatchException e) {
                        // Si ocurre un error al leer la ruta del archivo introducida por el usuario, informamos al usuario
                        System.out.println("Por favor, introduce una ruta de archivo válida.");
                    }
                    break;
                case 3:
                    // Solicitamos al usuario que introduzca la ruta del archivo
                    System.out.print("Introduce la ruta del archivo donde se guardarán los datos: ");
                    try {
                        // Leemos la ruta del archivo introducida por el usuario
                        String filePath = scanner.next();
                        // Creamos un objeto Path a partir de la ruta del archivo
                        Path path = Paths.get(filePath);
                        // Verificamos si el archivo existe y se puede escribir en él
                        if (Files.exists(path) && !Files.isWritable(path)) {
                            // Si el archivo no existe o no se puede escribir en él, informamos al usuario y volvemos al menú
                            System.out.println("La ruta del archivo no es válida o no se puede escribir en ella. Por favor, introduce una ruta válida.");
                            break;
                        }
                        // Creamos un objeto PokemonImpl para interactuar con la base de datos
                        PokemonImpl pokemonImpl = new PokemonImpl();
                        // Obtenemos una lista de todos los Pokemon en la base de datos
                        List<Pokemon> pokemons = pokemonImpl.getAll();
                        // Verificamos si la lista de Pokemon está vacía
                        if (pokemons.isEmpty()) {
                            // Si la lista está vacía, informamos al usuario y volvemos al menú
                            System.out.println("No se encontraron Pokemon en la base de datos.");
                            break;
                        }
                        // Si la lista no está vacía, recorremos la lista de Pokemon y escribimos los datos de cada Pokemon en el archivo
                        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
                            for (Pokemon pokemon : pokemons) {
                                writer.write(pokemon.toString());
                                writer.newLine();
                            }
                        }
                        // Informamos al usuario de que los datos se han descargado correctamente
                        System.out.println("Los datos se han descargado correctamente en el archivo " + filePath);
                    } catch (IOException e) {
                        // Si ocurre un error al escribir en el archivo, informamos al usuario
                        System.out.println("No se pudo escribir en el archivo: " + e.getMessage());
                    } catch (InputMismatchException e) {
                        // Si ocurre un error al leer la ruta del archivo introducida por el usuario, informamos al usuario
                        System.out.println("Por favor, introduce una ruta de archivo válida.");
                    }
                    break;
                case 4:
                    // Solicitamos al usuario que introduzca la ruta del archivo
                    System.out.print("Introduce la ruta del archivo que contiene los IDs de los Pokemon a eliminar: ");
                    try {
                        // Leemos la ruta del archivo introducida por el usuario y eliminamos los espacios en blanco al principio y al final
                        String filePath = scanner.next().trim();
                        // Creamos un objeto Path a partir de la ruta del archivo
                        Path path = Paths.get(filePath);
                        // Verificamos si el archivo existe
                        if (!Files.exists(path)) {
                            // Si el archivo no existe, informamos al usuario y volvemos al menú
                            System.out.println("La ruta del archivo no es válida. Por favor, introduce una ruta válida.");
                            break;
                        }
                        // Si el archivo existe, leemos todas las líneas del archivo
                        List<String> lines = Files.readAllLines(path);
                        // Creamos un objeto PokemonImpl para interactuar con la base de datos
                        PokemonImpl pokemonImpl = new PokemonImpl();
                        // Recorremos cada línea del archivo
                        for (String line : lines) {
                            // Dividimos la línea en partes utilizando el punto y coma como separador
                            String[] parts = line.split(";");
                            // Convertimos la primera parte (el ID del Pokemon) a un número entero
                            int pokemonId = Integer.parseInt(parts[0]);
                            // Obtenemos el Pokemon con el ID obtenido
                            Pokemon pokemon = pokemonImpl.get(pokemonId);
                            // Si el Pokemon existe, lo eliminamos de la base de datos
                            if (pokemon != null) {
                                pokemonImpl.delete(pokemon);
                            }
                        }
                        // Informamos al usuario de que los Pokemon se han eliminado correctamente
                        System.out.println("Los Pokemon se han eliminado correctamente.");
                    } catch (IOException e) {
                        // Si ocurre un error al leer el archivo, informamos al usuario
                        System.out.println("No se pudo leer el archivo: " + e.getMessage());
                    } catch (NumberFormatException e) {
                        // Si ocurre un error al convertir el ID del Pokemon a un número entero, informamos al usuario
                        System.out.println("El ID del Pokemon debe ser un número.");
                    }
                    break;
                case 5:
                    try {
                        // Llamamos al método printPokemonTypes() para imprimir los tipos de Pokemon
                        PokemonImpl.printPokemonTypes();
                        // Solicitamos al usuario que introduzca el ID del tipo de Pokemon que quiere eliminar
                        System.out.print("Introduce el ID del tipo de Pokemon que quieres eliminar: ");
                        int typeId = scanner.nextInt();
                        // Creamos un objeto PokemonImpl para interactuar con la base de datos
                        PokemonImpl pokemonImpl = new PokemonImpl();
                        // Obtenemos una lista de todos los tipos únicos de Pokemon en la base de datos
                        List<String> types = pokemonImpl.getUniqueTypes();
                        // Obtenemos el tipo de Pokemon correspondiente al ID introducido por el usuario
                        String type = types.get(typeId - 1);
                        // Obtenemos una lista de todos los Pokemon de ese tipo en la base de datos
                        List<Pokemon> pokemons = pokemonImpl.getByType(type);
                        // Imprimimos los Pokemon de ese tipo
                        System.out.println("Pokemon de tipo " + type + ":");
                        for (int i = 0; i < pokemons.size(); i++) {
                            System.out.println((i + 1) + ". " + pokemons.get(i));
                        }
                        // Solicitamos al usuario que introduzca el ID del Pokemon que quiere eliminar
                        System.out.print("Introduce el ID del Pokemon que quieres eliminar: ");
                        int pokemonId = scanner.nextInt();
                        // Obtenemos el Pokemon con el ID introducido por el usuario
                        Pokemon pokemon = pokemonImpl.get(pokemonId);
                        // Verificamos si el Pokemon existe
                        if (pokemon == null) {
                            // Si el Pokemon no existe, informamos al usuario y volvemos al menú
                            System.out.println("No se encontró ningún Pokemon con el ID " + pokemonId);
                            break;
                        }
                        // Si el Pokemon existe, lo eliminamos de la base de datos
                        pokemonImpl.delete(pokemon);
                        // Informamos al usuario de que el Pokemon se ha eliminado correctamente
                        System.out.println("El Pokemon con el ID " + pokemonId + " se ha eliminado correctamente.");
                    } catch (InputMismatchException e) {
                        // Si ocurre un error al leer el ID del Pokemon introducido por el usuario, informamos al usuario
                        System.out.println("Por favor, introduce un número válido.");
                        scanner.next(); // descarta la entrada incorrecta
                    } catch (SQLException e) {
                        // Si ocurre un error al interactuar con la base de datos, imprimimos el mensaje de error
                        System.out.println("Error al interactuar con la base de datos: " + e.getMessage());
                    }
                    break;
                case 6:
                    System.out.println("Saliendo de la aplicación...");
                    break;
                default:
                    System.out.println("Opción no válida. Por favor, elige una opción del 1 al 6.");
                    break;
            }
        } while (option != 6);

        scanner.close();
    }
}