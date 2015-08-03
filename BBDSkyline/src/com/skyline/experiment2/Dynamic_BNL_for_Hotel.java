package com.skyline.experiment2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;

import data.Point_Float;

public class Dynamic_BNL_for_Hotel {
		
	File data_file;
	
	public Dynamic_BNL_for_Hotel(File data_file) {
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
 
    		boolean isSkyline = true;
    		LinkedList<Hotel_object> list = new LinkedList<Hotel_object>();
    		
    		for (int i=0; i < skyline_list_length; i++)
    		{
    			//skyline_object_list에서 객체를 불러옴
    			Hotel_object inner = (Hotel_object) candidates.get(i);
    			
    			
    			//시작

    			if(outer.price > inner.price && outer.dis > inner.dis) {
    			
    				isSkyline = false;
    				break;       
    			}
    			
    			else if(outer.price < inner.price && outer.dis < inner.dis) 
    				list.add(inner);
    			
    			else continue;
    				    			
    		}
    		
    		Iterator<Hotel_object> iter = list.iterator();
    		
    		while(iter.hasNext()) candidates.remove(iter.next());
    	
    		if(isSkyline) candidates.add(outer);
    		
      	}

    	outer_loop_interator.close();
    	outer_loop_interator = null;
  
    	return candidates;

    }

	


}
