package service;

import java.io.File;
import java.util.List;

import dao.BoardDAO;
import dto.BoardDTO;
import dto.PageDTO;

public class BoardServiceImp implements BoardService {

	private BoardDAO dao;
	
	public void setDao(BoardDAO dao) {
		this.dao = dao;
	}
	
	public BoardServiceImp() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public BoardDTO contentProcess(int num) {
		dao.readCount(num);
		return dao.content(num);
	}

	@Override
	public int countProcess() {
		
		return dao.count();
	}

	@Override
	public void deleteProcess(int num, String urlpath) {
		
		String path = dao.getFile(num);
		if(path!=null) {// 기존 첨부파일이 있으면 삭제
			File fe = new File(urlpath, path);
			fe.delete();
		}
		dao.delete(num);
		
	}

	@Override
	public String fileSelectprocess(int num) {
		
		return dao.getFile(num);
	}

	@Override
	public void insertProcess(BoardDTO dto) {
		if(dto.getRef()!=0){// 답변글
			dao.reStepCount(dto); // 기존의 답변들의 step 값을 +1
		dto.setRe_step(dto.getRe_step()+1);// 새로운 답변으로 등록되는 글이니까 step을 +1, 즉 1로 만들어준다. 최신 답변의 step 값이 1임
		dto.setRe_level(dto.getRe_level()+1);// 답변 레벨. 답변의 답변은 레벨=2
		
		}
		dao.save(dto);	
		
		
	}

	@Override
	public List<BoardDTO> listProcess(PageDTO pv) {
		
		return dao.list(pv);
	}

	@Override
	public void reStepProcess(BoardDTO dto) {
		
		

	}

	@Override
	public void updateProcess(BoardDTO dto, String urlpath) {
		
		String fileName = dto.getUpload();
		if(fileName!=null) {// 수정한 파일이 있으면
			String path= dao.getFile(dto.getNum());
		if(!path.isEmpty()) {// 기존 첨부파일이 있으면 삭제
			File fe = new File(urlpath, path);
			fe.delete();
		}
		}
		
		dao.update(dto);

	}

	@Override
	public BoardDTO updateSelectProcess(int num) {
		
		return dao.updateNum(num);
	}	
}
