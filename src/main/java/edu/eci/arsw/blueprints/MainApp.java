package edu.eci.arsw.blueprints;

import java.util.Set;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.services.BlueprintsServices;

public class MainApp {

    public static void main(String[] args) {
        System.out.println("=== Demostración de BlueprintsServices con Filtros ===\n");

        // Crear contexto de Spring
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);
        BlueprintsServices svc = ctx.getBean(BlueprintsServices.class);

        System.out.println("1. BlueprintsServices bean creado: " + svc);
        System.out.println("   Tipo: " + svc.getClass().getName() + "\n");

        try {
            // 2. Registrar planos con puntos duplicados y data para filtrado
            System.out.println("2. Registrando planos con datos de prueba para filtros...");

            // Plano con puntos duplicados consecutivos (para filtro de redundancias)
            Point[] duplicatePoints = {
                new Point(0, 0), // Keep
                new Point(0, 0), // Remove (duplicate)
                new Point(10, 10), // Keep
                new Point(10, 10), // Remove (duplicate)
                new Point(10, 10), // Remove (duplicate)
                new Point(20, 20), // Keep
                new Point(30, 30) // Keep
            };
            Blueprint bpDuplicates = new Blueprint("carlos", "casa-con-duplicados", duplicatePoints);
            svc.addNewBlueprint(bpDuplicates);
            System.out.println("   ✓ Registrado: " + bpDuplicates + " (7 puntos originales)");

            // Plano para submuestreo (puntos en secuencia)
            Point[] sequencePoints = {
                new Point(0, 0), // Keep (index 0)
                new Point(1, 1), // Remove (index 1)  
                new Point(2, 2), // Keep (index 2)
                new Point(3, 3), // Remove (index 3)
                new Point(4, 4), // Keep (index 4)
                new Point(5, 5), // Remove (index 5)
                new Point(6, 6), // Keep (index 6)
                new Point(7, 7) // Remove (index 7)
            };
            Blueprint bpSequence = new Blueprint("maria", "linea-secuencial", sequencePoints);
            svc.addNewBlueprint(bpSequence);
            System.out.println("   ✓ Registrado: " + bpSequence + " (8 puntos originales)");

            // Plano mixto (duplicados y secuencia)
            Point[] mixedPoints = {
                new Point(100, 100), // Keep
                new Point(100, 100), // Remove (duplicate)
                new Point(200, 200), // Keep  
                new Point(300, 300), // Remove (subsampling index 3)
                new Point(400, 400), // Keep
                new Point(500, 500) // Remove (subsampling index 5)
            };
            Blueprint bpMixed = new Blueprint("carlos", "plano-mixto", mixedPoints);
            svc.addNewBlueprint(bpMixed);
            System.out.println("   ✓ Registrado: " + bpMixed + " (6 puntos originales)\n");

            // 3. Demostrar filtrado actual (debería ser RedundancyFilter por @Component)
            System.out.println("3. Consultando planos (con filtro actual: RedundancyFilter)...");

            Blueprint filtered1 = svc.getBlueprint("carlos", "casa-con-duplicados");
            System.out.println("   ✓ Casa con duplicados - Puntos filtrados: " + filtered1.getPoints().size() + "/7");
            System.out.println("     Puntos: " + pointsToString(filtered1));

            Blueprint filtered2 = svc.getBlueprint("maria", "linea-secuencial");
            System.out.println("   ✓ Línea secuencial - Puntos filtrados: " + filtered2.getPoints().size() + "/8");
            System.out.println("     Puntos: " + pointsToString(filtered2));

            Blueprint filtered3 = svc.getBlueprint("carlos", "plano-mixto");
            System.out.println("   ✓ Plano mixto - Puntos filtrados: " + filtered3.getPoints().size() + "/6");
            System.out.println("     Puntos: " + pointsToString(filtered3) + "\n");

            // 4. Consultar por autor con filtrado
            System.out.println("4. Consultando todos los planos de Carlos (filtrados)...");
            Set<Blueprint> carlosBlueprints = svc.getBlueprintsByAuthor("carlos");
            System.out.println("   ✓ Planos de Carlos (" + carlosBlueprints.size() + " encontrados):");
            for (Blueprint bp : carlosBlueprints) {
                System.out.println("     - " + bp.getName() + ": " + bp.getPoints().size() + " puntos filtrados");
                System.out.println("       Puntos: " + pointsToString(bp));
            }

            // 5. Información sobre cómo cambiar el filtro
            System.out.println("\n5. Instrucciones para cambiar de filtro:");
            System.out.println("   Para usar FILTRO DE SUBMUESTREO en lugar de redundancias:");
            System.out.println("   1. Comentar @Component en RedundancyFilterBlueprint.java");
            System.out.println("   2. Descomentar @Component en SubsamplingFilterBlueprint.java");
            System.out.println("   3. Recompilar y ejecutar");
            System.out.println();
            System.out.println("   FILTRO ACTUAL: RedundancyFilter (elimina puntos consecutivos repetidos)");
            System.out.println("   FILTRO ALTERNATIVO: SubsamplingFilter (toma 1 de cada 2 puntos)");

        } catch (Exception e) {
            System.err.println("Error inesperado: " + e.getMessage());
            e.printStackTrace();
        } finally {
            ctx.close();
        }

        System.out.println("\n=== Demostración completada ===");
    }

    private static String pointsToString(Blueprint bp) {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < bp.getPoints().size(); i++) {
            Point p = bp.getPoints().get(i);
            sb.append("(").append(p.getX()).append(",").append(p.getY()).append(")");
            if (i < bp.getPoints().size() - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }
}
