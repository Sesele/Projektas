package parkingas;

import com.google.gson.Gson;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;



public class Parkingas {
	
	public static DateTimeFormatter datosFormatas = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm");  
	public static long 	saskaitosNumeris;

	
	public static Scanner sc = new Scanner(System.in);

	/**
	 * @param args
	 */
	public static void main(String[] args) throws IOException {
	
		
		System.out.println("//  UZKRAUNU ISSAUGOTUS DUOMENIS" );
		System.out.println("//  " );
		System.out.println("//  " );
		//////////
		File failas = DirFilForImp("auto_atvaziavo.json");
		////////
		if (failas!=null) {
			Auto.ParkuojasiFromGson(failas);
			Map<String, Auto> DabarParkuojasiMap = Auto.DuokNumeriuSarasa();
//			for (Auto au : DabarParkuojasiMap.values()) {
//				System.out.println(au);
//			}
		}
		System.out.println("//  " );
		//////////
		failas = DirFilForImp("kainynas.json");
		////////
		if (failas!=null) {

			Kainynas.KainaFromGson(failas);
			Map<Integer, Kainynas> kainynasMapCopy=Kainynas.DuokKainyna();
//			for (Kainynas k: kainynasMapCopy.values() ) {
//				System.out.println(k);
//			}
		}
		////////////////////////////////////////////////
		
		
        	 
        	 
		////////////////////////////////////////////////
		
		String [] menu= {
				"1. ATVAŽIUOJA         ",
				"2. IŠVAŽIUOJA         ",
				"3. KAINYNAS           ",
				"4. ATASKAITA 1        ",
				"5. ATASKAITA 2        ",

				};
		
		int pagr_menu=0;

		while(true) {
			System.out.println( ">>");
			System.out.println( ">>");
			System.out.println( "/////////////////////  PRADŽIA   //////////////////////");
			System.out.println( ">>");
			System.out.println( ">>");
			System.out.println( "*******************************************************");
			System.out.println( "*******************************************************");
			for (String pm : menu) {
				System.out.println( "** "+  pm + "******************************");
			}
			System.out.println( "*******************************************************");
			System.out.println( "*******************************************************");
			System.out.println("-1 BAIGTI");
			System.out.println( "*******************************************************");
			System.out.println( "*******************************************************");
			System.out.println( ">>" );
					
			try {
				pagr_menu = sc.nextInt();
			} catch (InputMismatchException e) {
				sc.next();
			}	
			
			
			if(pagr_menu==-1)   break;
			if (pagr_menu>=1 && pagr_menu<= menu.length){
			
			
	    		System.out.println( menu[pagr_menu-1]  + "..........................");

				switch (pagr_menu ) {
					case 1:
			        	AtvykoNaujas();
			        	break;
			        case 2:  
			    		Isvyksta();
			        	break;
			        case 3:  
			    		Kainynas();
			        	break;
			        case 4:  
			    		Ataskaita1();
			        	break;
			        case 5:  
			    		Ataskaita2();
			        	break;

			        default :
						System.err.println("Prasome ivesti numerį iš sąrašo...");
				} //switch   
			
			}else { //tarp 1 ir length
				System.err.println("Prasome ivesti numerį iš sąrašo......");
			}
		} //  while(true); 
			
			
			
		sc.close();

			
			
		System.out.println( "/////////////////////  PABAIGA //////////////////////");
		
		
		
	} //MAIN
///////////////////////////////////////////////////////////////////////////////////////////////////	
	private static String  SkenuojuNumeri() {
		
		String num="";

		System.out.println("//////////////////////////////////////////////////");
		System.out.println("//             SKENUOJU  NUMERĮ                 //");
		System.out.println("//////////////////////////////////////////////////");
		System.out.println("-1 BAIGTI");
		System.out.println( ">>");
		
		
		try {
			num = sc.next();
		} catch (InputMismatchException e) {
			sc.next();
		}	
	
		return num;
	}
	
////////////////////////////////////////////////////////////////////////////////////////////////////
	private static void   AtvykoNaujas() throws IOException  {
		
		Map<String, Auto> DabarParkuojasiMap;
		
		String auto_num="";
		
		do {
			auto_num = SkenuojuNumeri(); ////////Skenuoju///////
			
			if(auto_num.equals("-1") ) { break;};

			DabarParkuojasiMap= Auto.DuokNumeriuSarasa();
			
			if(DabarParkuojasiMap.get(auto_num)!= null) {
				System.err.println("Toks automobilis jau įvažiavo...");
				continue;
			};
			
			
			///////   GAUNU PASKUTINE KAINA ///////////
			Map<Integer, Kainynas> kainynasMapCopy=Kainynas.DuokKainyna();
			int n_id=kainynasMapCopy.size();

			//////    IVEDU NAUJA AUTO    ///////////
			Auto.NaujasAuto(auto_num, n_id);
			
			
			
			
		} while (true);  //while
			
		/////////////////////////////////////////
				
			
		DabarParkuojasiMap= Auto.DuokNumeriuSarasa();
			
		//////////
		File failas = DirFilForExp("auto_atvaziavo.json", false);
		////////
		
		Gson gson = new Gson();

		for (Auto au : DabarParkuojasiMap.values()) {
			//String json = gson.toJson(au);
			System.out.println(au);

			try ( BufferedWriter writer = new BufferedWriter( new FileWriter( failas, true ) ) ) {

				gson.toJson(au, writer);
				//gson.toJson(json, writer);
				writer.newLine();


	        } catch (IOException e) {
	            e.printStackTrace();
	        }
        }
		
		
		
		return ;
	}// AtvykoNaujas	
	
	
////////////////////////////////////////////////////////////////////////////////////////////////////
private static void   Isvyksta() throws IOException{

	Map<String, Auto> DabarParkuojasiMap;
	
	String auto_num="";
	
	do {
		auto_num = SkenuojuNumeri(); ////////Skenuoju///////
	
		if(auto_num.equals("-1") ) { break;};
		
		DabarParkuojasiMap= Auto.DuokNumeriuSarasa();
		
		Auto isvazuoja_auto = DabarParkuojasiMap.get(auto_num);
		
		if( isvazuoja_auto== null ) {
			System.err.println("Toks automobilis dar neįvažiavo...");
			continue;
		};
		
		////// Skaiciuoju suma atsiskaitymui   ///////////
		LocalDateTime laikas_nuo=isvazuoja_auto.getPradzia();
		LocalDateTime laikas_iki=LocalDateTime.now();
		
		Duration duration = Duration.between( laikas_nuo,laikas_iki );
		
		long minutes= duration.toMinutes(); // duaration minutemis
		long dien =  (long) Math.floor(minutes /(24 * 60));//sveikas dienu skaicius
		long min= minutes - dien*24*60;
		long val= (long) Math.floor( min /  60) ;//sveikas valandu skaicius
		min= min - val*60;
		
		///////   GAUNU IVAZIAVIMO METU FIKSUOTA KAINA ///////////
		
		Map<Integer, Kainynas> kainynasMapCopy=Kainynas.DuokKainyna();
		int n_id=kainynasMapCopy.size();
		String mat_vnt = "v";
		double kaina= 0.00;
		
		if (n_id>0) {
			mat_vnt = kainynasMapCopy.get( isvazuoja_auto.getKainosId() ).getMatVnt();
			kaina= kainynasMapCopy.get( isvazuoja_auto.getKainosId()  ).getKaina();
			System.out.println("//////////////////////////////////////////////////");
			System.out.println(">>");
			System.out.println(">>" );
			
		}
		
		
		
		
	    System.out.println("              SĄSKAITA     NR.   " +  ++saskaitosNumeris);
		System.out.println( "              " + LocalDateTime.now().format(datosFormatas));
	    System.out.println("              AUTOMOBILIS NR. " + auto_num);
		System.out.println( "----------------------------------------------------"); 
		System.out.println( "Atvykimo laikas  "+  laikas_nuo.format(datosFormatas)  );
		System.out.println( "Išvykimo laikas  "+  laikas_iki.format(datosFormatas)  );
		System.out.println( "Trukmė : "+  
				((dien==0) ? "" :  dien + " dien. ") +  val + " val. " + min + " min.");
		System.out.println( "----------------------------------------------------"); 
		System.out.println( "Kaina : "+   String.format("%10.2f", kaina) + " per " + mat_vnt + ".");
		System.out.println( "Suma  : "+   String.format("%10.2f", ( Math.round( (kaina * minutes/60) *100) / 100.00 ) )  ); 
		System.out.println( "----------------------------------------------------"); 
		System.out.println( ">>"); 
		System.out.println( ">>"); 
		System.out.println( ">>  AR ATSISKAITĖ ????  "); 
		System.out.println( ">>                (-1)..NE"); 
		System.out.println( ">> (BET KOKS SIMBOLIS)..TAIP  "); 
		String bet_kas=null;
		try {
			bet_kas = sc.next();
		} catch (InputMismatchException e) {
			sc.next();
		}	
		if(  bet_kas.equals("-1") ) continue;
		
		isvazuoja_auto.setPabaiga(laikas_iki);
		isvazuoja_auto.setSuma(Math.round( (kaina * minutes/60) *100) / 100.00 );
		
		// ISSAUGAU  ISVAZIAVUSI AUTOMOBILI GSON  ISTORIJAI //	
		///////////////////
		DabarParkuojasiMap= Auto.DuokNumeriuSarasa();
		//////////
		File failas = DirFilForExp("auto_isvaziavo.json", true); //true - netrinti failo 
		
		Gson gson = new Gson();
		try ( BufferedWriter writer = new BufferedWriter( new FileWriter( failas, true ) ) ) {
			gson.toJson(isvazuoja_auto, writer);
			writer.newLine();
	    } catch (IOException e) {
	            e.printStackTrace();
	    }
		
		//////////  ISTRINU ISVAZIAVUSI AUTO /////////////
		Auto.IstrinuAuto(auto_num);
		////////// PASIKRAUNU /////////////
		DabarParkuojasiMap= Auto.DuokNumeriuSarasa();
		///////// VEL ISSAUGAU  ESAMUS NES SITUACIJA PASIKEITE////
		//////////
		failas = DirFilForExp("auto_atvaziavo.json", false);
		////////
		
		gson = new Gson();

		for (Auto au : DabarParkuojasiMap.values()) {
			try ( BufferedWriter writer = new BufferedWriter( new FileWriter( failas, true ) ) ) {
				gson.toJson(au, writer);
				writer.newLine();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
        }
		
		
	} while (true);  //while
	
	return ;
}// Isvyksta	

///////////////////////////////////////////////////////////////////////////////////////////////////

private static void Kainynas() throws FileNotFoundException, IOException {
	
	Map<Integer, Kainynas> kainynasMapCopy=Kainynas.DuokKainyna();
	
	int n_id=kainynasMapCopy.size();
	String mat_vnt;
	double kaina;
	
	if (n_id>0) {
		mat_vnt = kainynasMapCopy.get(n_id ).getMatVnt();
		kaina= kainynasMapCopy.get(n_id ).getKaina();
		System.out.println("//////////////////////////////////////////////////");
		System.out.println(" Kainynas NR. " +  n_id);
		System.out.println(" Kaina    "  + kaina + " / " + mat_vnt +"." );
		
	}
	
	

	System.out.println("//////////////////////////////////////////////////");
	
	
	System.out.println("//  ĮVESKIT NAUJĄ KAINĄ  :  ");
	System.out.println("//  (-1 BAIGTI)");

	
	do { //while true
		try {
			kaina = Double.parseDouble(sc.next());
		    break; 
		    
	    } catch (NumberFormatException ignore) {
			System.err.println("Įveskit kainą !");
	    }    
	        
    }while(true );   
	if ( kaina== -1 ) return;
	
	Kainynas.NaujaKaina( Math.round( kaina * 100) / 100.00  ); 
	
		
		
	System.out.println( ">>");
	
	kainynasMapCopy=Kainynas.DuokKainyna();
//	for (Kainynas k: kainynasMapCopy.values() ) {
//		System.out.println(k);
//	}
	
	
	//////////
	File failas = DirFilForExp("kainynas.json",false);
	////////
	
	Gson gson = new Gson();

	for (Kainynas ka: kainynasMapCopy.values()) {
		//String json = gson.toJson(au);
		System.out.println(ka);

		try ( BufferedWriter writer = new BufferedWriter( new FileWriter( failas, true ) ) ) {

			gson.toJson(ka, writer);
			//gson.toJson(json, writer);
			writer.newLine();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	
	
}///KAINYNAS

////////////////////////////////////////////////////////////////////////////////////////////////////
private static void   Ataskaita1() {

	Map<String, Auto> DabarParkuojasiMap  = Auto.DuokNumeriuSarasa();
	
	///////   GAUNU IVAZIAVIMO METU FIKSUOTA KAINA ///////////
	Map<Integer, Kainynas> kainynasMapCopy=Kainynas.DuokKainyna();
	int n_id=kainynasMapCopy.size();
	String mat_vnt = "v";
	double kaina= 0.00;
	System.out.println( "---------------------------------------------------------------"); 
	System.out.println( "-------------   ŠIUO METU YRA PARKAVIMO AIKŠTELĖJE   ----------"); 
	System.out.println( "---------------------------------------------------------------"); 
	System.out.println( "   Numeris |     Atvyko      |  Trukmė     |      Kaina (Eur) |"); 
	System.out.println( "---------------------------------------------------------------"); 
	
	
	for (Auto au : DabarParkuojasiMap.values()) {
		if (n_id>0) {
			mat_vnt = kainynasMapCopy.get( au.getKainosId() ).getMatVnt();
			kaina= kainynasMapCopy.get( au.getKainosId() ).getKaina();
		}
		//////Skaiciuoju laiko intervala   ///////////
		LocalDateTime laikas_nuo=au.getPradzia();
		LocalDateTime laikas_iki=LocalDateTime.now();
		
		Duration duration = Duration.between( laikas_nuo,laikas_iki );
		
		long minutes= duration.toMinutes(); // duaration minutemis
		long dien =  (long) Math.floor(minutes /(24 * 60));//sveikas dienu skaicius
		long min= minutes - dien*24*60;
		long val= (long) Math.floor( min /  60) ;//sveikas valandu skaicius
		min= min - val*60;
	
		System.out.println( String.format("%10s", au.getNumeris())  + "  " +
		                     laikas_nuo.format(datosFormatas) + "   " + 
		                     String.format("%-15s", ((dien==0) ? "" :  dien + "d. ") +  val + "v. " + min + "m. " )  +  
		                     String.format("%8.2f", kaina) + " per " + mat_vnt + "." );                     
		
	}	
	
	System.out.println( "---------------------------------------------------------------"); 
	System.out.println( "---------------------------------------------------------------"); 

	
	return ;
}// Ataskaita	


//////////////////////////////////////////////////////////////////////////////////////////////////
private static void   Ataskaita2() {

	File failas = DirFilForImp("auto_isvaziavo.json");
	////////
	if (failas==null) {
		System.err.println("Niekas dar neišvažiavo ...");
		return;
	}
	Map<String, Auto> IstorijaMap = Auto.IsvaziavoFromGson(failas);

	///////   GAUNU IVAZIAVIMO METU FIKSUOTA KAINA ///////////
	Map<Integer, Kainynas> kainynasMapCopy=Kainynas.DuokKainyna();
	int n_id=kainynasMapCopy.size();
	String mat_vnt = "v";
	double kaina= 0.00;
	
	System.out.println( ">>");
	System.out.println( ">>");
	System.out.println( "------------------------------------------------------------------------------------------------"); 
	System.out.println( "----------------------------           ATSISKAITĖ IR IŠVYKO         ----------------------------"); 
	System.out.println( "------------------------------------------------------------------------------------------------"); 
	System.out.println( "   Numeris |     Atvyko      |     Išvyko      |     Trukmė     |     Kaina      |   Suma(Eur) |"); 
	System.out.println( "------------------------------------------------------------------------------------------------"); 
	
	
	for (Auto au : IstorijaMap.values()) {
		if (n_id>0) {
			mat_vnt = kainynasMapCopy.get( au.getKainosId() ).getMatVnt();
			kaina= kainynasMapCopy.get( au.getKainosId() ).getKaina();
		}
		//////Skaiciuoju laiko intervala   ///////////
		LocalDateTime laikas_nuo=au.getPradzia();
		LocalDateTime laikas_iki=au.getPabaiga();
		
		Duration duration = Duration.between( laikas_nuo,laikas_iki );
		
		long minutes= duration.toMinutes(); // duaration minutemis
		long dien =  (long) Math.floor(minutes /(24 * 60));//sveikas dienu skaicius
		long min= minutes - dien*24*60;
		long val= (long) Math.floor( min /  60) ;//sveikas valandu skaicius
		min= min - val*60;
	
		System.out.println( String.format("%10s", au.getNumeris())  + "  " +
		                     laikas_nuo.format(datosFormatas) + "   " + 
		                     laikas_iki.format(datosFormatas) + "   " + 
		                     String.format("%-15s",((dien==0) ? "" :  dien + "d. ") +  val + "v. " + min + "m. ")  +  
		                     String.format("%8.2f", kaina) + " per " + mat_vnt + "."  + "  "  + 
		                	 String.format("%10.2f", ( Math.round( (kaina * minutes/60) * 100) / 100.00 ) )  ); 
	}	
	
	System.out.println( "---------------------------------------------------------------"); 
	System.out.println( "---------------------------------------------------------------"); 

	
	return ;
}// Ataskaita2	


//////////////////////////////////////////////////////////////////////////////////////////////////	
private static File DirFilForExp ( String fname, boolean ar_append) throws FileNotFoundException, IOException {
	File dir;
	if ((dir = new File("failai")).exists() == false) {
		dir.mkdir();
	}
	
	File fil = new File(dir + "/" + fname);
	
	if (fil.exists()) {
		if (!ar_append) fil.delete(); 
	  } else {
		fil.createNewFile();
	  }
	
	return fil;
}	
///////////////////////////////////////////////////////////////////////////////////////////////////

private static File DirFilForImp ( String fname) {
	File dir;
	if ((dir = new File("failai")).exists() == false) {
		System.err.println("Nėra direktorijos  "  + dir);
		return null;
	}
	File fil = new File(dir + "/" + fname);
	
	if (!fil.exists()) {
		System.err.println("Nėra išsaugotų duomenų   "  + fil);
		return null;
	}
	return fil;
}
	
	

}//CLASS
