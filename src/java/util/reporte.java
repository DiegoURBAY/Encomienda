/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import dao.Conexion;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Picture;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import sun.nio.ch.IOUtil;
import sun.util.calendar.BaseCalendar.Date;

/**
 *
 * @author usuario
 */
public class reporte extends Conexion {
    
        protected Connection conexion;
    
    private String driver = "com.mysql.jdbc.Driver";
    private String url = "jdbc:mysql://localhost:3307/encomienda";

    private String user = "root";
    private String password = "";
        
    public void conectar(){
        try{
            conexion = DriverManager.getConnection(url, user, password);
            Class.forName(driver);                        
        }catch(ClassNotFoundException | SQLException e){

        }
    }
        
    public void cerrar() throws SQLException{
        if(conexion != null){
            if(!conexion.isClosed()){
                conexion.close();
            }
        }
    }
    
    public static void main(String[] args) throws SQLException, ClassNotFoundException{
        reporte();
    }
    
    public static void reporte() throws SQLException, ClassNotFoundException{
Connection conexion;
             String driver = "com.mysql.jdbc.Driver";
     String url = "jdbc:mysql://localhost:3307/encomienda";

     String user = "root";
     String password = "";
        
    
     
            conexion = DriverManager.getConnection(url, user, password);
            Class.forName(driver);                        


        Workbook book = new XSSFWorkbook();
        Sheet sheet = book.createSheet("Productos");
        
        try {
            //Añadir imagen al excel
            InputStream is = new FileInputStream("src\\java\\img\\logo.jpg");
            
            //crear variable byte para traer la imagen
            byte[] bytes = IOUtils.toByteArray(is);
            
            //traer el index de la imagen, luego se le indica el formato
            int imgIndex = book.addPicture(bytes, Workbook.PICTURE_TYPE_JPEG);
            //para no dejar archivos temporales abiertos
            is.close();
            
            //agregar la imagen al archivo
            CreationHelper help = book.getCreationHelper();
            //crear la imagen
            Drawing draw = sheet.createDrawingPatriarch();
            
            //sacar el ancho de la imagen para poder colocarla de forma correcta
            ClientAnchor anchor = help.createClientAnchor();
            //se indica en que columna colocar la imagen
            anchor.setCol1(0);
            //se indica en que fila colocar la imagen
            anchor.setRow1(1);
            
            //Para crear la imagen
            Picture pict = draw.createPicture(anchor, imgIndex);
            //cambiar el tamaño de la imagen
            pict.resize(1, 3);
            
            //colocar el estilo del titulo
            CellStyle tituloEstilo = book.createCellStyle();
            //alineaciones
            tituloEstilo.setAlignment(HorizontalAlignment.CENTER);
            tituloEstilo.setVerticalAlignment(VerticalAlignment.CENTER);
            Font fuenteTitulo = book.createFont();
            fuenteTitulo.setFontName("Arial");
            fuenteTitulo.setBold(true);
            fuenteTitulo.setFontHeightInPoints((short)14);
            //se le asigna el estilo al titulo
            tituloEstilo.setFont(fuenteTitulo);
            
            //establecer la fila donde irá el titulo. FILA 2
            Row filaTitulo = sheet.createRow(1);
            ////contenido en un rango de celdas 
            Cell celdaTitulo = filaTitulo.createCell(1);
            celdaTitulo.setCellStyle(tituloEstilo);
            celdaTitulo.setCellValue("Reporte de Productos");
            
            //combinar celdas para el titulo
            //el primero parametro es la fila donde inicia, el segundo la ultima
            //el tercero, la primera columa y el cuarto, el ultimo
            sheet.addMergedRegion(new CellRangeAddress(1, 2, 1, 3));
            
            
            //colocar los titulos (variables de bd)
            String[] cabecera = new String []{"tiempo","sobre","paquete", "total"};
            
            //crear los estilos para el encabezado
            //Color de relleno azul solido
            //Color de relleno  solido
            //bordes
            CellStyle headerStyle = book.createCellStyle();
            headerStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            headerStyle.setBorderBottom(BorderStyle.THIN);
            headerStyle.setBorderLeft(BorderStyle.THIN);
            headerStyle.setBorderRight(BorderStyle.THIN);
            headerStyle.setBorderBottom(BorderStyle.THIN);
            
            //establecer fuentes
            Font font = book.createFont();
            font.setFontName("Arial");
            font.setBold(true);            
            font.setColor(IndexedColors.WHITE.getIndex());
            font.setFontHeightInPoints((short)12);
            //se le asigna la fuente al estilo
            headerStyle.setFont(font);        
            
            //crear fila para encabezados, fila 5
            Row filaEncabezados = sheet.createRow(4);
            
            //se imprime el valor de las cabeceras a las celdas
            for(int i= 0; i < cabecera.length; i++){
                
                //para añadirle los estilos a la celda se deben de crear
                //por separado
                Cell celdaEncabezado = filaEncabezados.createCell(i);
                celdaEncabezado.setCellStyle(headerStyle);
                celdaEncabezado.setCellValue(cabecera[i]);
                                
            }
            
            //establecer desde que fila aplicaremos el contenido, fila 6
            int numFilaDatos = 5;
            
            //borde
            CellStyle datosEstilo = book.createCellStyle();
            datosEstilo.setBorderBottom(BorderStyle.THIN);
            datosEstilo.setBorderLeft(BorderStyle.THIN);
            datosEstilo.setBorderRight(BorderStyle.THIN);
            datosEstilo.setBorderBottom(BorderStyle.THIN);        
                        
        PreparedStatement pst;
        PreparedStatement pst1;
        ResultSet rs;
        ResultSet rs1;
        String sqlTrac = "SET lc_time_names = 'es_ES' ";               
        String sql ="SELECT monthname(e.fechatime) AS mes, SUM(CASE WHEN LENGTH(c.identificador) =8 THEN p.precio ELSE 0 END) AS persona, SUM(CASE WHEN LENGTH(c.identificador) =11 THEN p.precio ELSE 0 END) AS empresa FROM clientes c INNER JOIN encomiendas e ON e.idCliente = c.id INNER JOIN tiposencomiendas p ON e.id = p.idEncomienda WHERE e.fechatime BETWEEN '2019-01-01' AND '2019-08-10' GROUP BY mes ORDER BY e.fechatime";
           
            pst1 = conexion.prepareStatement(sqlTrac);
            pst = conexion.prepareStatement(sql);
            rs1 = pst1.executeQuery();             
            rs = pst.executeQuery();       
            
             int numCol = rs.getMetaData().getColumnCount();
             System.out.println(numCol);
            
            while(rs.next()){
                
                Row filaDatos = sheet.createRow(numFilaDatos);
                
                 for (int a = 0; a < numCol; a++) {
                    Cell celdaDatos = filaDatos.createCell(a);
                    //estilo para celdas
                    celdaDatos.setCellStyle(datosEstilo);
                    
                    //agregar valores a la celda
                    //se debe filtar los resultados sino habra error
                    if(a == 1 || a == 2){
                       
                        System.out.println(rs.getString(a+1));

                        celdaDatos.setCellValue(rs.getDouble(a+1));
                        System.out.println(rs.getDouble(a+1));
                    }
                    else{
                         celdaDatos.setCellValue(rs.getString(a+1));
                    }
                 }
                Cell celdaImporte = filaDatos.createCell(3);
                celdaImporte.setCellStyle(datosEstilo);
                celdaImporte.setCellFormula(String.format("B%d+C%d", numFilaDatos+1, numFilaDatos+1));
             
                numFilaDatos++;
            }        

            //generar el reporte
            FileOutputStream fileOut = new FileOutputStream("C:\\Users\\usuario\\Downloads\\Reporte.xlsx");
            book.write(fileOut);
            fileOut.close();
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(reporte.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(reporte.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(reporte.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
