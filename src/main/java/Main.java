import JPA.Carrera;
import JPA.Estudiante;
import readerCSV.DBInitializer;
import servicio.CarreraService;
import servicio.EstudianteService;
import servicio.InscripcionService;
import servicio.ReporteService;
import DTO.CarreraDTO;
import DTO.ReporteCarreraDTO;
import repository.EntityManagerFactory;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory.init(); // Inicializa la conexión JPA

        CarreraService carreraService = new CarreraService();
        EstudianteService estudianteService = new EstudianteService();
        InscripcionService inscripcionService = new InscripcionService();
        ReporteService reporteService = new ReporteService();

        Scanner scanner = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("\n----- MENÚ -----");
            System.out.println("1. Alta de estudiante");
            System.out.println("2. Alta de carrera");
            System.out.println("3. Inscribir estudiante en carrera(buscar por libreta)");
            System.out.println("4. Listar estudiantes ordenados");
            System.out.println("5. Buscar estudiante por libreta");
            System.out.println("6. Buscar estudiantes por género");
            System.out.println("7. Buscar estudiantes por carrera y ciudad");
            System.out.println("8. Mostrar carreras con cantidad de inscriptos");
            System.out.println("9. Generar reporte de carreras");
            System.out.println("10. Inicializar Base de Datos (drop, create, cargar CSV)");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine(); // Limpia buffer

            switch (opcion) {
                case 10:
                    inicializarBaseDeDatos();
                    break;

                case 1:
                    Estudiante estudiante = new Estudiante();
                    System.out.print("Nombre: ");
                    estudiante.setNombre(scanner.nextLine());
                    System.out.print("Apellido: ");
                    estudiante.setApellido(scanner.nextLine());
                    System.out.print("Edad: ");
                    estudiante.setEdad(scanner.nextInt());
                    scanner.nextLine();
                    System.out.print("Género: ");
                    estudiante.setGenero(scanner.nextLine());
                    System.out.print("Número de documento: ");
                    estudiante.setNumeroDocumento(scanner.nextLine());
                    System.out.print("Ciudad de residencia: ");
                    estudiante.setCiudadResidencia(scanner.nextLine());
                    System.out.print("Número de libreta universitaria: ");
                    estudiante.setNumeroLibreta(scanner.nextLine());

                    estudianteService.altaEstudiante(estudiante);
                    System.out.println("Estudiante dado de alta.");
                    break;

                case 2:
                    Carrera carrera = new Carrera();
                    System.out.print("Nombre de la carrera: ");
                    carrera.setNombre(scanner.nextLine());
                    carreraService.guardarCarrera(carrera);
                    System.out.println("Carrera guardada.");
                    break;

                case 3:
                    System.out.print("Número de libreta del estudiante: ");
                    String numeroLibreta = scanner.nextLine();
                    System.out.print("ID de la carrera: ");
                    Long idCarrera = scanner.nextLong();
                    System.out.print("Año de inscripción: ");
                    int anio = scanner.nextInt();
                    scanner.nextLine(); // Limpiar buffer

                    Estudiante est = estudianteService.buscarPorLibreta(numeroLibreta);
                    Carrera car = carreraService.buscarCarreraPorId(idCarrera.intValue());
                    if (est != null && car != null) {
                        inscripcionService.matricularEstudiante(est, car, anio);
                        System.out.println("Estudiante inscripto en la carrera.");
                    } else {
                        System.out.println("Estudiante o carrera no encontrada.");
                    }
                    break;


                case 4:
                    System.out.print("Orden (nombre/apellido): ");
                    String orden = scanner.nextLine();
                    List<Estudiante> estudiantesOrdenados = estudianteService.listarEstudiantes(orden);
                    estudiantesOrdenados.forEach(System.out::println);
                    break;

                case 5:
                    System.out.print("Número de libreta: ");
                    String libreta = scanner.nextLine();
                    Estudiante estBuscado = estudianteService.buscarPorLibreta(libreta);
                    System.out.println(estBuscado != null ? estBuscado : "No encontrado.");
                    break;

                case 6:
                    System.out.print("Género: ");
                    String genero = scanner.nextLine();
                    List<Estudiante> estudiantesGenero = estudianteService.buscarPorGenero(genero);
                    estudiantesGenero.forEach(System.out::println);
                    break;

                case 7:
                    System.out.print("ID de carrera: ");
                    int idCarreraBusqueda = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Ciudad: ");
                    String ciudad = scanner.nextLine();
                    List<Estudiante> estudiantesCarreraCiudad = estudianteService.buscarPorCarreraYCiudad(idCarreraBusqueda, ciudad);
                    estudiantesCarreraCiudad.forEach(System.out::println);
                    break;

                case 8:
                    List<CarreraDTO> carreras = carreraService.carrerasConEstudiantes();
                    carreras.forEach(System.out::println);
                    break;

                case 9:
                    List<ReporteCarreraDTO> reportes = reporteService.generarReporteCarreras();
                    reportes.forEach(System.out::println);
                    break;

                case 0:
                    System.out.println("Saliendo...");
                    break;

                default:
                    System.out.println("Opción no válida.");
            }

        } while (opcion != 0);

        scanner.close();
        EntityManagerFactory.close(); // Cierra la conexión JPA
    }
    private static void inicializarBaseDeDatos() {
        System.out.println("🧹 Eliminando datos anteriores...");
        borrarDatosAnteriores();

        System.out.println("📥 Cargando datos desde archivos CSV...");
        DBInitializer dbInitializer = new DBInitializer();
        dbInitializer.populateDB();

        System.out.println("✅ Base de datos inicializada correctamente.");
    }

    // Método para borrar todos los datos antes de cargar los CSV
    private static void borrarDatosAnteriores() {
        EntityManagerFactory.init(); // Asegurar que el EntityManager está listo
        EstudianteService estudianteService = new EstudianteService();
        CarreraService carreraService = new CarreraService();
        InscripcionService inscripcionService = new InscripcionService();

        inscripcionService.borrarTodasLasInscripciones(); // Hay que agregar este método en InscripcionService
        estudianteService.borrarTodosLosEstudiantes(); // Hay que agregar este método en EstudianteService
        carreraService.borrarTodasLasCarreras(); // Hay que agregar este método en CarreraService

        System.out.println("🗑️ Base de datos limpia. Lista para cargar nueva información.");
    }

}
