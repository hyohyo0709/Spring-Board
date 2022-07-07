package dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;

import dto.BoardDTO;
import dto.PageDTO;

public class BoardDAOImp implements BoardDAO {

	private SqlSessionTemplate sqlSession;
	
	public void setSqlSession(SqlSessionTemplate sqlSession) {
		this.sqlSession = sqlSession;
	}
	
	public BoardDAOImp() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public BoardDTO content(int num) {
		
		return sqlSession.selectOne("view", num);
	}

	@Override
	public int count() {
//		boardMapper.xml
		return sqlSession.selectOne("board.count");
	}

	@Override
	public void delete(int num) {
		sqlSession.delete("board.deleteContent",num);

	}

	@Override
	public String getFile(int num) {
		
		return sqlSession.selectOne("board.uploadFile",num);
	}

	@Override
	public List<BoardDTO> list(PageDTO pv) {
		
		return sqlSession.selectList("board.list",pv);
	}

	@Override
	public void readCount(int num) {
		sqlSession.update("board.readCount", num);

	}

	@Override
	public void reStepCount(BoardDTO dto) {
sqlSession.update("board.reStepCount", dto);		

	}

	@Override
	public void save(BoardDTO dto) {
		sqlSession.insert("board.save", dto);

	}

	@Override
	public void update(BoardDTO dto) {
		sqlSession.update("board.editContent", dto);

	}

	@Override
	public BoardDTO updateNum(int num) {
		
		return sqlSession.selectOne("board.view", num);
	}
}
