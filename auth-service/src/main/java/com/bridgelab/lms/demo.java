package com.bridgelab.lms;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class demo {

    public static String toSortedlog(String date, String time){

        String[] splitdate = date.split("-");

        String DD = splitdate[0];
        String MM = splitdate[1];
        String YYYY = splitdate[2];

        String[] splittime = time.split(":");

        String hh = splittime[0];
        String mm = splittime[1];

        return YYYY+MM+DD+hh+mm;

    }

    public static void main(String[] args) {


        List<List<String>> ls = Arrays.asList(
                Arrays.asList("02-03-2023", "12:00", "INFO", "User login"),
                Arrays.asList("02-03-2023", "14:00", "CRITICAL", "Unauthorized access"),
                Arrays.asList("01-03-2023", "10:30", "WARN", "Disk space low"),
                Arrays.asList("02-03-2023", "09:00", "ERROR", "Server crash"),
                Arrays.asList("01-03-2023", "10:30", "WARN", "Disk space low again")
        );



        List<List<String>> filterLog = new ArrayList<>();

        for(List<String> x: ls){
            String status = x.get(2);
            System.out.println(status);

            if(status.equals("ERROR") || status.equals("CRITICAL")){
                filterLog.add(x);
            }

        }

        System.out.println(filterLog);

        filterLog.sort(Comparator.comparing(
                log -> toSortedlog( log.get(0), log.get(1)),
                Comparator.reverseOrder()
        )
        );

        for(List<String> x: filterLog){
            System.out.println(x);
        }








    }
}
