package controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import dto.BoardDTO;
import dto.PageDTO;
import service.BoardService;

// http://localhost:8090/myapp/list.sb

@Controller
public class BoardController {

	private BoardService service;
	private PageDTO pdto;
	private int currentPage;
	
	public void setService(BoardService service) {
		this.service = service;
	}
	
	public BoardController() {
		// TODO Auto-generated constructor stub
	}
	
	@RequestMapping(value = "/list.sb")
	public ModelAndView listMehod(PageDTO pv, ModelAndView mav) {
		int totalRecord = service.countProcess();
		
		if(totalRecord !=0) {
			if(pv.getCurrentPage()==0) {
				currentPage=1;
			}else {
				currentPage=pv.getCurrentPage();
			}
			pdto= new PageDTO(currentPage, totalRecord);
			List<BoardDTO> alist = service.listProcess(pdto);		
		mav.addObject("alist", alist);
		mav.addObject("pv", pdto);
			
		}
		
		mav.setViewName("board/list");
		return mav;
	}// //////////////////////////////////////list method
	
	
	@RequestMapping(value = "/write.sb", method = RequestMethod.GET)
	public ModelAndView writeMethod(BoardDTO dto,PageDTO pv ,ModelAndView mav) {
		if(dto.getRef()!=0) { // 답변글이면
			mav.addObject("currentPage", pv.getCurrentPage());
			mav.addObject("dto", dto);
		}
		
		mav.setViewName("board/write");
		return mav;
	}////////////////////////////////////////////writemethod
	
	
//	글 저장
	@RequestMapping(value = "/writesave.sb", method = RequestMethod.POST)
	public String writeProMethod(BoardDTO dto,PageDTO pv ,HttpServletRequest request) {
		MultipartFile file = dto.getFilename();
		if(!file.isEmpty()) {
			UUID random = saveCopyFile(file, request);
			dto.setUpload(random+"_"+file.getOriginalFilename());
		}
		
		dto.setIp(request.getRemoteAddr());
		
		service.insertProcess(dto);
		//답변글인지 새 글인지 
		if(dto.getRef()!=0) {//답변글
			
			return "redirect:/list.sb?currentPage=" + pv.getCurrentPage();
		}else {//새 글
			
			return "redirect:/list.sb";
		}
		
		
		
	}/////////////////////////writepromethod 
	
	
	// 글 수정
	@RequestMapping(value = "/update.sb", method = RequestMethod.GET)
	public ModelAndView updateMethod(int num, int currentPage, ModelAndView mav) {
		mav.addObject("dto", service.updateSelectProcess(num));
		mav.addObject("currentPage", currentPage);
		mav.setViewName("board/update");
		return mav;
	}
	
	
	
	@RequestMapping(value = "/view")
	public ModelAndView viewMethod(int currentPage, int num, ModelAndView mav) {
		
		
		 mav.addObject("dto", service.contentProcess(num));
		 mav.addObject("currentPage", currentPage);
		 mav.setViewName("board/view");
		
		return mav;
		
	}/////////////////////////////viewmethod
	
	// 글 수정
	@RequestMapping(value = "/update.sb", method = RequestMethod.POST)
	public String updateProMethod(BoardDTO dto, int currentPage, HttpServletRequest request) {
		MultipartFile file = dto.getFilename();
		if(!file.isEmpty()) {
			UUID random = saveCopyFile(file, request);
			dto.setUpload(random+"_"+file.getOriginalFilename());
		}
		
		service.updateProcess(dto, urlPath(request));
		return "redirect:/list.sb?currentPage=" + currentPage;
	}
	

//글 삭제
	@RequestMapping(value = "/delete.sb", method = RequestMethod.GET)
	public String deleteMethod(int num, int currentPage, HttpServletRequest request) {
		service.deleteProcess(num, urlPath(request));
		return "redirect:/list.sb?currentPage=" + currentPage;
	}
	
	
	// 업로드 파일 경로
	private String urlPath(HttpServletRequest request) {
		String root = request.getSession().getServletContext().getRealPath("/");
//		C:C:\Users\ user\Desktop\khj\smart_study\spring_workspace\.metadata\.plugins\org.eclipse.wst.server.core\tmp0\wtpwebapps\spring08_board\
//		System.out.println("root" + root);
		
		String saveDirectory = root + "temp" + File.separator;
		return saveDirectory;
	}
	
	
//	업로드 파일 첨부
	private UUID saveCopyFile(MultipartFile file, HttpServletRequest request) {
		String filename = file.getOriginalFilename();
		
		
		//중복 파일명을 처리하기 위해 난수 발생
		UUID ran = UUID.randomUUID();
		
		File fe= new File(urlPath(request));
		if(!fe.exists()) {
			fe.mkdirs();
		}
		
		File ff = new File(urlPath(request), ran + "_" + filename);
		
		try {
			FileCopyUtils.copy(file.getInputStream(), new FileOutputStream(ff));
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ran;
		
		
	}///////////////////save copy file
	
	
	// 첨부파일 다운로드
	@RequestMapping(value = "/contentdownload")
	public ModelAndView downMethod(int num, ModelAndView mav) {
		mav.addObject("num", num);
		mav.setViewName("download");
		return mav;
	}////////////////////////downfile
	
}
