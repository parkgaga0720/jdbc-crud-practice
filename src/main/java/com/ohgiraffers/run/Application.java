package com.ohgiraffers.run;

import com.ohgiraffers.model.dao.EmployeeDAO;
import com.ohgiraffers.model.dto.EmployeeDTO;

import java.sql.Connection;
import java.util.List;
import java.util.Scanner;

import static com.ohgiraffers.common.JDBCTemplate.*;

public class Application {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Connection con = getConnection();
        EmployeeDAO empDAO = new EmployeeDAO();

        while (true) {
            System.out.println("====== 직원 관리 프로그램 ======");
            System.out.println("1. 직원 전체 조회");
            System.out.println("2. 직원 상세 조회");
            System.out.println("3. 직원 등록");
            System.out.println("4. 직원 수정");
            System.out.println("5. 직원 삭제");
            System.out.println("0. 종료");
            System.out.print("메뉴 선택: ");
            int menu = sc.nextInt();
            sc.nextLine();

            try {
                switch (menu) {
                    case 1:
                        List<EmployeeDTO> empList = empDAO.selectAllEmp(con);
                        for (EmployeeDTO emp : empList) {
                            System.out.println(emp.getEmpId() + " " + emp.getEmpName());
                        }
                        break;

                    case 2:
                        System.out.print("조회할 사번 : ");
                        String empId = sc.nextLine();
                        EmployeeDTO emp = empDAO.selectByIdEmp(con, empId);
                        if (emp != null) {
                            System.out.println("==========");
                            System.out.println("사번 " + emp.getEmpId());
                            System.out.println("이름 " + emp.getEmpName());
                            System.out.println("이메일 " + emp.getEmail());
                            System.out.println("전화번호 " + emp.getPhone());
                            System.out.println("부서코드 " + emp.getDeptCode());
                            System.out.println("직급코드 " + emp.getJobCode());
                            System.out.println("급여등급 " + emp.getSalLevel());
                            System.out.println("급여 " + emp.getSalary());
                            System.out.println("보너스 " + emp.getBonus());
                            System.out.println("입사일 " + emp.getHireDate());
                            System.out.println("퇴사일 " + emp.getEntDate());
                            System.out.println("퇴직여부 " + emp.getEntYn());
                            System.out.println("==========");
                        } else {
                            System.out.println("해당 사번의 직원이 없습니다");
                        }
                        break;
                    case 3:
                        // 직원 등록
                        System.out.print("사번: ");
                        String newEmpId = sc.nextLine();
                        System.out.print("이름: ");
                        String empName = sc.nextLine();
                        System.out.print("주민등록번호: ");
                        String empNo = sc.nextLine();
                        System.out.print("직급코드: ");
                        String jobCode = sc.nextLine();
                        System.out.print("급여등급: ");
                        String salLevel = sc.nextLine();

                        EmployeeDTO newEmp = new EmployeeDTO(
                                newEmpId, empName, empNo, null, null, null, jobCode, salLevel, null, null, null, null, null,null
                        );

                        int result = empDAO.insertEmp(con, newEmp);
                        if(result > 0) {
                            System.out.println("직원 등록 성공!");
                        } else {
                            System.out.println("직원 등록 실패!");
                        }

                        break;
                    case 5:
                        // 직원 삭제
                        System.out.print("삭제할 사번 : ");
                        String delEmpId = sc.nextLine();
                        int delResult = empDAO.deleteEmp(con, delEmpId);
                        if (delResult > 0) {
                            System.out.println("직원 삭제 성공!");
                        } else {
                            System.out.println("직원 삭제 실패!");
                        }
                        break;

                    case 0:
                        System.out.println("프로그램을 종료합니다.");
                        close(con);
                        sc.close();
                        return;

                    default:
                        System.out.println("없는 메뉴 입니다.");
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
        }
    }
}
