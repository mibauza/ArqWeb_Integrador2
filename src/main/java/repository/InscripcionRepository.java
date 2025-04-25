package repository;

import DTO.EstadisticasPorAnioDTO;
import DTO.ReporteCarreraDTO;
import JPA.Carrera;
import JPA.Estudiante;
import JPA.Inscripcion;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class InscripcionRepository {
    // Método 2.b: Matricular un estudiante en una carrera
    public Inscripcion matricular(Estudiante estudiante, Carrera carrera, int anio) {
        EntityManager em = EntityManagerFactory.getEntityManager();
        try {
            em.getTransaction().begin();

            Inscripcion inscripcion = new Inscripcion();
            inscripcion.setEstudiante(estudiante);
            inscripcion.setCarrera(carrera);
            inscripcion.setGraduado(false);
            inscripcion.setAnioInscripcion(anio);
            em.persist(inscripcion);
            em.getTransaction().commit();

            return inscripcion;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
            return null;
        } finally {
            em.close();
        }
    }

    // Método para guardar o actualizar una inscripción
    public Inscripcion save(Inscripcion inscripcion) {
        EntityManager em = EntityManagerFactory.getEntityManager();
        try {
            em.getTransaction().begin();

            if (inscripcion.getId() == null || inscripcion.getId() == 0) {
                em.persist(inscripcion);
            } else {
                inscripcion = em.merge(inscripcion);
            }

            em.getTransaction().commit();
            return inscripcion;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
            return null;
        } finally {
            em.close();
        }
    }

    // Método para buscar inscripciones por carrera
    public List<Inscripcion> findByCarrera(int idCarrera) {
        EntityManager em = EntityManagerFactory.getEntityManager();
        try {
            String jpql = "SELECT i FROM Inscripcion i WHERE i.carrera.id = :idCarrera";
            TypedQuery<Inscripcion> query = em.createQuery(jpql, Inscripcion.class);
            query.setParameter("idCarrera", idCarrera);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    // Método para buscar inscripciones por estudiante
    public List<Inscripcion> findByEstudiante(int idEstudiante) {
        EntityManager em = EntityManagerFactory.getEntityManager();
        try {
            String jpql = "SELECT i FROM Inscripcion i WHERE i.estudiante.id = :idEstudiante";
            TypedQuery<Inscripcion> query = em.createQuery(jpql, Inscripcion.class);
            query.setParameter("idEstudiante", idEstudiante);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    // Método 3: Generar reporte de carreras con inscriptos y egresados por año
    public List<ReporteCarreraDTO> getReporteCarreras() {
        EntityManager em = EntityManagerFactory.getEntityManager();
        try {
            // Primero obtenemos todas las carreras ordenadas alfabéticamente
            String jpqlCarreras = "SELECT c FROM Carrera c ORDER BY c.nombre ASC";
            List<Carrera> carreras = em.createQuery(jpqlCarreras, Carrera.class).getResultList();

            List<ReporteCarreraDTO> reporte = new ArrayList<>();

            for (Carrera carrera : carreras) {
                ReporteCarreraDTO reporteCarrera = new ReporteCarreraDTO();
                reporteCarrera.setId(carrera.getId());
                reporteCarrera.setNombre(carrera.getNombre());

                // Para cada carrera, obtenemos inscriptos por año
                String jpqlInscriptos = "SELECT i.anioInscripcion, COUNT(i) " +
                        "FROM Inscripcion i " +
                        "WHERE i.carrera.id = :idCarrera " +
                        "GROUP BY i.anioInscripcion " +
                        "ORDER BY i.anioInscripcion ASC";

                List<Object[]> inscriptosPorAnio = em.createQuery(jpqlInscriptos)
                        .setParameter("idCarrera", carrera.getId())
                        .getResultList();

                // Para cada carrera, obtenemos egresados por año
                String jpqlEgresados = "SELECT i.anioGraduacion, COUNT(i) " +
                        "FROM Inscripcion i " +
                        "WHERE i.carrera.id = :idCarrera " +
                        "AND i.graduado = true " +
                        "GROUP BY i.anioGraduacion " +
                        "ORDER BY i.anioGraduacion ASC";

                List<Object[]> egresadosPorAnio = em.createQuery(jpqlEgresados)
                        .setParameter("idCarrera", carrera.getId())
                        .getResultList();

                // Organizamos los datos por año
                Map<Integer, EstadisticasPorAnioDTO> estadisticasPorAnio = new TreeMap<>();

                // Procesamos inscriptos
                for (Object[] dato : inscriptosPorAnio) {
                    Integer anio = (Integer) dato[0];
                    Long cantidad = (Long) dato[1];

                    EstadisticasPorAnioDTO estadistica = estadisticasPorAnio.getOrDefault(anio, new EstadisticasPorAnioDTO());
                    estadistica.setAnio(anio);
                    estadistica.setCantidadInscriptos(cantidad.intValue());
                    estadisticasPorAnio.put(anio, estadistica);
                }

                // Procesamos egresados
                for (Object[] dato : egresadosPorAnio) {
                    Integer anio = (Integer) dato[0];
                    Long cantidad = (Long) dato[1];

                    EstadisticasPorAnioDTO estadistica = estadisticasPorAnio.getOrDefault(anio, new EstadisticasPorAnioDTO());
                    estadistica.setAnio(anio);
                    estadistica.setCantidadEgresados(cantidad.intValue());
                    estadisticasPorAnio.put(anio, estadistica);
                }

                // Asignamos las estadísticas a la carrera
                reporteCarrera.setEstadisticasPorAnio(new ArrayList<>(estadisticasPorAnio.values()));
                reporte.add(reporteCarrera);
            }

            return reporte;
        } finally {
            em.close();
        }
    }
}
