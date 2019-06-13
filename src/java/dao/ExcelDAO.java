/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.ss.formula.FormulaParseException;
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
public class ExcelDAO extends Conexion {
    
    public String generarIngresos(Date inicio, Date fin)  throws SQLException, ClassNotFoundException{

        List<String> fechas = fecha_excel(inicio, fin);
        
        String mensaje;
            
        Workbook book = new XSSFWorkbook();
        Sheet sheet = book.createSheet("Ingresos");
        
        try {
            
            InputStream is = new FileInputStream("C:\\Users\\usuario\\Documents\\NetBeansProjects\\Encomienda\\src\\java\\img\\logo.jpg");
        //   Path path = Paths.get("src\\java\\img\\logo.jpg");
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
            celdaTitulo.setCellValue("Reporte de cantidad de Ingresos desde "+fechas.get(0)+" hasta "+fechas.get(1));
            
            //combinar celdas para el titulo
            //el primero parametro es la fila donde inicia, el segundo la ultima
            //el tercero, la primera columa y el cuarto, el ultimo
            sheet.addMergedRegion(new CellRangeAddress(1, 2, 1, 12));
            
            
            //colocar los titulos (variables de bd)
            String[] cabecera = new String []{"Mes","Empresa","Persona", "Total"};
            
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
            String sql ="SELECT monthname(e.fechatime) AS mes, SUM(CASE WHEN LENGTH(c.identificador) =8 THEN p.precio ELSE 0 END) AS persona, SUM(CASE WHEN LENGTH(c.identificador) =11 THEN p.precio ELSE 0 END) AS empresa FROM clientes c INNER JOIN encomiendas e ON e.idCliente = c.id INNER JOIN tiposencomiendas p ON e.id = p.idEncomienda WHERE e.fechatime BETWEEN '"+inicio+"' AND '"+fin+"' GROUP BY mes ORDER BY e.fechatime";

                this.conectar();
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

            String titulo_documento = "Ingresos_Reporte_"+fechas.get(2);
                
            //generar el reporte
            FileOutputStream fileOut = new FileOutputStream("C:\\Users\\usuario\\Downloads\\"+titulo_documento+".xlsx");
            book.write(fileOut);
            fileOut.close();
            
            
            mensaje = "ok";
            
        } catch (FileNotFoundException ex) {
            
            Logger.getLogger(ExcelDAO.class.getName()).log(Level.SEVERE, null, ex);
            mensaje = " error: no se encuentra la imagen del logo o existe excel del mismo nombre. Especificacion del error ("+ex+")";
        } catch (IOException | SQLException | FormulaParseException ex) {
            Logger.getLogger(ExcelDAO.class.getName()).log(Level.SEVERE, null, ex);
            mensaje = " error: "+ex;
        }

        return mensaje;
    }
    
    
public String generarIngresosDetallado(String tipo_cliente_string, int tipo_cliente, String mes, Date inicio, Date fin)  throws SQLException, ClassNotFoundException{

        List<String> fechas = fecha_excel(inicio, fin);
        
        String mensaje;
            
        Workbook book = new XSSFWorkbook();
        Sheet sheet = book.createSheet("Ingresos");
        
        try {
            
            InputStream is = new FileInputStream("C:\\Users\\usuario\\Documents\\NetBeansProjects\\Encomienda\\src\\java\\img\\logo.jpg");
        //   Path path = Paths.get("src\\java\\img\\logo.jpg");
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
            
                        
            
            String titulo_excel;
                
            if(mes == null){
                titulo_excel = "Reporte de ingresos en soles de "+tipo_cliente_string+"s registrados desde "+fechas.get(0)+" hasta "+fechas.get(1);
                
            }
            else{
                titulo_excel = "Reporte de ingresos en soles de "+tipo_cliente_string+"s registrados durante el mes de "+mes;
            }           
            
            celdaTitulo.setCellValue(titulo_excel);
            
            //combinar celdas para el titulo
            //el primero parametro es la fila donde inicia, el segundo la ultima
            //el tercero, la primera columa y el cuarto, el ultimo
            sheet.addMergedRegion(new CellRangeAddress(1, 2, 1, 12));
            
            
            //colocar los titulos (variables de bd)
            String[] cabecera = new String []{"Fecha", "Ingresos"};
            
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
            String sql;

            //String sql ="SELECT monthname(c.fecharegistro) AS mes, SUM(c.precio) AS total FROM tiposencomiendas c WHERE c.fecharegistro BETWEEN '"+inicio+"' AND '"+fin+"' GROUP BY mes ORDER BY c.fecharegistro";

            if(mes ==null){                          
            //   sql = "SELECT c.fecharegistro AS mes, SUM(c.estado) AS total FROM clientes c WHERE c.fecharegistro BETWEEN '"+inicio+"' AND '"+fin+"' AND LENGTH(c.identificador) ="+tipo_cliente+" GROUP BY mes ORDER BY c.fecharegistro";
                sql = "SELECT p.fecharegistro AS mes, SUM(p.precio) AS total FROM clientes c INNER JOIN encomiendas e ON e.idCliente = c.id INNER JOIN tiposencomiendas p ON e.id = p.idEncomienda WHERE p.fecharegistro BETWEEN '"+inicio+"' AND '"+fin+"' AND LENGTH(c.identificador) ="+tipo_cliente+" GROUP BY mes ORDER BY p.fecharegistro";
            }
            else{
                sql = "SELECT p.fecharegistro AS mes, SUM(p.precio) AS total FROM clientes c INNER JOIN encomiendas e ON e.idCliente = c.id INNER JOIN tiposencomiendas p ON e.id = p.idEncomienda WHERE monthname(p.fecharegistro)='"+mes+"' AND LENGTH(c.identificador) ="+tipo_cliente+" GROUP BY mes ORDER BY p.fecharegistro";
            }   

                this.conectar();
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
                        if(a == 1){

                            celdaDatos.setCellValue(rs.getDouble(a+1));
                            System.out.println(rs.getDouble(a+1));
                        }
                        else{
                       String fecha_reporte_detallado = rs.getString(a+1);
           //                  System.out.println(rs.getString(a+1));
                         
                            String[] parts = fecha_reporte_detallado.split("-");
                            String año_parts = parts[0];
                            String mes_parts = parts[1];
                            String dia_parts = parts[2];
                            String parts_final = dia_parts+"/"+mes_parts+"/"+año_parts;

                            System.out.println(rs.getString(a+1));
                            //Cambiamos el orden al formato americano, de esto dd/mm/yyyy a esto mm/dd/yyyy
                        
                            celdaDatos.setCellValue(parts_final);
                        }
                    }
             //       Cell celdaImporte = filaDatos.createCell(3);
              //      celdaImporte.setCellStyle(datosEstilo);
           //         celdaImporte.setCellFormula(String.format("B%d+C%d", numFilaDatos+1, numFilaDatos+1));

                    numFilaDatos++;
                }        

            String titulo_documento = "Ingresos_Reporte_Detallado_"+fechas.get(2);
                
            //generar el reporte
            FileOutputStream fileOut = new FileOutputStream("C:\\Users\\usuario\\Downloads\\"+titulo_documento+".xlsx");
            book.write(fileOut);
            fileOut.close();
            
            
            mensaje = "ok";
            
        } catch (FileNotFoundException ex) {
            
            Logger.getLogger(ExcelDAO.class.getName()).log(Level.SEVERE, null, ex);
            mensaje = " error: no se encuentra la imagen del logo o existe excel del mismo nombre. Especificacion del error ("+ex+")";
        } catch (IOException | SQLException | FormulaParseException ex) {
            Logger.getLogger(ExcelDAO.class.getName()).log(Level.SEVERE, null, ex);
            mensaje = " error: "+ex;
        }

        return mensaje;
    }    
    
    public List<String> fecha_excel(Date inicio, Date fin){
        
        List<String> result = new ArrayList<>(); 
        
        Calendar inicio_1 = Calendar.getInstance();
        inicio_1.setTime(inicio);
        
        Calendar fin_1 = Calendar.getInstance();
        fin_1.setTime(fin);   
        
         Calendar actual = Calendar.getInstance();
        
        
        int inicio_mes = inicio_1.get(Calendar.MONTH)+1;
        int inicio_año = inicio_1.get(Calendar.YEAR);        
        int inicio_dia = inicio_1.get(Calendar.DAY_OF_MONTH); 
        
        int fin_mes = fin_1.get(Calendar.MONTH)+1;
        int fin_año = fin_1.get(Calendar.YEAR);        
        int fin_dia = fin_1.get(Calendar.DAY_OF_MONTH);         
        
        
        int actual_mes = actual.get(Calendar.MONTH)+1;
        int actual_año = actual.get(Calendar.YEAR);        
        int actual_dia = actual.get(Calendar.DAY_OF_MONTH); 


        String inicio_mes_string = ""+inicio_mes;
        if(inicio_mes < 10){
            inicio_mes_string = "0"+inicio_mes;
        }
        String fin_mes_string = ""+fin_mes;
        if(fin_mes < 10){
            fin_mes_string = "0"+fin_mes;
        }
        String actual_mes_string = ""+actual_mes;
        if(fin_mes < 10){
            actual_mes_string = "0"+actual_mes;
        }        
        
        int hora = actual.get(Calendar.HOUR_OF_DAY);
        int minuto = actual.get(Calendar.MINUTE);
        int segundo = actual.get(Calendar.SECOND);      
        
        String actual_minuto_string = ""+minuto;
        if(minuto < 10){
            actual_minuto_string = "0"+minuto;
        }        
        String actual_segundo_string = ""+segundo;
        if(segundo < 10){
            actual_segundo_string = "0"+segundo;
        }               
        
        String fecha_inicio = inicio_dia + "/"+inicio_mes_string+"/"+inicio_año;
        String fecha_fin = fin_dia + "/"+fin_mes_string+"/"+fin_año;
        
        String fecha_actual = actual_dia + "-"+actual_mes_string+"-"+actual_año;        
        String hora_actual = hora + "-"+actual_minuto_string+"-"+actual_segundo_string;
        
        String fecha_hora = fecha_actual+"_"+ hora_actual;
        
        result.add(fecha_inicio);
        result.add(fecha_fin);
        result.add(fecha_hora);
                
        return result;
        
    }
    
}
