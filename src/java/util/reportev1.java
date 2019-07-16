/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import dao.Conexion;
import dao.ReporteDAO;
import entidad.Reporte;
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
import java.util.List;
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

/**
 *
 * @author usuario
 */
public class reportev1 extends Conexion {
    
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
            String[] cabecera = new String []{"tiempo","sobre","paquete"};
            
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
                        
         
            //Consulta a la base de datos
            ReporteDAO reporeteDAO = new ReporteDAO();


            List<Reporte> lis_ingreso = reporeteDAO.consultarPrecioPorFechaPrueba();
            
            String[] miarray = new String[lis_ingreso.size()];
            Reporte[][] miArray = new Reporte[lis_ingreso.size()][3];
            
for(int i=0; i<lis_ingreso.size();i++){
miArray[i][0]=lis_ingreso.get(i*2);
miArray[i][1]=lis_ingreso.get(i*2+1);
}
            
                    String[][] document = new String[][] {
            {"Sergio P", "1234567", "sergiop@prueba.es"},
            {"Laura L", "4324251", "laural@prueba.es"},
            {"Laura L", "4324251", "laural@prueba.es"},
            {"Laura L", "4324251", "laural@prueba.es"},
            {"Laura L", "4324251", "laural@prueba.es"},
            {"Juan H", "7363153", "juanh@prueba.es"}
                            
        };
            
            //columnas trae la consulta            
            int numCol = 3;            
            
            
            //crear cada celda por separado
            for (int j = 1; j <= lis_ingreso.size(); j++) {

                //creamos fila y que inicie desde el 5
                Row filaDatos = sheet.createRow(numFilaDatos + j);
                
                for (int a = 0; a < cabecera.length; a++) {

                                 
                    Cell celdaDatos = filaDatos.createCell(a);
                       //estilo para celdas
                    celdaDatos.setCellStyle(datosEstilo);
                                        
                    //agregar valor
                    //se deben filrar valores adecuados

               //     celdaDatos.setCellValue(lis_ingreso.get(a).getTiempo());                    
                //    celdaDatos.setCellValue(lis_ingreso.get(a).getSobre());
               //     celdaDatos.setCellValue(lis_ingreso.get(a).getPaquete());
                    
            //        filaDatos.createCell(a).setCellValue(lis_ingreso.get(j).getTiempo());
        
                    celdaDatos.setCellValue(String.valueOf(miArray[j - 1][a]));
                    
System.out.println("tiempo: "+lis_ingreso.get(a).getTiempo()+", sobre: "+lis_ingreso.get(a).getSobre()+", paquete: "+lis_ingreso.get(a).getPaquete());                    
                /*    
                    if(a == 1 || a == 2){
                        
                        celdaDatos.setCellValue(lis_ingreso.get(a+1).getSobre());
                        System.out.println(lis_ingreso.get(a+1).getSobre());
                    
                        celdaDatos.setCellValue(lis_ingreso.get(a+1).getPaquete());
                        System.out.println(lis_ingreso.get(a+1).getPaquete());
                    }
                    else{
                        celdaDatos.setCellValue(lis_ingreso.get(a+1).getTiempo());
                        System.out.println(lis_ingreso.get(a+1).getTiempo());
                    }                                              
              */
                }
                
          //      Cell celdaImporte = filaDatos.createCell(3);
          //      celdaImporte.setCellStyle(datosEstilo);
         //       celdaImporte.setCellFormula(String.format("C%d+D%d", numFilaDatos+1, numFilaDatos+1));
         //   
             //   numFilaDatos++;
                
            }
           
            //generar el reporte
            FileOutputStream fileOut = new FileOutputStream("C:\\Users\\usuario\\Downloads\\Reporte.xlsx");
            book.write(fileOut);
            fileOut.close();
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(reportev1.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(reportev1.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(reportev1.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
