class Encode {
	static String ascii_encode(String s){
		String get = "";
		switch( Integer.parseInt(s, 10) ){
			case  0: get = "A"; break;
			case  1: get = "B"; break;
			case  2: get = "C"; break;
			case  3: get = "D"; break;
			case  4: get = "E"; break;
			case  5: get = "F"; break;
			case  6: get = "G"; break;
			case  7: get = "H"; break;
			case  8: get = "I"; break;
			case  9: get = "J"; break;
			case 10: get = "K"; break;
			case 11: get = "L"; break;
			case 12: get = "M"; break;
			case 13: get = "N"; break;
			case 14: get = "O"; break;
			case 15: get = "P"; break;
			case 16: get = "Q"; break;
			case 17: get = "R"; break;
			case 18: get = "S"; break;
			case 19: get = "T"; break;
			case 20: get = "U"; break;
			case 21: get = "V"; break;
			case 22: get = "W"; break;
			case 23: get = "X"; break;
			case 24: get = "Y"; break;
			case 25: get = "Z"; break;
			case 26: get = "a"; break;
			case 27: get = "b"; break;
			case 28: get = "c"; break;
			case 29: get = "d"; break;
			case 30: get = "e"; break;
			case 31: get = "f"; break;
			case 32: get = "g"; break;
			case 33: get = "h"; break;
			case 34: get = "i"; break;
			case 35: get = "j"; break;
			case 36: get = "k"; break;
			case 37: get = "l"; break;
			case 38: get = "m"; break;
			case 39: get = "n"; break;
			case 40: get = "o"; break;
			case 41: get = "p"; break;
			case 42: get = "q"; break;
			case 43: get = "r"; break;
			case 44: get = "s"; break;
			case 45: get = "t"; break;
			case 46: get = "u"; break;
			case 47: get = "v"; break;
			case 48: get = "w"; break;
			case 49: get = "x"; break;
			case 50: get = "y"; break;
			case 51: get = "z"; break;
			case 52: get = "0"; break;
			case 53: get = "1"; break;
			case 54: get = "2"; break;
			case 55: get = "3"; break;
			case 56: get = "4"; break;
			case 57: get = "5"; break;
			case 58: get = "6"; break;
			case 59: get = "7"; break;
			case 60: get = "8"; break;
			case 61: get = "9"; break;
			case 62: get = "+"; break;
			case 63: get = "/"; break;
			default: get = " ";
		}
		return get;
	}

}

