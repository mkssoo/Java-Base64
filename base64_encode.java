////////////////////////////////////////////
//プログラム名:base64_encode.java         //
//実行:java base64_encode [引数1] [引数2] //
//引数1:エンコードする画像                //
//引数2:出力ファイル名                    //
////////////////////////////////////////////

import java.io.*;
public class base64_encode {
	//メインメソッド
	public static void main(String[] args) throws IOException {

		long start, end; //実行時間計測用変数
		start = System.currentTimeMillis(); //計測開始
		//バイナリファイルを読み込むためにFileInputStreamを使う
		FileInputStream in = new FileInputStream(args[0]);
		StringBuilder sb = new StringBuilder("");
		StringBuilder bin = new StringBuilder("");
		int c;

		//バイナリファイルを読み込む
		while((c = in.read()) != -1){
			sb.delete(0, sb.length()); //中身を削除
			sb.append(Integer.toBinaryString(c)); //2進数の文字列に変換
			if(sb.length()<8){ //8bitかどうか判定
				for(int f = sb.length();f<8;f++){ //8bitになるように"0"を加える
					sb.insert(0,"0"); //左側から"0"を加える
				}
			}
			//binに加える
			bin.append(sb);
		}
		in.close();

		//文字数カウント
		int len = bin.length();

		int cut = len / 24; //商
		int r   = len % 24; //剰余

		//3byte(24bit)ずつ分割し、配列に格納
		String[] data = new String[cut+1];

		data[0] = bin.substring(0,24);

		int j;
		for(j=1;j<cut;j++){
			data[j] = bin.substring(24*j, 24*(j+1));
		}

		//残ったもの(剰余)を格納
		data[cut] = bin.substring(24*cut, len);

		//tmp.txtに3byte(24bit)ずつ区切ったものを書き込む(そのたびに改行する)
		BufferedWriter tmp_write = new BufferedWriter(new FileWriter("tmp.txt"));
		for(int x=0;x<data.length;x++){
			tmp_write.write(data[x]+"\n");
		}
		tmp_write.close();

		//tmp.txtは24bitずつ改行されている、tmp2.txtに6bitずつ区切り、
		//それぞれの先頭に"00"を加えたものを書き込む
		BufferedReader tmp_read  = new BufferedReader(new FileReader("tmp.txt"));
		BufferedWriter tmp2_write = new BufferedWriter(new FileWriter("tmp2.txt"));
		String t,h;
		String point=""; //=,==を代入する変数
		//24bitを6bitずつ区切り、"00"を加える
		String[] array = new String[4];
		while((t = tmp_read.readLine()) != null){

			//3byte(24bit)かどうか判断
			String d;
			if(t.length()==24){
				for(int x=0;x<t.length();x+=6){
					d = t.substring(x,x+6);
					tmp2_write.write("00"+d);
				}
				tmp2_write.write("\n");

			}else{ //24bitではない場合、最終行のみ該当する
				tmp2_write.close(); //最終行処理のため、閉じる
				//最終行処理
				endLine(t,array);

				if(t.length()==24){ //残りがない場合、"===="をつける
					point = "====";
				}
				if(t.length()==16){ //残り2byte(16bit)の場合、"="をつける
					point = "=";
				}
				if(t.length()==8){ //残り1byte(8bit)の場合、"=="をつける
					point = "==";
				}
			}

		}
		tmp_read.close();

		//tmp2.txtのファイルを読み込み、tmp3.txtに10進数変換されたものを書き込む
		BufferedReader tmp2_read = new BufferedReader(new FileReader("tmp2.txt"));
		BufferedWriter tmp3_write = new BufferedWriter(new FileWriter("tmp3.txt"));
		int s;
		while((t = tmp2_read.readLine()) != null){
			for(int z = 0;z<t.length();z+=8){
				s = Integer.parseInt(t.substring(z,z+8),2);
				tmp3_write.write(s+"\n");
			}
		}
		tmp3_write.close();
		tmp2_read.close();

		//tmp3.txtを読み込み、対応付けられた文字に変換していき、引数2に書き込む
		BufferedReader tmp3_read = new BufferedReader(new FileReader("tmp3.txt"));
		BufferedWriter f_write = new BufferedWriter(new FileWriter(args[1]));
		int count;

		count = 1;
		Encode encode = new Encode();
		while((t = tmp3_read.readLine()) != null){
			h = encode.ascii_encode(t);
			if(count==64){
				f_write.write(h+"\n");
				count = 1;
			}else{
				f_write.write(h);
				count++;
			}
		}
		f_write.write(point);
		tmp3_read.close();
		f_write.close();

		end = System.currentTimeMillis();
		System.out.println("実行時間:"+(end-start)+"ミリ秒");
	}
	//ここまでメインメソッド

	//最終行専用メソッド
	static void endLine(String s, String[] method_array) throws IOException{
		//tmp2.txtに追記する
		BufferedWriter method_write = new BufferedWriter(new FileWriter("tmp2.txt",true));
		int num = 0;
		int q = s.length() / 6; //商
		int r = s.length() % 6; //剰余

		int i;
		for(i=0; i<q*6; i+=6){
			method_array[num] = s.substring(i,i+6);
			num++;
		}

		//余ったものを格納
		method_array[num] = s.substring(q*6,q*6+r);

		//6bitになるように右側に"0"を加える
		int j;
		for(j=method_array[num].length(); j<6; j++){
			method_array[num] = method_array[num] + "0";
		}

		//配列の中身をファイルに書き込む
		int k;
		for(k=0; k<=num; k++){
			method_write.write("00" + method_array[k]);
		}
		method_write.close();
	}
	//ここまで最終行メソッド
}

