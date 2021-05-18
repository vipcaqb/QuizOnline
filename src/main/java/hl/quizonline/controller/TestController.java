package hl.quizonline.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.ArrayList;
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
		Long a = examPackageRepository.getViewsMonth(new Date(), new Date());
		System.out.println(a);
		return "manage/manage-question-addexcel";
	}
	@PostMapping("/addexcel")
	@Transactional
	public String addExcel(@RequestParam("excelfile") MultipartFile file, Model model) throws IOException  {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			//Tải file cần xử lý lên
			String currentUserName = authentication.getName();
			InputStream in = file.getInputStream();
		    
		    //đọc file excel
			System.out.println("da tai len file"+MyHelper.getTag(file.getOriginalFilename()));
		    Workbook wb = null;
		    if(MyHelper.getTag(file.getOriginalFilename()).equals(".xls")) {
		    	wb = new HSSFWorkbook(in);
		    }
		    else if (MyHelper.getTag(file.getOriginalFilename()).equals(".xlsx")) {
		    	wb = new XSSFWorkbook(in);
		    }
		    else {
		    	model.addAttribute("msgerr", "Sai định dạng file");
		    	return "manage/manage-question-addexcel";
		    }
		    System.out.println("da doc dc file");
		    //Lấy sheet đầu tiên
		    Sheet sheet = wb.getSheetAt(0);
		    List<Question> questionList = new ArrayList<Question>();
		    //Lấy tất cả row
		    Iterator<Row> iterator = sheet.iterator();
		    while(iterator.hasNext()) {
		    	Row nextRow = iterator.next();
		    	if (nextRow.getRowNum() == 0) {
	                // Bỏ qua tiêu đề
	                continue;
	            }
		    	// Lấy tất cả cell
	            Iterator<Cell> cellIterator = nextRow.cellIterator();
	            Question question = new Question();
	            List<Answer> answerList = new ArrayList<Answer>();
	            //Duyệt ô
	            while(cellIterator.hasNext()) {
	            	Answer answer = new Answer();
	            	Cell cell = cellIterator.next();
	            	//Kiểm tra nếu ô trống thì bỏ qua row
	            	Object cellValue = getCellValue(cell);
	                if (cellValue == null || cellValue.toString().isEmpty()) {
	                    break;
	                }
	            	
	            	if(cell.getColumnIndex()==0) {
	            		//Lấy tiêu đề
	            		question.setQuestionContent(cell.getStringCellValue());
	            	}
	            	else {
	            		
	            		//Kiểm tra xem ô tiếp theo có tồn tại không, nếu có mới thực hiện
	            		if(cellIterator.hasNext()) {
	            			Cell cell2 = cellIterator.next();
	            			//Nếu ô không có gì thì bỏ qua row
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
		    //Kết quả có được là questionList danh sách các câu hỏi
		    //Thêm vào db
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
