package readerCSV;

import JPA.Carrera;
import JPA.Estudiante;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import servicio.CarreraService;
import servicio.EstudianteService;
import servicio.InscripcionService;

import java.io.*;
import java.util.Arrays;
import java.util.List;

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
        List<String> posiblePaths = Arrays.asList(
                "src/main/java/resources/" + archivo,
                "src/main/resources/" + archivo,
                "resources/" + archivo,
                archivo
        );

        File file = null;
        for (String path : posiblePaths) {
            file = new File(path);
            if (file.exists()) {
                System.out.println("✅ Archivo encontrado en: " + file.getAbsolutePath());
                break;
            }
        }

        if (file == null || !file.exists()) {
            System.err.println("❌ No se pudo encontrar el archivo: " + archivo);
            System.err.println("Rutas probadas:");
            for (String path : posiblePaths) {
                System.err.println("  - " + new File(path).getAbsolutePath());
            }
            throw new FileNotFoundException("No se pudo encontrar el archivo: " + archivo);
        }

        try {
            // Verificar si el archivo está vacío
            if (file.length() == 0) {
                throw new IOException("El archivo está vacío: " + file.getAbsolutePath());
            }

            // Verificar contenido del archivo (primeras líneas)
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                System.out.println("Primeras líneas del archivo " + archivo + ":");
                String line;
                int lineCount = 0;
                while ((line = br.readLine()) != null && lineCount < 3) {
                    System.out.println("  " + line);
                    lineCount++;
                }
            }

            // Leer el CSV
            Reader in = new FileReader(file);
            try {
                CSVParser csvParser = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(in);
                System.out.println("Columnas encontradas en el CSV: " + csvParser.getHeaderNames());
                return csvParser.getRecords();
            } catch (Exception e) {
                throw new IOException("Error al parsear el CSV: " + e.getMessage(), e);
            }
        } catch (IOException e) {
            System.err.println("❌ Error al leer el archivo: " + e.getMessage());
            throw e;
        }
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

        try {
            Iterable<CSVRecord> records = getData("carreras.csv");

            // Imprime todas las columnas para diagnóstico
            CSVParser parser = records.iterator().next().getParser();
            System.out.println("Columnas en el CSV: " + parser.getHeaderMap().keySet());

            for (CSVRecord row : records) {
                try {
                    // Imprime la fila actual para diagnóstico
                    System.out.println("Procesando fila: " + row.toString());

                    // Obtén los valores de manera segura, proporcionando mensajes claros si faltan
                    String nombreCarrera;
                    if (row.isMapped("carrera")) {
                        nombreCarrera = row.get("carrera");
                    } else {
                        throw new IllegalArgumentException("La columna 'carrera' no existe en el CSV");
                    }

                    int duracion;
                    if (row.isMapped("duracion")) {
                        String duracionStr = row.get("duracion");
                        if (duracionStr == null || duracionStr.trim().isEmpty()) {
                            System.out.println("⚠️ Valor de duración vacío para " + nombreCarrera + ", usando valor por defecto: 5");
                            duracion = 5;
                        } else {
                            try {
                                duracion = Integer.parseInt(duracionStr.trim());
                            } catch (NumberFormatException e) {
                                System.out.println("⚠️ Valor de duración no válido para " + nombreCarrera + ": '" + duracionStr + "', usando valor por defecto: 5");
                                duracion = 5;
                            }
                        }
                    } else {
                        System.out.println("⚠️ La columna 'duracion' no existe en el CSV, usando valor por defecto: 5");
                        duracion = 5;
                    }

                    // Obtener el id_carrera para usarlo como código si está disponible
                    String codigo = null;
                    if (row.isMapped("id_carrera")) {
                        codigo = row.get("id_carrera");
                        System.out.println("Usando id_carrera como código: " + codigo);
                    }

                    // Crear y guardar la carrera
                    Carrera carrera = new Carrera();
                    carrera.setNombre(nombreCarrera);
                    carrera.setDuracion(duracion);
                    if (codigo != null && !codigo.trim().isEmpty()) {
                        carrera.setCodigo(codigo);
                    }

                    System.out.println("Guardando carrera: " + carrera.getNombre() + ", duración: " + carrera.getDuracion() +
                            (codigo != null ? ", código: " + codigo : ""));
                    carreraService.guardarCarrera(carrera);

                } catch (Exception e) {
                    System.err.println("Error al procesar carrera: " + e.getMessage());
                    e.printStackTrace();
                }
            }
            System.out.println("✅ Carreras insertadas");
        } catch (Exception e) {
            System.err.println("❌ Error al cargar carreras: " + e.getMessage());
            e.printStackTrace();
            throw e; // Re-lanzar la excepción para que se maneje en el método de nivel superior
        }
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
