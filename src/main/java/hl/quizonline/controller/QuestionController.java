package hl.quizonline.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

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
import org.springframework.data.domain.Page;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import hl.quizonline.config.MyConstances;
import hl.quizonline.entity.Account;
import hl.quizonline.entity.Answer;
import hl.quizonline.entity.ExamPackage;
import hl.quizonline.entity.Image;
import hl.quizonline.entity.Question;
import hl.quizonline.entity.QuestionPackage;
import hl.quizonline.service.AccountService;
import hl.quizonline.service.AnswerService;
import hl.quizonline.service.ImageService;
import hl.quizonline.service.MailboxService;
import hl.quizonline.service.QuestionPackageService;
import hl.quizonline.service.QuestionService;
import hl.quizonline.service.impl.FileUploadUtil;
import hl.quizonline.service.impl.MyHelper;


// TODO: Auto-generated Javadoc
/**
 * The Class QuestionController.
 */
@Controller
@RequestMapping("/manage/question")
public class QuestionController {
	
	/** The question package service. */
	@Autowired
	QuestionPackageService questionPackageService;
	
	/** The question service. */
	@Autowired
	QuestionService questionService;
	
	/** The account service. */
	@Autowired
	AccountService accountService;
	
	/** The answer service. */
	@Autowired
	AnswerService answerService;

	/** The image service. */
	@Autowired
	ImageService imageService;
	
	/** The mailbox service. */
	@Autowired
	MailboxService mailboxService;
	/**
	 * List exam.
	 *
	 * @param questionPackageID the question package ID
	 * @param pageNo the page no
	 * @param key the key
	 * @param packPageNo the pack page no
	 * @param model the model
	 * @return the string
	 */
	@GetMapping(value = {"","/{questionPackageID}"})
	public String listExam(@PathVariable(name ="questionPackageID",required = false ) Integer questionPackageID,
			@RequestParam(name = "page",required = false) Integer pageNo,
			@RequestParam(name="key",required = false) String key,
			@RequestParam(name="packPage",required = false) Integer packPageNo, Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
		    String currentUserName = authentication.getName();
		    if(pageNo==null) pageNo=1;
		    if(key==null) key = "";
		    if(packPageNo==null) packPageNo=1;
		    
		    Page<QuestionPackage> packPage  = questionPackageService.getList(currentUserName, packPageNo, MyConstances.PAGE_SIZE);
		    List<QuestionPackage> questionPackageList = packPage.getContent();
		   
		    
		    if(questionPackageID == null) {
		    	if(questionPackageList.size()>0) {
		    		questionPackageID = questionPackageList.get(0).getQuestionPackageID();
		    	}
		    }
		    
		    if(questionPackageID!= null) {
		    	Page<Question> page = 
		    			questionService.searchQuestionByContent(key,questionPackageID, pageNo, MyConstances.PAGE_SIZE);
//		    	List<Question> questionList = questionService.getAll(questionPackageID);
		    	List<Question> questionList = page.getContent();
		    	QuestionPackage selectedQuestionPackage = questionPackageService.findByID(questionPackageID);
		    	
		    	
		    	model.addAttribute("selectedQuestionPackage", selectedQuestionPackage);
		    	model.addAttribute("questionList", questionList);
		    	model.addAttribute("page", page);
		    }
		    
		    model.addAttribute("packPage", packPage);
		    model.addAttribute("key", key);
		    model.addAttribute("questionPackageList", questionPackageList);
		    model.addAttribute("questionPackageID", questionPackageID);
			return "manage/manage-question";
		}
		return "redirect:/login";
	}
	
	/**
	 * Adds the question package.
	 *
	 * @param questionPackageName the question package name
	 * @return the string
	 */
	@PostMapping("/addpackage")
	public String addQuestionPackage(@RequestParam String questionPackageName) {
		QuestionPackage qp = new QuestionPackage();
		qp.setName(questionPackageName);
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentUserName = authentication.getName();
		Account acc = accountService.getAccountByUsername(currentUserName).get();
		qp.setAccount(acc);
		System.out.println(acc);
		questionPackageService.create(qp);
		return "redirect:/manage/question";
	}
	
	/**
	 * Edits the question package.
	 *
	 * @param questionPackageID the question package ID
	 * @param name the name
	 * @return the string
	 */
	@PostMapping("/editpackage/{questionPackageID}")
	public String editQuestionPackage(@PathVariable("questionPackageID") Integer questionPackageID,
			@RequestParam(name = "name") String name) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			String currentUserName = authentication.getName();
			Account currentAccount = accountService.getAccountByUsername(currentUserName).get();
			
			QuestionPackage questionPackage = questionPackageService.findByID(questionPackageID);
			if(questionPackage.getAccount().getAccountID() == currentAccount.getAccountID()) {
				questionPackage.setName(name);
				questionPackageService.edit(questionPackage);
			}
			
			return "redirect:/manage/question";
		}
		
		return "redirect:/login";
		
	}
	
	/**
	 * Adds the question form.
	 *
	 * @param questionPackageID the question package ID
	 * @param question the question
	 * @param model the model
	 * @return the string
	 */
	@GetMapping("/addquestion/{questionPackageID}")
	public String addQuestionForm(@PathVariable(name = "questionPackageID") Integer questionPackageID,Question question,Model model) {
		List<Question> questionList = questionService.getAll(questionPackageID);
		
		QuestionPackage qp = questionPackageService.findByID(questionPackageID);
		model.addAttribute("questionPackage", qp);
		model.addAttribute("questionPackageID", questionPackageID);
		model.addAttribute("questionList", questionList);
		return "manage/manage-question-add";
	}
	
	/**
	 * Adds the question.
	 *
	 * @param questionPackageID the question package ID
	 * @param multipartFiles the multipart files
	 * @param content the content
	 * @param txtPhuongAn the txt phuong an
	 * @param cbPhuongAn the cb phuong an
	 * @return the string
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@PostMapping("/addquestion/{questionPackageID}")
	@Transactional
	public String addQuestion(@PathVariable(name = "questionPackageID") Integer questionPackageID,
			@RequestParam(name="files",required = false) MultipartFile[] multipartFiles,
			@RequestParam("content") String content,
			@RequestParam("txt-pa") List<String> txtPhuongAn,
			@RequestParam(name = "cb-pa",required = false) List<Integer> cbPhuongAn) throws IOException {
		QuestionPackage qp = questionPackageService.findByID(questionPackageID);
		Question question = new Question();
		
		List<Answer> answerList = new ArrayList<Answer>();
		//set answer
		for(int i =0; i< txtPhuongAn.size();i++) {
			boolean isCorrect = false;
			for(int j=0;j<cbPhuongAn.size();j++) {
				if(i==cbPhuongAn.get(j)-1) {
					isCorrect=true;
					break;
				}
			}
			Answer ans = new Answer(txtPhuongAn.get(i), isCorrect, question);
			answerService.create(ans);
			answerList.add(ans);
		}
		//Xu ly upload file
		String uploadDir = "upload-question-image/";
		List<Image> imageList = new ArrayList<Image>();
		if(multipartFiles!=null) {
			System.out.println("Phat hien co anh can upload");
			
			//Upload file
			System.out.println("dang upload");
			for(MultipartFile item : multipartFiles) {
				
				if(item.getSize()==0) continue;
				
				//upload
				String fileName = StringUtils.cleanPath(item.getOriginalFilename()) + System.currentTimeMillis();
				String encodedFilename = Base64.getEncoder().encodeToString(fileName.getBytes()) + ".jpg";
				FileUploadUtil.saveFile(uploadDir, encodedFilename, item);
				//Them vao list de them vao db
				Image img = new Image();
				img.setUrl(encodedFilename);
				imageList.add(img);
			}
			
			//set image list to question
			question.setImages(imageList);
			System.out.println("upload thanh cong");
		}
		
		//set question
		question.setQuestionContent(content);
		question.setAnswers(answerList);
		question.setQuestionPackage(qp);
		//do add question
		Question createdQuestion =  questionService.create(question);
		System.out.println("Đã tạo xong câu hỏi");
		System.out.println("Thêm ảnh");
		for(Image item : imageList) {
			item.setQuestion(createdQuestion);
			imageService.createImage(item);
		}
		System.out.println("Hoàn thành việc thêm ảnh.");
		return "redirect:/manage/question/addquestion/"+questionPackageID;
	}
	
	/**
	 * Edits the question form.
	 *
	 * @param questionPackageID the question package ID
	 * @param questionID the question ID
	 * @param model the model
	 * @return the string
	 */
	@GetMapping("/editquestion/{questionPackageID}/{questionID}")
	public String editQuestionForm(@PathVariable(name = "questionPackageID") Integer questionPackageID,
			@PathVariable(name = "questionID") Integer questionID,
			Model model){
		//get info
		List<Question> questionList = questionService.getAll(questionPackageID);
		QuestionPackage qp = questionPackageService.findByID(questionPackageID);
		Question question = questionService.getByID(questionID);

		
		//return in model
		model.addAttribute("question", question);
		model.addAttribute("questionPackage", qp);
		model.addAttribute("questionPackageID", questionPackageID);
		model.addAttribute("questionList", questionList);
		return "manage/manage-question-edit";
	}

	/**
	 * Edits the question.
	 *
	 * @param questionID the question ID
	 * @param multipartFiles the multipart files
	 * @param content the content
	 * @param txtPhuongAn the txt phuong an
	 * @param cbPhuongAn the cb phuong an
	 * @return the string
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@PostMapping(value = "/editquestion/{questionID}")
	@Transactional
	public String editQuestion(@PathVariable(name = "questionID") Integer questionID,
			@RequestParam(name="files",required = false) MultipartFile[] multipartFiles,
			@RequestParam("content") String content,
			@RequestParam("txt-pa") List<String> txtPhuongAn,
			@RequestParam(name = "cb-pa",required = false) List<Integer> cbPhuongAn) throws IOException {
		
		Question question = questionService.getByID(questionID);
		QuestionPackage qp = question.getQuestionPackage();
		List<Answer> answerList = new ArrayList<Answer>();
		//xóa các phương án cũ
		questionService.deleteAllAnswer(questionID);
		
		//set answer
		for(int i =0; i< txtPhuongAn.size();i++) {
			boolean isCorrect = false;
			for(int j=0;j<cbPhuongAn.size();j++) {
				if(i==cbPhuongAn.get(j)-1) {
					isCorrect=true;
					break;
				}
			}
			Answer ans = new Answer(txtPhuongAn.get(i), isCorrect, question);
			answerService.create(ans);
			answerList.add(ans);
		}
		//Xu ly upload file
		String uploadDir = "upload-question-image/";
		List<Image> imageList = new ArrayList<Image>();
		if(multipartFiles!=null) {
			System.out.println("Phat hien co anh can upload");
			
			//Upload file
			System.out.println("dang upload");
			for(MultipartFile item : multipartFiles) {
				
				if(item.getSize()==0) continue;
				
				//upload
				String fileName = StringUtils.cleanPath(item.getOriginalFilename()) + System.currentTimeMillis();
				String encodedFilename = Base64.getEncoder().encodeToString(fileName.getBytes()) + ".jpg";
				FileUploadUtil.saveFile(uploadDir, encodedFilename, item);
				//Them vao list de them vao db
				Image img = new Image();
				img.setUrl(encodedFilename);
				imageList.add(img);
			}
			
			System.out.println("upload thanh cong");
		}
		
		//set question
		question.setQuestionContent(content);
		question.setAnswers(answerList);
		question.setQuestionPackage(qp);
		//do add question
		Question createdQuestion =  questionService.edit(question);
		System.out.println("Đã tạo xong câu hỏi");
		System.out.println("Thêm ảnh");
		for(Image item : imageList) {
			item.setQuestion(createdQuestion);
			System.out.println(item);
			imageService.createImage(item);
		}
		System.out.println("Hoàn thành việc thêm ảnh.");
		return "redirect:/manage/question/editquestion/"+qp.getQuestionPackageID()+"/"+ question.getQuestionID();
	}
	
	/**
	 * Adds the with excel form.
	 *
	 * @param examPackageID the exam package ID
	 * @param model the model
	 * @return the string
	 */
	@GetMapping("addexcel/{examPackageID}")
	public String addWithExcelForm(@PathVariable("examPackageID") Integer examPackageID,
			Model model) {
		
		model.addAttribute("examPackageID", examPackageID);
		return "manage/manage-question-addexcel";
	}
	
	/**
	 * Adds the with excel.
	 *
	 * @param examPackageID the exam package ID
	 * @param file the file
	 * @param model the model
	 * @return the string
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@PostMapping("/addexcel/{examPackageID}")
	public String addWithExcel(@PathVariable("examPackageID") Integer examPackageID,@RequestParam("excelfile") MultipartFile file, 
			Model model) throws IOException {
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
	            			try {
	            				if((Double)cellValue2 == 0 ) {
		            				isCorrect=false;
		            			}
		            			else {
		            				isCorrect = true;
		            			}
	            				answer.setIdCorrect(isCorrect);
		            			answerList.add(answer);
							} catch (ClassCastException e) {
								int column = cell2.getColumnIndex();
								char c = (char)(column+ 'A');
								model.addAttribute("msgerr", "Sai kiểu dữ liệu.");
								model.addAttribute("msgdetail","Sai ở ô : "+(cell2.getRowIndex()+1)+"-"+c);
						    	return "manage/manage-question-addexcel";
							}
	            			
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
		    questionService.addListQuestionToQuestionPackage(examPackageID, questionList);
		    wb.close();
		    model.addAttribute("msgsuccess", questionList.size());
			return "manage/manage-question-addexcel";
		}
		
		return "redirect:/login";
		
	}

	/**
	 * Gets the cell value.
	 *
	 * @param cell the cell
	 * @return the cell value
	 */
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

    /**
     * Delete question package.
     *
     * @param questionPackageID the question package ID
     * @param reasonID the reason ID
     * @param reason the reason
     * @return the string
     */
    @GetMapping("/deletePackage/{questionPackageID}")
    @Transactional
    public String deleteQuestionPackage(@PathVariable("questionPackageID") Integer questionPackageID,
    		@RequestParam(name = "reasonID",required =  false) Integer reasonID,
			@RequestParam(name = "reason", required = false) String reason,
			@RequestParam(name="redirectUrl",required = false) String redirectUrl
			) {
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			String currentUserName = authentication.getName();
			Optional<Account> opAccount = accountService.getAccountByUsername(currentUserName);
			Account currentAccount = opAccount.get();
			QuestionPackage questionPackage = questionPackageService.findByID(questionPackageID);
			//Kiểm tra xem người dùng hiện tại có đủ quyền xóa hay không
			if(!currentUserName.equals(questionPackage.getAccount().getUsername())) {
				if(!currentAccount.getRole().equals("ROLE_ADMIN")) {
					return "redirect:/login";
				}
			}
			//xóa questionPackage
			questionPackageService.delete(questionPackageID);
			//Sau khi xóa, kiểm tra xem người xóa là ai, nếu người xóa là admin và không phải
			//chủ sở hữu thì tiến hành gửi thư thông báo rằng ExamPackage đã bị admin xóa
			if(!currentUserName.equals(questionPackage.getAccount().getUsername())) {
				String reasonMessage = "";
				if(reasonID!=null) {
					if(reasonID==0) {
						reasonMessage = "Spam";
					}
					else if (reasonID==1){
						reasonMessage = "Nội dung không phù hợp";
					}
					else if (reasonID == 2) {
						reasonMessage = reason;
					}
				}
				//Tiến hành gửi thông báo
				mailboxService.noticeUserWhenAdminDeleteQuestionPackage(questionPackage.getAccount(), questionPackage, reasonMessage);
				System.out.println(redirectUrl);
				if(redirectUrl!=null) {
					return "redirect:"+ redirectUrl;
				}
				else {
					return "redirect:/manage/account";
				}
			}
			
			if(redirectUrl!=null) {
				return "redirect:"+ redirectUrl;
			}
			else {
				return "redirect:/manage/question";
			}
    	
		}
		
		return "redirect:/login";
    }

    /**
     * Delete question.
     *
     * @param questionID the question ID
     * @return the string
     */
    @GetMapping("/deleteQuestion/{questionID}")
    public String deleteQuestion(@PathVariable("questionID") Integer questionID) {
    	Question question = questionService.getByID(questionID);
    	questionService.delete(questionID);
    	
    	return "redirect:/manage/question/addquestion/"+question.getQuestionPackage().getQuestionPackageID();
    }
    
    /**
     * Delete image.
     *
     * @param imageID the image ID
     * @return the string
     */
    @PostMapping("/deleteImage/{imageID}")
    public String deleteImage(@PathVariable("imageID") Integer imageID) {
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			String currentUserName = authentication.getName();
			Image img = imageService.getImageByID(imageID);
			String urlRedirect = "/manage/question/editquestion/"+ 
			img.getQuestion().getQuestionPackage().getQuestionPackageID() + 
			"/" + img.getQuestion().getQuestionID();
			//kiem tra xem nguoi dung co quyen xoa anh hay khong
			Account account = accountService.getAccountByUsername(currentUserName).get();
			if(account.getUsername().equals(img.getQuestion().getQuestionPackage().getAccount().getUsername())|| account.getRole().equals("ROLE_ADMIN")) {
				imageService.deleteImage(imageID);
				return "redirect:"+urlRedirect;
			}else {
				return "redirect:/login";
			}
			
		}
		return "redirect:/login";
    }
}
