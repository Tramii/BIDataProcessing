package limpiezaDatosProyecto2BI;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;
import java.util.Locale;

public class AgregarNumFilaAPCEntrega3 {
	private Hashtable filaDelPc;
	
	public AgregarNumFilaAPCEntrega3(){
		filaDelPc = new Hashtable();
		guardarFilasDeCadaPc();
		leerYProcesar();
	}
	public void guardarFilasDeCadaPc(){
		try{
			    
			File fileHistorial = new File("./data/datos/EquiposYsusFilas.csv");
		    BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fileHistorial)));
		    String line  = br.readLine();
		    int primero =1;
		    while ((line) != null) {
		    	if(primero ==1){
		    		primero++; 
		    	}
		    	else
		    	{
			    	String [] valores = line.split(",");
			    	String equipo = valores[0];
			    	String fila = valores[4];
			    	//System.out.println(equipo +"   "+fila);
			    	filaDelPc.put(equipo, fila);
			    }
		    	line  = br.readLine();
		    }
			br.close();
		}catch (Exception e){
			
		}
	}
	
	public void leerYProcesar(){
		try{
			// solo hay que separar
			File fileHistorialBien = new File("./data/datosProcesados/usoDeUnaMaquinaCONFILAS.txt");
			PrintWriter writer = new PrintWriter(fileHistorialBien, "UTF-8");
			    
			File fileHistorial = new File("./data/datosProcesados/usoDeUnaMaquina.txt");
		    BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fileHistorial)));
		    String line  = br.readLine();
		    int primero =1;
		    while ((line) != null) {
		    	if(primero ==1){
		    		primero++; writer.println("usuario,sala,idEquipo,dia,mes,año,hora,minuto,diaLenguajeNatural,semana,filaDelPcEnLaSala");
		    	}else{	        	
			        String idCompuActual = line.split(",")[2];
			        //System.out.println(idCompuActual);
			        writer.println(line +","+filaDelPc.get(idCompuActual));
		    	}
		    	line  = br.readLine();
		    }
			writer.close();
			br.close();
		}catch (Exception e){
			
		}
			System.out.println("termino");
	}

	public static void main(String[] args) {
		AgregarNumFilaAPCEntrega3 main = new AgregarNumFilaAPCEntrega3();
	}
}
