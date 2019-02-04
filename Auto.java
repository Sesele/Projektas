package parkingas;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import com.google.gson.Gson;



public class Auto {
	
	String numeris;
	private LocalDateTime pradzia;
	private LocalDateTime pabaiga;
	double suma;
	int kainosId;
	

	public Auto(String numeris, int kainosId) {
		this.numeris=numeris;
		this.pradzia = LocalDateTime.now().minusHours(25).minusMinutes(1).minusSeconds(0);
		this.kainosId=kainosId;
	}
	
	public String getNumeris() {
			return numeris;
	}
	public LocalDateTime getPradzia() {
		return pradzia;
	}
	public LocalDateTime getPabaiga() {
		return pabaiga;
	}

	public int getKainosId() {
			return kainosId;
	}
	///////
	
	public void setPabaiga(LocalDateTime pabaiga ) {
		this.pabaiga = pabaiga;
	}
	public void setKainosId(int kainosId ) {
		this.kainosId = kainosId;
	}
	public void setSuma(double suma ) {
		this.suma = suma;
	}
	///////
	@Override
	public String toString() {
		return  String.format("%10s", numeris) + " -  " + pradzia + "  - "  + pabaiga + " -   " 	+ suma ;
		
	}
	
	///////
	
	// Rimantas
	// Rimantas 1
	// Rimantas 2
	// Rimantas 3
	
	
	
	
	
	private static Map<String, Auto> autoMap;
	
	/////////   Nuskenuotas naujas Auto numeris   ////////////////////////
	
	public static  void  NaujasAuto(String nr,int n_id) {
		if (autoMap == null) {
			autoMap = new HashMap<>();
		}	
		Auto au = new Auto(nr,n_id);
		autoMap.put(au.getNumeris(), au);
		
		return ;
	}
	/// Istrinu isvaziavusi auto
	public static  void  IstrinuAuto(String nr) {
		autoMap.remove(nr);
		return ;
	}
	///
	
	
	
	//// Grazinu dabar  besiparkuojanciu Auto sarasa
	public static final Map<String, Auto> DuokNumeriuSarasa() {

		Map<String, Auto> autoMapCopy = new HashMap<>();
		if (autoMap != null) {
			for (Auto au: autoMap.values()) {
				autoMapCopy.put(au.getNumeris(), au);
			}
		}
		//grazinu isrusiuota
		Map<String, Auto> autoMapCopySorted = new TreeMap<>(autoMapCopy);
		
		
		
		return autoMapCopySorted;
	}
	
	////////////////////////////////////////////////////////
	//FROM GSON///
	public static  void ParkuojasiFromGson(File failas) {
		Gson gson = new Gson();

		try (BufferedReader reader = new BufferedReader(new FileReader(failas))) {
			String line;
    	
			while ((line = reader.readLine()) != null) {
				if (autoMap == null) {
					autoMap = new HashMap<>();
				}	
				System.out.println(line);
				Auto au = gson.fromJson(line, Auto.class);
				autoMap.put(au.getNumeris(), au);
			}
	    	
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
		return;
	
	}
////////////////////////////////////////////////////////
//FROM GSON ISTORIJA ATASKAITAI ///
public static  Map<String, Auto> IsvaziavoFromGson(File failas) {
	Gson gson = new Gson();
	Map<String, Auto> istorijaMap = new TreeMap<>();

	try (BufferedReader reader = new BufferedReader(new FileReader(failas))) {
	String line;
	
	while ((line = reader.readLine()) != null) {
	
		Auto au = gson.fromJson(line, Auto.class);
		istorijaMap.put(au.getNumeris(), au);
	}
	
	} catch (IOException e) {
		e.printStackTrace();
	}
	return istorijaMap;

}
////////////////////////////////////////////////////////////////////////////////////////	
	
}// KLASS	
	
	
