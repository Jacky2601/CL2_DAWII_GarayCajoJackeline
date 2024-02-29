package com.cibertec.assessment.service.imp;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cibertec.assessment.model.Polygon;
import com.cibertec.assessment.model.Square;
import com.cibertec.assessment.repo.PolygonRepo;
import com.cibertec.assessment.repo.SquareRepo;
import com.cibertec.assessment.service.PolygonService;
import com.cibertec.assessment.service.SquareService;

@Service
public class SquareServiceImpl implements SquareService{

	@Autowired 
	SquareRepo squareRepo;
	
	@Autowired
    PolygonRepo polygonRepo;
	
	@Autowired
	PolygonService polygonService;
	
	//Al momento de crear se debe validar si 
	//alguno de parte del cuadrado se encuentra dentro de algun
	//poligono y de ser asi se debe capturar el id de los poligonos y 
	//guardar como un string pero con formato de array
	//Ejemplo polygons = "["1","2"]"
	//Se guardan los ids correspondites
	//usar los metodos ya existentes para listar polygonos
	
	
	@Override
	public Square create(Square s) {
	    List<Polygon> polygons = polygonRepo.findAll();
	    List<Integer> intersectingPolygonIds = new ArrayList<>();

	    // Verificar intersección con polígonos y actualizar el cuadrado
	    for (Polygon polygon : polygons) {
	        if (isIntersecting(s, polygon)) {
	            intersectingPolygonIds.add(polygon.getId());
	        }
	    }

	    // Actualizar el cuadrado con los IDs de los polígonos interceptados
	    s.setPolygons(intersectingPolygonIds.toString());

	    // Guardar el cuadrado en la base de datos
	    return squareRepo.save(s);
	}
	
	
	private boolean isIntersecting(Square s, Polygon polygon) {
	    double[] squareX = convertStringToDoubleArray(s.getXPoints());
	    double[] squareY = convertStringToDoubleArray(s.getYPoints());
	    double[] polygonX = convertStringToDoubleArray(polygon.getXPoints());
	    double[] polygonY = convertStringToDoubleArray(polygon.getYPoints());

	    int j = squareX.length - 1;
	    boolean oddNodes = false;

	    for (int i = 0; i < squareX.length; i++) {
	        if ((squareY[i] < polygonY[i] && squareY[j] >= polygonY[i])
	                || (squareY[j] < polygonY[i] && squareY[i] >= polygonY[i])) {
	            if (squareX[i] + (polygonY[i] - squareY[i]) / (squareY[j] - squareY[i]) * (squareX[j] - squareX[i]) < polygonX[i]) {
	                oddNodes = !oddNodes;
	            }
	        }
	        j = i;
	    }

	    return oddNodes;
	}
	
	

	private boolean doIntersect(double x1, double y1, double x2, double y2, double[] polygonX, double[] polygonY) {
	    // Iterar sobre los bordes del polígono
	    int numPoints = polygonX.length;
	    for (int i = 0; i < numPoints; i++) {
	        double x3 = polygonX[i];
	        double y3 = polygonY[i];
	        double x4 = polygonX[(i + 1) % numPoints]; // El siguiente punto en el polígono
	        double y4 = polygonY[(i + 1) % numPoints];
	        
	        // Verificar si las líneas (x1, y1)-(x2, y2) y (x3, y3)-(x4, y4) se intersectan
	        if (doIntersectLines(x1, y1, x2, y2, x3, y3, x4, y4)) {
	            return true; // Se intersectan
	        }
	    }
	    
	    return false; // No se intersectan
	}

	private boolean doIntersectLines(double x1, double y1, double x2, double y2, double x3, double y3, double x4, double y4) {
	    // Calcular las orientaciones
	    double orientation1 = orientation(x1, y1, x2, y2, x3, y3);
	    double orientation2 = orientation(x1, y1, x2, y2, x4, y4);
	    double orientation3 = orientation(x3, y3, x4, y4, x1, y1);
	    double orientation4 = orientation(x3, y3, x4, y4, x2, y2);
	    
	    // Verificar si las orientaciones son diferentes
	    if (orientation1 != orientation2 && orientation3 != orientation4) {
	        return true; // Se intersectan
	    }
	    
	    // Verificar casos especiales de colinearidad
	    if (orientation1 == 0 && onSegment(x1, y1, x2, y2, x3, y3)) {
	        return true; // Se intersectan
	    }
	    
	    if (orientation2 == 0 && onSegment(x1, y1, x2, y2, x4, y4)) {
	        return true; // Se intersectan
	    }
	    
	    if (orientation3 == 0 && onSegment(x3, y3, x4, y4, x1, y1)) {
	        return true; // Se intersectan
	    }
	    
	    if (orientation4 == 0 && onSegment(x3, y3, x4, y4, x2, y2)) {
	        return true; // Se intersectan
	    }
	    
	    return false; // No se intersectan
	}

	private double orientation(double x1, double y1, double x2, double y2, double x3, double y3) {
	    // Calcular la orientación usando el método del producto cruzado
	    return (x2 - x1) * (y3 - y2) - (y2 - y1) * (x3 - x2);
	}

	private boolean onSegment(double x1, double y1, double x2, double y2, double x, double y) {
	    // Verificar si el punto (x, y) está en el segmento (x1, y1)-(x2, y2)
	    return x >= Math.min(x1, x2) && x <= Math.max(x1, x2) && y >= Math.min(y1, y2) && y <= Math.max(y1, y2);
	}

	
	private double[] convertStringToDoubleArray(String points) {
	    // Eliminar los corchetes al principio y al final y luego dividir los puntos por la coma y espacio
	    String[] stringPoints = points.substring(1, points.length() - 1).split(", ");
	    
	    // Crear un arreglo de dobles del mismo tamaño que la cantidad de puntos
	    double[] doublePoints = new double[stringPoints.length];
	    
	    // Convertir cada punto de cadena a un número de punto flotante
	    for (int i = 0; i < stringPoints.length; i++) {
	        doublePoints[i] = Double.parseDouble(stringPoints[i]);
	    }
	    
	    return doublePoints;
	}

	

	@Override
	public List<Square> list() {
		
		return squareRepo.findAll();
						
	}
	
	
	
}
