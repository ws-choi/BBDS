package com.skyline.experiment2;

import index.ExternalSort;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.skyline.experiment2.Hotel_object;

import data.Point_Float;

public class Dynamic_ANTILESS_for_Hotel {
		
	File data_file;
	
	public Dynamic_ANTILESS_for_Hotel(File data_file) {
		this.data_file = data_file;
	}
		

	String nextline (BufferedReader br) throws IOException{
		return  br.readLine();
	}
	
	Hotel_object get_hotel (String input, Point_Float point)
	{
		String[] buf = input.split(",");
		Hotel_object obj = new Hotel_object();
		obj.oid = (int) Float.parseFloat(buf[0]);
		obj.x = Float.parseFloat(buf[1]);
		obj.y = Float.parseFloat(buf[2]);
		obj.dis = distance(obj.x,obj.y, point);
		obj.price = Float.parseFloat(buf[3]); 
		
		return obj;
	}

	
    private float distance(float x, float y, Point_Float point) {
    	
    	float distance = (float) Math.sqrt((x-point.x)*(x-point.x) + (y-point.y)*(y-point.y));
    	return distance;
	}

	public LinkedList<Hotel_object> return_skyline_obj (Point_Float point) throws IOException 
    {
		
		sortFile(data_file);
		
    	// 중간 스카이라인 객체를 담기위한 링크드리스트 skyline_object_list
    	LinkedList<Hotel_object> candidates = new LinkedList<Hotel_object>();

    	BufferedReader outer_loop_interator = new BufferedReader(new FileReader(data_file), 256);
    	
    	// 인풋의 첫 데이터를 skyline_object_list에 삽입
    	candidates.add( get_hotel(outer_loop_interator.readLine(), point));
				
    	String outer_list;
    	//모든 인풋 데이터를 스캔할 때 까지 알고리즘 수행
    	while ( (outer_list = nextline(outer_loop_interator)) != null )
    	{
        	//다음 객체를 불러옴
    		Hotel_object outer = get_hotel(outer_list, point);
        	
    		int skyline_list_length = candidates.size();
 
    		boolean isSkyline;
    		
    		if(outer.dis < candidates.getFirst().dis)
    		{
    			isSkyline = true;
    		}
    		
//    		if(outer.dis < candidates.getFirst().dis && outer.price < candidates.getFirst().price)
    		else{

    			isSkyline = true;
    			
    			for (int i=0; i < skyline_list_length; i++)
    			{
    			
    				//skyline_object_list에서 객체를 불러옴
    				Hotel_object inner = (Hotel_object) candidates.get(i);
    				
    				
    				if( inner.dis <= outer.dis )
    				{
        				if(inner.dis < outer.dis) {
        					
        					isSkyline = false; // since outper.price >= inner.price
        					break;       
        				}
        				
        				else if (outer.dis == inner.dis)
        				{
        				
        					if (outer.price > inner.dis){
        						isSkyline = false;
    	    					break;       
    	    				}
    	    				else
    	    					break;
        					
        				}
    				}
    				

    				else
    					break;
	    			    				
    				
    				    				    			
    			}
    		
   
    		}
    			if(isSkyline) candidates.add(outer);
    		
    		sort(candidates);
      	}

    	outer_loop_interator.close();
  
    	return candidates;

    }

	private void sort(LinkedList<Hotel_object> candidates) {
		Collections.sort(candidates, new Comparator<Hotel_object>() {

			@Override
			public int compare(Hotel_object o1, Hotel_object o2) {
				
				if(o1.dis > o2.dis)
					return 1;
				
				else if (o1.dis < o2.dis)
					return -1;
				
				return 0;
			}
		});
		
	}
	
	public void sortFile (File file) throws IOException{

		Comparator<String> cmp = new Comparator<String>() {
			
			@Override
			public int compare(String o1, String o2) {
			
				String[] array_01 = o1.split(",");
				String[] array_02 = o2.split(",");
			
				float f_01 = Float.parseFloat(array_01[3])   ;
				float f_02 = Float.parseFloat(array_02[3])   ;
			
				if(f_01 < f_02)
					return -1;
				
				else if (f_01 > f_02)
					return +1;
				
				return 0;
			}
		};


		List<File> a = ExternalSort.sortInBatch(file , cmp);
		
		ExternalSort.mergeSortedFiles(a, file, cmp);

	}
}
