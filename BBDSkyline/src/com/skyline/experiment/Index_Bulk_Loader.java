package com.skyline.experiment;

import index.basic_ds.SortedLinList;
import index.rtree.dimitris.Data;
import index.rtree.dimitris.RTree;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;



public class Index_Bulk_Loader {

	File file;
	BufferedReader br;
	RTree tree;
	
	public Index_Bulk_Loader(String path) throws IOException {

		this(new File(path));
	}

	
	public Index_Bulk_Loader(File input) throws IOException {

		file = input;
		
		br = new BufferedReader(new FileReader(file));
		
		String filename = "temp.r";
		get_a_new_file(filename);
		tree = new RTree("temp.r", 256, 0, 3);

		bulk_loader();

	}
	
	private void get_a_new_file(String filename) throws IOException {
		
		File f = new File(filename);
		if(f.exists()) f.delete();
	}


	public RTree getTree () { return tree;}
	private void bulk_loader() throws IOException {

		String line;
		
		br.readLine();
		
		while( (line = br.readLine()) != null )
		{
			String[] buf = line.split(",");
			Data tuple = new Data(3);
			
			tuple.id = Integer.parseInt(buf[0]);
			tuple.data[0] = tuple.data[1] = Float.parseFloat(buf[1]);
			tuple.data[2] = tuple.data[3] = Float.parseFloat(buf[2]);
			tuple.data[4] = tuple.data[5] = Float.parseFloat(buf[3]);
			

			tree.insert(tuple);
		}
		
		//System.out.println(tree.skyline().get_num());
		
/*		SortedLinList Result_Set = tree.skyline();
		
		while(Result_Set.get_num() > 0){
//			System.out.println((Data)Result_Set.get_first());
			Data data = ((Data)Result_Set.get_first());
			
			String buf = data.id + "," + data.data[0]+","+data.data[3] ;
			System.out.println(buf);
			Result_Set.erase();
		}
*/
	}
	
	public static void main(String[] args) throws IOException {
		
		String path = "E:/workspace_R/";
		File file = new File(path+"final_anti_real_out.txt");
		BufferedWriter bw = new BufferedWriter( new FileWriter(file));
		Index_Bulk_Loader ibl = new Index_Bulk_Loader(path+"final_anti_real.txt");
		
		RTree tree = ibl.getTree();

		SortedLinList Result_Set = tree.skyline();
		
		bw.write("\"ID\",\"Price\",\"Distance\"\n");
		while(Result_Set.get_num() > 0){
//			System.out.println((Data)Result_Set.get_first());
			Data data = ((Data)Result_Set.get_first());
			
			String buf = data.id + "," + data.data[0]+","+data.data[3] +"\n";
		//	System.out.println(buf);
			bw.write(buf);
			Result_Set.erase();
		}
		
		bw.close();
	}
	
}
