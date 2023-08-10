package team.star.score_system.util;

import org.apache.poi.ss.usermodel.*;
import team.star.score_system.constant.ErrorCode;
import team.star.score_system.dto.StudentDto;
import team.star.score_system.dto.TeacherDto;
import team.star.score_system.exception.AppException;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Patrick_Star
 * @version 1.0
 */
public class ExcelUtil {
    public static List<TeacherDto> excelToTeacherList(InputStream inputStream) {
        List<TeacherDto> list = new ArrayList<>();
        Workbook workbook = null;
        try {
            workbook = WorkbookFactory.create(inputStream);
            inputStream.close();
            Sheet sheet = workbook.getSheetAt(0);
            int rowLength = sheet.getLastRowNum() + 1;
            Row row = sheet.getRow(0);
            int colLength = row.getLastCellNum();
            Cell cell = null;
            for (int a = 1; a < rowLength; a++) {
                TeacherDto teacher = new TeacherDto();
                for (int b = 0; b < colLength; b++) {
                    cell = sheet.getRow(a).getCell(b);
                    cell.setCellType(CellType.STRING);
                    String stringCellValue = cell.getStringCellValue();
                    switch (b) {
                        case 0 -> teacher.setId(Integer.valueOf(stringCellValue));
                        case 1 -> teacher.setName(stringCellValue);
                        case 2 -> teacher.setGender(stringCellValue);
                        case 3 -> teacher.setPhoneNum(stringCellValue);
                        case 4 -> teacher.setBirthYear(Integer.parseInt(stringCellValue));
                        case 5 -> teacher.setJobTitle(stringCellValue);
                        case 6 -> teacher.setCollege(stringCellValue);
                    }
                }
                list.add(teacher);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException(ErrorCode.FILE_ERROR);
        }
        return list;
    }

    public static List<StudentDto> excelToStudentList(InputStream inputStream) {
        List<StudentDto> list = new ArrayList<>();
        Workbook workbook = null;
        try {
            workbook = WorkbookFactory.create(inputStream);
            inputStream.close();
            Sheet sheet = workbook.getSheetAt(0);
            int rowLength = sheet.getLastRowNum() + 1;
            Row row = sheet.getRow(0);
            int colLength = row.getLastCellNum();
            Cell cell = null;
            for (int a = 1; a < rowLength; a++) {
                StudentDto student = new StudentDto();
                for (int b = 0; b < colLength; b++) {
                    cell = sheet.getRow(a).getCell(b);
                    cell.setCellType(CellType.STRING);
                    String stringCellValue = cell.getStringCellValue();
                    switch (b) {
                        case 0 -> student.setId(Long.valueOf(stringCellValue));
                        case 1 -> student.setName(stringCellValue);
                        case 2 -> student.setGender(stringCellValue);
                        case 3 -> student.setBirthYear(Integer.parseInt(stringCellValue));
                        case 4 -> student.setClassName(stringCellValue);
                        case 5 -> student.setHometown(stringCellValue);
                    }
                }
                list.add(student);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException(ErrorCode.FILE_ERROR);
        }
        return list;
    }
}

