package com.cibertec.assessment;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class DibujarPoligono extends JPanel {
	// Coordenadas de los vértices del polígono
    //Poligono 1 
	Double xpoint1[] = {10.0, 205.0, 305.0, 405.0, 500.0};
    Double ypoint1[] = {10.0, 501.0, 506.0, 107.0, 30.0};
    
    //Poligono 2 
    Double xpoint2[] = {100.0, 605.0, 305.0, 405.0, 500.0};
    Double ypoint2[] = {100.0, 601.0, 506.0, 337.0, 300.0};
    
 // Coordenadas de los vértices del cuadrado
    
    Double xcuadrado1[] = {500.0, 500.0, 600.0, 600.0};
    Double ycuadrado1[] = {100.0, 200.0, 200.0, 100.0};
     
    Double xcuadrado2[] = {100.0, 100.0, 200.0, 200.0};
    Double ycuadrado2[] = {300.0, 400.0, 400.0, 300.0};
     
    Double xcuadrado3[] = {500.0, 500.0, 600.0, 600.0};
    Double ycuadrado3[] = {350.0, 450.0, 450.0, 350.0};
      
    Double[] xcuadrado4 = {60.0, 60.0, 360.0, 360.0};
    Double[] ycuadrado4 = {60.0, 360.0, 360.0, 60.0};
      
      
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
               
        // Dibujar los polígonos
        g2d.setColor(Color.BLUE);
        g2d.drawPolygon(toIntArray(xpoint1), toIntArray(ypoint1), xpoint1.length);
        
        g2d.setColor(Color.BLACK);
        g2d.drawPolygon(toIntArray(xpoint2), toIntArray(ypoint1), xpoint2.length);
        
     // Dibujar los cuadrados
        g2d.setColor(Color.RED);
        g2d.drawPolygon(toIntArray(xcuadrado1), toIntArray(ycuadrado1), xcuadrado1.length);
        
        g2d.setColor(Color.ORANGE);
        g2d.drawPolygon(toIntArray(xcuadrado2), toIntArray(ycuadrado2), xcuadrado2.length);
        
        g2d.setColor(Color.GREEN);
        g2d.drawPolygon(toIntArray(xcuadrado3), toIntArray(ycuadrado3), xcuadrado3.length);
        
        g2d.setColor(Color.GRAY);
        g2d.drawPolygon(toIntArray(xcuadrado4), toIntArray(ycuadrado4), xcuadrado4.length);  
        
    }

    // Método para convertir Double[] a int[]
    private int[] toIntArray(Double[] array) {
        int[] intArray = new int[array.length];
        for (int i = 0; i < array.length; i++) {
            intArray[i] = array[i].intValue();
        }
        return intArray;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Dibujar Polígono");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(new DibujarPoligono());
            frame.setSize(300, 300);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
