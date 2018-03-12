////////////////////////////////////////////
//�ץ����̾:base64_encode.java         //
//�¹�:java base64_encode [����1] [����2] //
//����1:���󥳡��ɤ������                //
//����2:���ϥե�����̾                    //
////////////////////////////////////////////

import java.io.*;
public class base64_encode {
	//�ᥤ��᥽�å�
	public static void main(String[] args) throws IOException {

		long start, end; //�¹Ի��ַ�¬���ѿ�
		start = System.currentTimeMillis(); //��¬����
		//�Х��ʥ�ե�������ɤ߹��ि���FileInputStream��Ȥ�
		FileInputStream in = new FileInputStream(args[0]);
		StringBuilder sb = new StringBuilder("");
		StringBuilder bin = new StringBuilder("");
		int c;

		//�Х��ʥ�ե�������ɤ߹���
		while((c = in.read()) != -1){
			sb.delete(0, sb.length()); //��Ȥ���
			sb.append(Integer.toBinaryString(c)); //2�ʿ���ʸ������Ѵ�
			if(sb.length()<8){ //8bit���ɤ���Ƚ��
				for(int f = sb.length();f<8;f++){ //8bit�ˤʤ�褦��"0"��ä���
					sb.insert(0,"0"); //��¦����"0"��ä���
				}
			}
			//bin�˲ä���
			bin.append(sb);
		}
		in.close();

		//ʸ�����������
		int len = bin.length();

		int cut = len / 24; //��
		int r   = len % 24; //��;

		//3byte(24bit)����ʬ�䤷������˳�Ǽ
		String[] data = new String[cut+1];

		data[0] = bin.substring(0,24);

		int j;
		for(j=1;j<cut;j++){
			data[j] = bin.substring(24*j, 24*(j+1));
		}

		//�Ĥä����(��;)���Ǽ
		data[cut] = bin.substring(24*cut, len);

		//tmp.txt��3byte(24bit)���Ķ��ڤä���Τ�񤭹���(���Τ��Ӥ˲��Ԥ���)
		BufferedWriter tmp_write = new BufferedWriter(new FileWriter("tmp.txt"));
		for(int x=0;x<data.length;x++){
			tmp_write.write(data[x]+"\n");
		}
		tmp_write.close();

		//tmp.txt��24bit���Ĳ��Ԥ���Ƥ��롢tmp2.txt��6bit���Ķ��ڤꡢ
		//���줾�����Ƭ��"00"��ä�����Τ�񤭹���
		BufferedReader tmp_read  = new BufferedReader(new FileReader("tmp.txt"));
		BufferedWriter tmp2_write = new BufferedWriter(new FileWriter("tmp2.txt"));
		String t,h;
		String point=""; //=,==�����������ѿ�
		//24bit��6bit���Ķ��ڤꡢ"00"��ä���
		String[] array = new String[4];
		while((t = tmp_read.readLine()) != null){

			//3byte(24bit)���ɤ���Ƚ��
			String d;
			if(t.length()==24){
				for(int x=0;x<t.length();x+=6){
					d = t.substring(x,x+6);
					tmp2_write.write("00"+d);
				}
				tmp2_write.write("\n");

			}else{ //24bit�ǤϤʤ���硢�ǽ��ԤΤ߳�������
				tmp2_write.close(); //�ǽ��Խ����Τ��ᡢ�Ĥ���
				//�ǽ��Խ���
				endLine(t,array);

				if(t.length()==24){ //�Ĥ꤬�ʤ���硢"===="��Ĥ���
					point = "====";
				}
				if(t.length()==16){ //�Ĥ�2byte(16bit)�ξ�硢"="��Ĥ���
					point = "=";
				}
				if(t.length()==8){ //�Ĥ�1byte(8bit)�ξ�硢"=="��Ĥ���
					point = "==";
				}
			}

		}
		tmp_read.close();

		//tmp2.txt�Υե�������ɤ߹��ߡ�tmp3.txt��10�ʿ��Ѵ����줿��Τ�񤭹���
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

		//tmp3.txt���ɤ߹��ߡ��б��դ���줿ʸ�����Ѵ����Ƥ���������2�˽񤭹���
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
		System.out.println("�¹Ի���:"+(end-start)+"�ߥ���");
	}
	//�����ޤǥᥤ��᥽�å�

	//�ǽ������ѥ᥽�å�
	static void endLine(String s, String[] method_array) throws IOException{
		//tmp2.txt���ɵ�����
		BufferedWriter method_write = new BufferedWriter(new FileWriter("tmp2.txt",true));
		int num = 0;
		int q = s.length() / 6; //��
		int r = s.length() % 6; //��;

		int i;
		for(i=0; i<q*6; i+=6){
			method_array[num] = s.substring(i,i+6);
			num++;
		}

		//;�ä���Τ��Ǽ
		method_array[num] = s.substring(q*6,q*6+r);

		//6bit�ˤʤ�褦�˱�¦��"0"��ä���
		int j;
		for(j=method_array[num].length(); j<6; j++){
			method_array[num] = method_array[num] + "0";
		}

		//�������Ȥ�ե�����˽񤭹���
		int k;
		for(k=0; k<=num; k++){
			method_write.write("00" + method_array[k]);
		}
		method_write.close();
	}
	//�����ޤǺǽ��ԥ᥽�å�
}

