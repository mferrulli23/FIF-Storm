package com.fuzzystream;

import java.util.Scanner;


import com.fuzzystream.fif_core.FuzzySet;

public class ProfileFactory implements Factory {

	private static final int EXISTING_PROFILE_ID = 1;
	private static final int NEW_PROFILE_ID = 2;
	private static final int PROFILE_A_ID = 1;
	private static final int PROFILE_B_ID = 2;
	private static final int PROFILE_C_ID = 3;
	private static final int PROFILE_D_NEW_ID = 4;
	
	public Object getInstance(int x) {
		assert(x >= PROFILE_A_ID && x <= PROFILE_D_NEW_ID);
		AbstractProfile profile = null;
		if(x == PROFILE_A_ID){
			try {
				ProfileA pA = ProfileA.getInstanceProfileA();
				profile = pA;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else if(x == PROFILE_B_ID){
			try{
				ProfileB pB = ProfileB.getInstanceProfileB();
				profile = pB;
			} catch (Exception e){
				e.printStackTrace();
			}	
		}
		else if(x == PROFILE_C_ID){
			try {
				ProfileC pC = ProfileC.getInstanceProfileC();
				profile = pC;
			} catch (Exception e) {
				e.printStackTrace();
			}
		
		}
		else if(x == PROFILE_D_NEW_ID){
			ProfileD pD;
			try {
				pD = ProfileD.getInstanceProfileD();
				profile = pD;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		return profile;
	}
	
	public int profileSelection(){
		System.out.println("**Please select an option:**");
    	System.out.println("1) Load a existing demo user profile");
    	System.out.println("2) Create a new user profile");
    	System.out.println("Enter 1 or 2...");
    	Scanner in = new Scanner(System.in);
    	int option = in.nextInt();
    	while (option != EXISTING_PROFILE_ID && option != NEW_PROFILE_ID){
    		System.out.println("Please insert a correct choice (1 or 2): ");
    		option = in.nextInt();
    	}
    	if(option == EXISTING_PROFILE_ID)
    		return loadExistingProfile();
    	else if(option == NEW_PROFILE_ID)
    		return PROFILE_D_NEW_ID;
    	return -1;
    		
	}
	
	private int loadExistingProfile(){
		System.out.println("Choose an existing profile:");
		System.out.println("Profile A: [PRESS 1]");
		System.out.println("Action = 0.8, Thriller = 0.7, Mystery = 0.5");
		System.out.println("1986 = 0.4, 1997 = 0.8");
		System.out.println("");
		System.out.println("Profile B: [PRESS 2]");
		System.out.println("2: Animation = 0.8, Comedy = 0.7, Adventure = 0.5");
		System.out.println("No preference about year.");
		System.out.println("");
		System.out.println("Profile C: [PRESS 3]");
		System.out.println("No preference about genre.");
		System.out.println("1983 = 0.4, 1998 = 0.8");
		System.out.println("");
		System.out.println("Please insert profile number: ");
		Scanner in = new Scanner(System.in);
		int scelta = in.nextInt();
		while (scelta != PROFILE_A_ID && scelta != PROFILE_B_ID && scelta != PROFILE_C_ID){
    		System.out.println("Please insert a correct choice (1 or 2 or 3): ");
    		scelta = in.nextInt();
    	}
		
		return scelta;
		
	}
	
	//public int createNewProfile(){
	//	return PROFILE_D_NEW_ID;
	//}

}
