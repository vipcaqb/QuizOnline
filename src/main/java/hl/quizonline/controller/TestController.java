package hl.quizonline.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;


import hl.quizonline.entity.Account;
import hl.quizonline.entity.Answer;
import hl.quizonline.entity.Question;
import hl.quizonline.repository.ExamPackageRepository;
import hl.quizonline.service.QuestionService;
import hl.quizonline.service.impl.MyHelper;

@Controller
@PropertySource("classpath:message.properties")
@RequestMapping("/test")
public class TestController {
	@Autowired
	Environment env;
	
	@Autowired
	QuestionService questionService;
	
	@Autowired
	ExamPackageRepository examPackageRepository;
	
	private String fileLocation;
	
	@GetMapping("")
	public String test() {
		String originalInput = "test input" + System.currentTimeMillis();
		String encodedString = Base64.getEncoder().encodeToString(originalInput.getBytes());
		System.out.println(encodedString);
		return "login";
	}
	@PostMapping("/addexcel")
	@Transactional
	public String addExcel(@RequestParam("excelfile") MultipartFile file, Model model) throws IOException  {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			//T???i file c???n x??? l?? l??n
			String currentUserName = authentication.getName();
			InputStream in = file.getInputStream();
		    
		    //?????c file excel
			System.out.println("da tai len file"+MyHelper.getTag(file.getOriginalFilename()));
		    Workbook wb = null;
		    if(MyHelper.getTag(file.getOriginalFilename()).equals(".xls")) {
		    	wb = new HSSFWorkbook(in);
		    }
		    else if (MyHelper.getTag(file.getOriginalFilename()).equals(".xlsx")) {
		    	wb = new XSSFWorkbook(in);
		    }
		    else {
		    	model.addAttribute("msgerr", "Sai ?????nh d???ng file");
		    	return "manage/manage-question-addexcel";
		    }
		    System.out.println("da doc dc file");
		    //L???y sheet ?????u ti??n
		    Sheet sheet = wb.getSheetAt(0);
		    List<Question> questionList = new ArrayList<Question>();
		    //L???y t???t c??? row
		    Iterator<Row> iterator = sheet.iterator();
		    while(iterator.hasNext()) {
		    	Row nextRow = iterator.next();
		    	if (nextRow.getRowNum() == 0) {
	                // B??? qua ti??u ?????
	                continue;
	            }
		    	// L???y t???t c??? cell
	            Iterator<Cell> cellIterator = nextRow.cellIterator();
	            Question question = new Question();
	            List<Answer> answerList = new ArrayList<Answer>();
	            //Duy???t ??
	            while(cellIterator.hasNext()) {
	            	Answer answer = new Answer();
	            	Cell cell = cellIterator.next();
	            	//Ki???m tra n???u ?? tr???ng th?? b??? qua row
	            	Object cellValue = getCellValue(cell);
	                if (cellValue == null || cellValue.toString().isEmpty()) {
	                    break;
	                }
	            	
	            	if(cell.getColumnIndex()==0) {
	            		//L???y ti??u ?????
	            		question.setQuestionContent(cell.getStringCellValue());
	            	}
	            	else {
	            		
	            		//Ki???m tra xem ?? ti???p theo c?? t???n t???i kh??ng, n???u c?? m???i th???c hi???n
	            		if(cellIterator.hasNext()) {
	            			Cell cell2 = cellIterator.next();
	            			//N???u ?? kh??ng c?? g?? th?? b??? qua row
	            			Object cellValue2 = getCellValue(cell2);
	                        if (cellValue == null || cellValue.toString().isEmpty()) {
	                            break;
	                        }
	            			answer.setAnswerContent(cell.getStringCellValue());
	            			boolean isCorrect;
	            			if((Double)cellValue2 == 0 ) {
	            				isCorrect=false;
	            			}
	            			else {
	            				isCorrect = true;
	            			}
	            			answer.setIdCorrect(isCorrect);
	            			answerList.add(answer);
	            		}
	            	}
	            }
	            if(question.getQuestionContent()!=null) {
	            	question.setAnswers(answerList);
	            	questionList.add(question);
	            }
		    }
		    //K???t qu??? c?? ???????c l?? questionList danh s??ch c??c c??u h???i
		    //Th??m v??o db
		    questionService.addListQuestionToQuestionPackage(2, questionList);
		    for(int i =0;i<questionList.size();i++) {
		    	System.out.println(questionList.get(i));
		    }
		    
		    wb.close();
			return "manage/manage-question-addexcel";
		}
		
		return "redirect:/test";
	}
	
	// Get cell value
    private static Object getCellValue(Cell cell) {
        CellType cellType = cell.getCellTypeEnum();
        Object cellValue = null;
        switch (cellType) {
        case BOOLEAN:
            cellValue = cell.getBooleanCellValue();
            break;
        case FORMULA:
            Workbook workbook = cell.getSheet().getWorkbook();
            FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
            cellValue = evaluator.evaluate(cell).getNumberValue();
            break;
        case NUMERIC:
            cellValue = cell.getNumericCellValue();
            break;
        case STRING:
            cellValue = cell.getStringCellValue();
            break;
        case _NONE:
        case BLANK:
        case ERROR:
            break;
        default:
            break;
        }
 
        return cellValue;
    }
}
