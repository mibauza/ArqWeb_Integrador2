package readerCSV;

import JPA.Carrera;
import JPA.Estudiante;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import servicio.CarreraService;
import servicio.EstudianteService;
import servicio.InscripcionService;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

public class DBInitializer {

    private final CarreraService carreraService;
    private final EstudianteService estudianteService;
    private final InscripcionService inscripcionService;

    public DBInitializer() {
        this.carreraService = new CarreraService();
        this.estudianteService = new EstudianteService();
        this.inscripcionService = new InscripcionService();
    }

    private Iterable<CSVRecord> getData(String archivo) throws IOException {
        String path = "src/main/java/resources/" + archivo; // O donde tengas los CSV
        Reader in = new FileReader(path);
        CSVParser csvParser = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(in);
        return csvParser.getRecords();
    }

    public void populateDB() {
        try {
            System.out.println("Populando base de datos...");

            cargarCarreras();
            cargarEstudiantes();
            cargarInscripciones();

            System.out.println("✅ Todos los datos fueron cargados exitosamente");

        } catch (Exception e) {
            System.err.println("Error al poblar base de datos: " + e.getMessage());
        }
    }

    private void cargarCarreras() throws IOException {
        System.out.println("Cargando carreras...");

        for (CSVRecord row : getData("carreras.csv")) {
            try {

                String nombreCarrera = row.get("carrera");
                int duracion = Integer.parseInt(row.get("duracion"));

                Carrera carrera = new Carrera();

                carrera.setNombre(nombreCarrera);
                carrera.setDuracion(duracion);

                carreraService.guardarCarrera(carrera);
            } catch (Exception e) {
                System.err.println("Error al procesar carrera: " + e.getMessage());
            }
        }
        System.out.println("✅ Carreras insertadas");
    }

    private void cargarEstudiantes() throws IOException {
        System.out.println("Cargando estudiantes...");

        for (CSVRecord row : getData("estudiantes.csv")) {
            try {
                String dni = row.get("DNI");
                String nombre = row.get("nombre");
                String apellido = row.get("apellido");
                int edad = Integer.parseInt(row.get("edad"));
                String genero = row.get("genero");
                String ciudad = row.get("ciudad");
                String libretaUniversitaria = row.get("LU");

                Estudiante estudiante = new Estudiante();
                estudiante.setNumeroDocumento(dni);
                estudiante.setNombre(nombre);
                estudiante.setApellido(apellido);
                estudiante.setEdad(edad);
                estudiante.setGenero(genero);
                estudiante.setCiudadResidencia(ciudad);
                estudiante.setNumeroLibreta(libretaUniversitaria);

                estudianteService.altaEstudiante(estudiante);
            } catch (Exception e) {
                System.err.println("Error al procesar estudiante: " + e.getMessage());
            }
        }
        System.out.println("✅ Estudiantes insertados");
    }

    private void cargarInscripciones() throws IOException {
        System.out.println("Cargando inscripciones...");

        for (CSVRecord row : getData("estudianteCarrera.csv")) {
            try {
                int idEstudiante = Integer.parseInt(row.get("id_estudiante"));
                int idCarrera = Integer.parseInt(row.get("id_carrera"));
                int anioInscripcion = Integer.parseInt(row.get("inscripcion"));
                // La columna "graduacion" y "antiguedad" por ahora no la usamos, salvo que quieras.

                // Primero, buscamos al estudiante por DNI
                Estudiante estudiante = estudianteService.buscarPorDNI(String.valueOf(idEstudiante));
                // Buscamos carrera por ID
                Carrera carrera = carreraService.buscarCarreraPorId(idCarrera);

                if (estudiante != null && carrera != null) {
                    inscripcionService.matricularEstudiante(estudiante, carrera, anioInscripcion);
                } else {
                    System.out.println("⚠️ Estudiante o Carrera no encontrados para inscripción. EstudianteID: " + idEstudiante + ", CarreraID: " + idCarrera);
                }

            } catch (Exception e) {
                System.err.println("Error al procesar inscripción: " + e.getMessage());
            }
        }
        System.out.println("✅ Inscripciones insertadas");
    }

}
