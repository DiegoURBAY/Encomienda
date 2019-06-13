
package util;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

// https://www.youtube.com/watch?v=oG18JTIKtLo
// 10. Crear, Leer y Modificar Excel en Java y MySQL
public class Excel {
    
    public static void main(String[] args){
        crearExcel();
    }
    
    public static void crearExcel(){
        
        //Se indica que se va a crear un archivo en excel con xls
        //Se requiere librería poi-3.16.jar
        //Workbook book = new HSSFWorkbook();
        
        //Se indica que se va a crear un archivo en excel con xlsx
        //Se requiere librería poi-ooxml-3.16
        //poi-ooxml-schemas-3.16
        //commons-collections4-4.1
        //xmlbeans-2.6.0
        Workbook book = new XSSFWorkbook();
        
        //se crea una pestaña, se le puede dar un nombre
        Sheet sheet = book.createSheet("Hola java");
        
        
        //se crea una fila primero (un arreglo en la posición 0)
        Row row = sheet.createRow(0);
        //se crea una celda con la fila creada
        row.createCell(0).setCellValue("hola mundo");
        //se crean más celdas
        row.createCell(1).setCellValue(7.5);
        row.createCell(2).setCellValue(true);
        
        //las funciones se crean en celdas separadas
        Cell celda = row.createCell(3);
        celda.setCellFormula(String.format("1+1", ""));
        
        //pasar parametros para crear las formulas
        Row rowUno = sheet.createRow(1);
        rowUno.createCell(0).setCellValue(7);
        rowUno.createCell(1).setCellValue(8);
        
        //suma celdas
        Cell celdados = rowUno.createCell(2);
        celdados.setCellFormula(String.format("A%d+B%d", 2,2));
        
        
        try {
            //se guarda el archivo creado
            FileOutputStream fileout = new FileOutputStream("Excel.xlsx");
            //se indica donde escribir
            book.write(fileout);
            //se cierra el archivo para que se peuda ejecutar
            fileout.close();
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Excel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Excel.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    //12:12
    public static void leer(){
        
    }
}
