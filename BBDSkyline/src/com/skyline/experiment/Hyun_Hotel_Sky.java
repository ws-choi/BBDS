package com.skyline.experiment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

public class Hyun_Hotel_Sky {
	
//	LinkedList<hyun_object> obj_list;
	
	File data_file;
	
	public Hyun_Hotel_Sky(File data_file) {
		this.data_file = data_file;
	}
		
    static double distance(hyun_object o) 
    {
    	//if query point = (0,0)
    	double distance = Math.sqrt((o.x-0)*(o.x-0) + (o.y-0)*(o.y-0));
    	
    	return distance;
    }
    
    static void print_obj_list(LinkedList<hyun_object> obj_list) 
    {
    	//System.out.println();
    	//System.out.println("object id 	price		Distance");
    	
    	java.util.Iterator<hyun_object> iterator =obj_list.iterator();
    	
    	while (iterator.hasNext())
		{
			hyun_object obj_buffer = iterator.next();
			System.out.println(obj_buffer.oid +" 		"+ obj_buffer.price+" 		"+ distance(obj_buffer));
		}
    }

	String nextline (BufferedReader br) throws IOException{
		return  br.readLine();
	}
	
	hyun_object get_hotel (String input)
	{
		String[] buf = input.split(",");
		hyun_object obj = new hyun_object();
		obj.oid = (int) Float.parseFloat(buf[0]);
		obj.x = Float.parseFloat(buf[1]);
		obj.y = Float.parseFloat(buf[2]);
		obj.price = Float.parseFloat(buf[3]); 
		
		return obj;
	}

	
    public LinkedList<hyun_object> return_skyline_obj() throws IOException 
    {
    	// 중간 스카이라인 객체를 담기위한 링크드리스트 skyline_object_list
    	LinkedList<hyun_object> skyline_object_list = new LinkedList<hyun_object>();

    	// 인풋 데이터를 스캔하기 위한 이터레이터 선언 
    	//System.out.println(obj_list);

    	//java.util.Iterator<hyun_object> outer_loop_iterator = obj_list.iterator();

    	BufferedReader outer_loop_interator = new BufferedReader(new FileReader(data_file), 256);
    	outer_loop_interator.readLine();
    	
    	// 인풋의 첫 데이터를 skyline_object_list에 삽입
    	skyline_object_list.add( get_hotel(outer_loop_interator.readLine()));
				
    	String outer_list;
    	//모든 인풋 데이터를 스캔할 때 까지 알고리즘 수행
    	while ( (outer_list = nextline(outer_loop_interator)) != null )
    	{
        	//다음 객체를 불러옴
    		hyun_object outer_obj_buffer = get_hotel(outer_list);
        	
    		//skyline_object_list의 사이즈를 불러와서 for문의 수행 횟수를 결정
    		int skyline_list_length = skyline_object_list.size();

    		for (int i=0; i < skyline_list_length; i++)
    		{
    			//skyline_object_list에서 객체를 불러옴
    			hyun_object inner_obj_buffer = (hyun_object) skyline_object_list.get(i);
    			
    			    			
    			//인풋 데이터에서 불러온 객체가 스카이라인 객체를 압도 하는지 확인
    			if (outer_obj_buffer.price <= inner_obj_buffer.price ||
    					distance(outer_obj_buffer) <= distance(inner_obj_buffer))
    			{
    				//가격과 거리가 동일한 경우 
    				if (outer_obj_buffer.price == inner_obj_buffer.price && 					
    						distance(outer_obj_buffer) == distance(inner_obj_buffer))
    				{
    					continue;
    				}

    				//가격이 동일하지만 거리가 더 클때, 혹은 거리가 동일하지만 가격이 더 큰 경우 break
    				else if (outer_obj_buffer.price >= inner_obj_buffer.price &&
    						distance(outer_obj_buffer) >= distance(inner_obj_buffer))
        			{
       				
    					break;
        			}	
    				
    				//인풋 데이터에서 불러온 객체가 스카이라인 객체를 압도 하면
    				else if (outer_obj_buffer.price < inner_obj_buffer.price &&
    						distance(outer_obj_buffer) < distance(inner_obj_buffer))
    				{  
    					//압도당하는 객체 skyline_object_list에서 삭제
    					skyline_object_list.remove(i);
    					i--;
    					skyline_list_length--;
    	    			
    					if(skyline_object_list.size()==0)
    	    			{
    	    				skyline_object_list.add(outer_obj_buffer);
    	    				break;
    	    			}
    					
    				}
    				
    				//거리는 동일하지만 인풋 객체의 가격이 더 작은경우
    				else if (outer_obj_buffer.price < inner_obj_buffer.price &&
    						distance(outer_obj_buffer) == distance(inner_obj_buffer))
    				{
    					//압도당하는 객체 skyline_object_list에서 삭제
    					skyline_object_list.remove(i);
    					i--;
    					skyline_list_length--;


    					if(skyline_object_list.size()==0)
    	    			{
    	    				skyline_object_list.add(outer_obj_buffer);
    	    				break;
    	    			}
    					
    						
    				}
    				
    				//가격은 동일하지만 인풋 객체의 거리가 더 적은경우 
    				else if (outer_obj_buffer.price == inner_obj_buffer.price &&
    						distance(outer_obj_buffer) < distance(inner_obj_buffer))
    				{
    					//압도당하는 객체 skyline_object_list에서 삭제
    					skyline_object_list.remove(i);
    					i--;
    					skyline_list_length--;


    					if(skyline_object_list.size()==0)
    	    			{
    	    				skyline_object_list.add(outer_obj_buffer);
    	    				break;
    	    			}
    					
    				}
    				
				//skyline_object_list를 다 살펴봐도 인풋 데이터가 압도 당하지 않으면
    			if (i+1 == skyline_list_length)
    				{
    					//System.out.println("added item:"+ outer_obj_buffer.oid);
    					//skyline_object_list에 추가
    					
    					skyline_object_list.add(outer_obj_buffer);
    					break;
    				}
    			    			
    			}
    			
    			//인풋 데이터에서 불러온 객체가 스카이라인 객체에 의하여 압도 당하면 break
    			else 
    				break;
    				
    		}
    	
      	}
    	//end of while loop
  
    	return skyline_object_list;
//    	print_obj_list(skyline_object_list);
//		//System.out.println("the size of skyline list" + skyline_object_list.size() + "  ");

    }
}
