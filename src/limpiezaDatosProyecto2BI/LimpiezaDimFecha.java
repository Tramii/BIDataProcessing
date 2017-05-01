package limpiezaDatosProyecto2BI;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;
import java.util.Locale;

public class LimpiezaDimFecha {
	private Hashtable posInicioMes2016;
	
	public LimpiezaDimFecha(){
		//Como saber un día a que semana del calendario estudiantil pertenece
		
		//Inicio semana 2016-1 => enero 18 Lunes  //ultimo día finales= 20 Mayo    //total 123 días
		//Días 2016-1 => Enero:13 Febrero: 29 Marzo: 31 Abril:30 Mayo:20  días
		//Inicio semana 2016-2 => Agosto 1 Lunes  //ultimo día finales= 25 Noviembre    //total 117 días
		//Días 2016-1 => Agosto:31 Septiembre: 30 Octubre: 31 Noviembre:25   días
		
		//#Semana = (#día + posInicioMes)/7   Si semana -1 => semana fuera de dias de clase o examenes
		
		posInicioMes2016 = new Hashtable();
		posInicioMes2016.put(1, -18);//18 enero= pos -18 + dia 18 = 0 = primer dia
		posInicioMes2016.put(2,(13)); //1 feb= pos 13+dia 1 = 14 
		posInicioMes2016.put(3,(13+29)); //marzo
		posInicioMes2016.put(4,(13+29+31)); //abril
		posInicioMes2016.put(5,(13+29+31+30));  //mayo
		
		posInicioMes2016.put(8,0);
		posInicioMes2016.put(9,31); //1 sept= pos 31+dia 1 = 32
		posInicioMes2016.put(10,(31+30)); //Oct
		posInicioMes2016.put(11,(31+30+31)); //Nov
		
		leerYProcesar();
	}
	
	
	public void leerYProcesar(){
		try{
			// solo hay que separar
			File fileHistorialBien = new File("./data/datosProcesados/historialF.txt");
			PrintWriter writer = new PrintWriter(fileHistorialBien, "UTF-8");
			    
			File fileHistorial = new File("./data/datos/historial.csv");
		    BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fileHistorial)));
		    String line  = br.readLine();
		    int primero =1;
		    while ((line) != null) {
		    	if(primero ==1){
		    		primero++; writer.println("usuario,equipo,dia,mes,año,hora,minuto,diaLenguajeNatural,semana");
		    	}else{
			        String [] campos = line.split(";");
			        String fecha = campos[2];
			        String [] valores = fecha.split("/");
			        int año = Integer.parseInt(valores[2].split(" ")[0]);
			        if(año == 2016){
			        	String dia = valores[0];
				        String mes = valores[1];
				        String [] horaMinuto = (valores[2].split(" ")[1]).split(":");
				        String hora = horaMinuto[0];
				        String minuto = horaMinuto[1];
				        int semana =0;
				        if(Integer.parseInt(mes) < 6 || (Integer.parseInt(mes) >=8 && Integer.parseInt(mes)<12) ){
				        	semana = (((Integer)posInicioMes2016.get(Integer.parseInt(mes)))
					        		+Integer.parseInt(dia))/7;
				        	semana++;
				        }
				        else{semana = -1;}
				        
				        String diaLengNat=dayName(año+"-"+mes+"-"+dia, "yyyy-MM-dd");
			        	writer.println(campos[0]+","+campos[1]+","+dia+","+mes+","+año+","+hora+","+minuto+","+diaLengNat+","+semana);		        	
			        }
		    	}
		    	line  = br.readLine();
		    }
			writer.close();
			br.close();
			
			//fact table uso de una sala
			Hashtable <String,Integer>cuantosEquipos = new Hashtable<String,Integer>();
			cuantosEquipos.put("waira 1", 47);
			cuantosEquipos.put("waira 2", 30);
			cuantosEquipos.put("turing", 39);
			
			File fileHistorialOcupacion = new File("./data/datos/historial_ocupacion.csv");
			File fileHistorialOcupacionBien = new File("./data/datosProcesados/UsoDeUnaSala.txt");
			PrintWriter writer2 = new PrintWriter(fileHistorialOcupacionBien, "UTF-8");
			    
		    BufferedReader br2 = new BufferedReader(new InputStreamReader(new FileInputStream(fileHistorialOcupacion)));
		    String line2  = br2.readLine();
		    int primero2 =1;
		    while ((line2) != null) {
		    	if(primero2 ==1){
		    		primero2++; writer2.println("sala,numEquipos,PorcentajeOcupacion,dia,mes,año,hora,minuto,diaLenguajeNatural,semana");
		    	}else{
			        String [] campos = line2.split(";");
			        String fecha = campos[0];
			        String [] valores = fecha.split("/");
			        int año = Integer.parseInt(valores[2].split(" ")[0]);
			        if(año == 2016){
			        	String dia = valores[0];
				        String mes = valores[1];
				        String [] horaMinuto = (valores[2].split(" ")[1]).split(":");
				        String hora = horaMinuto[0];
				        String minuto = horaMinuto[1];
				        int semana =0;
				        if(Integer.parseInt(mes) < 6 || (Integer.parseInt(mes) >=8 && Integer.parseInt(mes)<12) ){
				        	semana = (((Integer)posInicioMes2016.get(Integer.parseInt(mes)))
					        		+Integer.parseInt(dia))/7;
				        	semana++;
				        }
				        else{semana = -1;}
				        String diaLengNat=dayName(año+"-"+mes+"-"+dia, "yyyy-MM-dd");
				        int numEquipos = cuantosEquipos.get(campos[1]);
				        double ocupados =Double.parseDouble(campos[2]);
			        	writer2.println(campos[1]+","+numEquipos+","+(ocupados/numEquipos)+","+dia+","+mes+","+año+","+hora+","+minuto+","+diaLengNat+","+semana);		        	
			        }
		    	}
		    	line2  = br2.readLine();
		    }
			writer2.close();
			br2.close();
			}catch(Exception e){}

	}
    public static String dayName(String inputDate, String format){
        Date date = null;
        try {
            date = new SimpleDateFormat(format).parse(inputDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //System.out.println(inputDate+"          "+date.toString());
        return new SimpleDateFormat("EEEE", Locale.ENGLISH).format(date);
    }
	public static void main(String[] args) {
		LimpiezaDimFecha main = new LimpiezaDimFecha();
	}
}
