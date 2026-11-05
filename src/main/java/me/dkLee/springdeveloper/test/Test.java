package me.dkLee.springdeveloper.test;

import java.util.ArrayList;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        List<Integer> nums = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
//        List<Integer> result = new ArrayList<>();
//        for (int i : nums) {
////            System.out.println(i);
//            if ( i % 2 == 0) {
//                result.add(i * 2);
//
//            }
//        }
//        System.out.println(result);

//        stream은 리스트 같을걸 데이터 흐름으로 변환
//        filter는 조건에 맞는 요소만 골라내는 중간 연산 매서드
//        map은 스트림안의 각 요소를 다른 값으로 변환하는 중간 연산 매서드
//        toList는 스트림형태의 데이터 처리를 마무리하고, 결과를 새로운 List 형태로 수집하는 최종 연산 매서드
        List<Integer> result = nums.stream().filter(n -> n % 2 == 0).map(n -> n * 2).toList();
        System.out.println(result);
    }
}
