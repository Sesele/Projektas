package parkingas;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.TreeMap;

import com.google.gson.Gson;

public class Kainynas {
	
	private int id;
	private String matVnt;
	private double kaina;
	private LocalDateTime kainaNuo;
	//
	public Kainynas( String matVnt, double kaina) {
		this.id= kainynasMap.size() + 1 ;
		this.kaina=kaina;
		this.matVnt=matVnt;
		this.kainaNuo= LocalDateTime.now();
	}
	//
	public int getId() {
		return id;
	}
	//
	public String getMatVnt() {
		return matVnt;
	}
	//
	public double getKaina() {
		return kaina;
	}
	//
	private static Map <Integer, Kainynas> kainynasMap ;
	//
	@Override
	public String toString() {
		return  id + "  " + matVnt + "  " + kaina + "  " + kainaNuo ;
	}
	//
	////////////////////////////////////////////////////////
	//FROM GSON///
	public static  void KainaFromGson(File failas) {
		Gson gson = new Gson();

		try (BufferedReader reader = new BufferedReader(new FileReader(failas))) {
			String line;
    	
			while ((line = reader.readLine()) != null) {
				if (kainynasMap == null) {
					kainynasMap = new TreeMap<>();
				}	
				System.out.println(line);
				Kainynas au = gson.fromJson(line, Kainynas.class);
				kainynasMap.put(au.getId(), au);
			}
	    	
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
		return;
	
	}
	
	//////////////////////////////////////// Grazinu kainyna
	public static final Map<Integer, Kainynas> DuokKainyna() {

		Map<Integer, Kainynas> kainynasMapCopy = new TreeMap<>();
		if (kainynasMap != null) {
			for (Kainynas kainyn: kainynasMap.values()) {
				kainynasMapCopy.put(kainyn.getId(), kainyn);
			}
		}
		
		return kainynasMapCopy;
	}
	
	////////////////////////////////////////////////////////
	
	public static  void  NaujaKaina(double kaina) {
		if (kainynasMap == null) {
			kainynasMap = new TreeMap<>();
		}	
		Kainynas kainyn = new Kainynas("v", kaina);
		kainynasMap.put(kainyn.getId(), kainyn);
		
		return ;
	}
	
////////////////////////////////////////////////////////////////////////////////////////
	
	
	
	
	
	
}
