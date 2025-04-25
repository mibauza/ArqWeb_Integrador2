import DTO.ReporteCarreraDTO;
import JPA.Carrera;
import JPA.Estudiante;
import repository.EntityManagerFactory;
import service.CarreraService;
import service.EstudianteService;
import service.InscripcionService;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        EntityManagerFactory.init();

        CarreraService carreraService = new CarreraService();
        EstudianteService estudianteService = new EstudianteService();
        InscripcionService inscripcionService = new InscripcionService();

        try {
            int opcion;
            do {
                System.out.println("\n===== MENÚ PRINCIPAL =====");
                System.out.println("1. Alta de carrera");
                System.out.println("2. Alta de estudiante");
                System.out.println("3. Matricular estudiante");
                System.out.println("4. Listar estudiantes ordenados por apellido");
                System.out.println("5. Buscar estudiante por libreta");
                System.out.println("6. Buscar estudiantes por género");
                System.out.println("7. Buscar estudiantes por carrera y ciudad");
                System.out.println("8. Generar reporte de carreras");
                System.out.println("0. Salir");
                System.out.print("Seleccione una opción: ");
                opcion = Integer.parseInt(scanner.nextLine());

                switch (opcion) {
                    case 1 -> {
                        System.out.print("Ingrese nombre de la carrera: ");
                        String nombre = scanner.nextLine();
                        Carrera carrera = carreraService.crearCarrera(nombre);
                        System.out.println("✅ Carrera creada: " + carrera);
                    }
                    case 2 -> {
                        System.out.print("Nombre: ");
                        String nombre = scanner.nextLine();
                        System.out.print("Apellido: ");
                        String apellido = scanner.nextLine();
                        System.out.print("Edad: ");
                        int edad = Integer.parseInt(scanner.nextLine());
                        System.out.print("Género (M/F): ");
                        String genero = scanner.nextLine();
                        System.out.print("Número de documento: ");
                        String documento = scanner.nextLine();
                        System.out.print("Ciudad de residencia: ");
                        String ciudad = scanner.nextLine();

                        Estudiante estudiante = estudianteService.crearEstudiante(nombre, apellido, edad, genero, documento, ciudad);
                        System.out.println("✅ Estudiante creado: " + estudiante);
                    }
                    case 3 -> {
                        System.out.print("ID del estudiante: ");
                        Long idEstudiante = Long.parseLong(scanner.nextLine());
                        System.out.print("ID de la carrera: ");
                        Long idCarrera = Long.parseLong(scanner.nextLine());
                        System.out.print("Año de inscripción: ");
                        int anio = Integer.parseInt(scanner.nextLine());

                        inscripcionService.matricularEstudiante(idEstudiante, idCarrera, anio);
                        System.out.println("✅ Estudiante matriculado.");
                    }
                    case 4 -> {
                        System.out.println("📋 Estudiantes ordenados por apellido:");
                        estudianteService.getEstudiantesOrdenados("apellido").forEach(System.out::println);
                    }
                    case 5 -> {
                        System.out.print("Ingrese número de libreta: ");
                        String libreta = scanner.nextLine();
                        Estudiante encontrado = estudianteService.buscarPorLibreta(libreta);
                        System.out.println(encontrado != null ? encontrado : "❌ Estudiante no encontrado.");
                    }
                    case 6 -> {
                        System.out.print("Ingrese género (M/F): ");
                        String genero = scanner.nextLine();
                        estudianteService.buscarPorGenero(genero).forEach(System.out::println);
                    }
                    case 7 -> {
                        System.out.print("ID de la carrera: ");
                        Long idCarrera = Long.parseLong(scanner.nextLine());
                        System.out.print("Ciudad: ");
                        String ciudad = scanner.nextLine();
                        estudianteService.buscarPorCarreraYCiudad(idCarrera, ciudad).forEach(System.out::println);
                    }
                    case 8 -> {
                        System.out.println("📈 Reporte de carreras:");
                        List<ReporteCarreraDTO> reporte = inscripcionService.generarReporteCarreras();
                        reporte.forEach(System.out::println);
                    }
                    case 0 -> System.out.println("👋 Saliendo del programa...");
                    default -> System.out.println("❌ Opción inválida. Intente nuevamente.");
                }

            } while (opcion != 0);

        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            EntityManagerFactory.close();
            scanner.close();
        }
    }
}
