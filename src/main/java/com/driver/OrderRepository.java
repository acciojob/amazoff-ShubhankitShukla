package com.driver;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Repository
public class OrderRepository {

    HashMap<String,Order> r = new HashMap<>();
    HashMap<String,DeliveryPartner> p = new HashMap<>();
    HashMap<String, List<String>> st = new HashMap<>();
    HashMap<String,Integer> ass = new HashMap<>();

    public void addOrder(Order order)
    {


        r.put(order.getId(),order);
    }

    public void addPartner(String id)
    {
        DeliveryPartner deliveryPartner = new DeliveryPartner(id);
        p.put(id,deliveryPartner);
    }

    public void addOrderPartnerPair(String orderId,String partnerId)
    {
        if(r.containsKey(orderId) && p.containsKey(partnerId))
        {
            r.put(orderId,r.get(orderId));
            p.put(partnerId,p.get(partnerId));
            if(st.containsKey(partnerId))
            {
                List<String> temp = st.get(partnerId);
                temp.add(orderId);
                st.put(partnerId,temp);
            }
            else {
                List<String> temp = new ArrayList<>();
                temp.add(orderId);
                st.put(partnerId,temp);
            }
            ass.put(orderId,1);
        }
    }

    public Order getOrderById(String orderId)
    {
        if(r.containsKey(orderId))
        {
            return r.get(orderId);
        }

        return null;
    }

    public DeliveryPartner getPartnerById(String partnerId)
    {
        if(p.containsKey(partnerId))
        {
            return p.get(partnerId);
        }

        return null;
    }

    public int getOrderCountByPartnerId(String partnerId)
    {
        if(st.containsKey(partnerId))
        {
            return st.get(partnerId).size();
        }

        return 0;
    }

    public List<String> getOrdersByPartnerId(String partnerId)
    {
        if(st.containsKey(partnerId))
        {
            return st.get(partnerId);
        }

        return new ArrayList<>();
    }

    public List<String> getAllOrders()
    {
        List<String> temp = new ArrayList<>();
        for(String i:r.keySet())
        {
            temp.add(i);
        }

        return temp;
    }

    public int getCountOfUnassignedOrders()
    {
        int count =0;
        for(String i:ass.keySet())
        {
            if(ass.get(i)!=1)
            {
                count++;
            }
        }

        return count;
    }

    public int getOrdersLeftAfterGivenTimeByPartnerId(String time,String partnerId)
    {
        String []temp = time.split(":");
        int hour = Integer.valueOf(temp[0]);
        int min = Integer.valueOf(temp[1]);
        int givenTime = (60*hour)+min;

        List<String> list = st.get(partnerId);
        int count =0;
        for(String i:list)
        {
            Order order = r.get(i);
            int delTime = order.getDeliveryTime();
            if(delTime>givenTime)
            {
                count++;
            }
        }

        return count;
    }

    public String getLastDeliveryTimeByPartnerId(String partnerId)
    {
        if(st.containsKey(partnerId))
        {
            List<String> temp = st.get(partnerId);
            Order lastOrder = r.get(temp.get(temp.size()-1));
            int lastTime = lastOrder.getDeliveryTime();
            int r = lastTime%60;
            int q = lastTime/60;
            String hour = String.valueOf(q);
            String min = String.valueOf(r);
            String timeFormat = hour+":"+min;

            return timeFormat;
        }

        return "";
    }

    public void deletePartnerById(String partnerId)
    {
        List<String> list = st.get(partnerId);
        for(String i:list)
        {
            ass.put(i,0);
        }

        if(p.containsKey(partnerId))
        {
            p.remove(partnerId);
        }

        st.remove(partnerId);
    }

    public void deleteOrderById(String orderId)
    {
        for(String i:st.keySet())
        {
            List<String> list = st.get(i);
            for(String j:list)
            {
                if(orderId.equals(j))
                {
                    //remove from deliveryboy list
                    list.remove(j);
                    //remove from orders
                    r.remove(j);
                    //remove from assigened hashmap
                    ass.remove(j);
                }
            }
        }
    }
}
